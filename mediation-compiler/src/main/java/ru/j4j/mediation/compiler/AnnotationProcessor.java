package ru.j4j.mediation.compiler;

import org.yaml.snakeyaml.Yaml;
import ru.j4j.mediation.compiler.builder.MediationCompiler;
import ru.j4j.mediation.compiler.config.MediationCompileException;
import ru.j4j.mediation.compiler.config.MediationConfig;
import ru.j4j.mediation.compiler.model.MediationModel;
import ru.j4j.mediation.compiler.model.UnitClassName;
import ru.j4j.mediation.compiler.utils.CreateIfNotExists;
import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;
import ru.j4j.mediation.core.annotations.ToContext;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * @author Artemiy.Shchekotov
 * @since 3/21/2017
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    private static final String DEFAULT_CONFIG_FILE_NAME = "j4j-mediation.yaml";

    private final ClassLoader classLoader = AnnotationProcessor.class.getClassLoader();

    private final AtomicBoolean doProcessFlag = new AtomicBoolean(Boolean.FALSE);

    private ProcessingEnvironment processingEnv;
    private MediationConfig mediationConfig;
    private MediationModel model = new MediationModel();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new HashSet<>(super.getSupportedAnnotationTypes());
        result.addAll(asList(
                FromContext.class.getName(),
                ToContext.class.getName(),
                RunnableMethod.class.getName()
        ));
        return Collections.unmodifiableSet(result);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnv   = processingEnv;
        this.mediationConfig = loadConfig();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver() && doProcessFlag.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            fillModel(roundEnv);
            new MediationCompiler(processingEnv, mediationConfig, model).compile();
        }
        return false;
    }

    private MediationConfig loadConfig() {
        return new Yaml().loadAs(classLoader.getResourceAsStream(DEFAULT_CONFIG_FILE_NAME), MediationConfig.class);
    }

    private void fillModel(RoundEnvironment roundEnv) {
        fillModelByGetters(roundEnv);
        fillModelBySetters(roundEnv);
        fillModelByCommands(roundEnv);
    }

    private void fillModelByGetters(RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(ToContext.class)
                .stream()
                .map(ExecutableElement.class::cast)
                .forEach(element -> {

                    Element type         = element.getEnclosingElement();
                    String  unitName     = type.toString();
                    String  returnType   = element.getReturnType().toString();
                    String  getterName   = element.getSimpleName().toString();
                    String  originalName = getterName;

                    if (getterName.indexOf("get") == 0) {
                        if (getterName.length() < 4) {
                            throw new MediationCompileException(
                                    format("getter \"%s\" of unit \"%s\" must have size > 3", getterName, unitName));
                        }
                        getterName = getterName.substring(3, getterName.length());
                    } else if (getterName.indexOf("is") == 0) {
                        if (getterName.length() < 3) {
                            throw new MediationCompileException(
                                    format("getter \"%s\" of unit \"%s\" must have size > 2", getterName, unitName));
                        }
                        getterName = getterName.substring(2, getterName.length());
                    }

                    model.getUnit(UnitClassName.of(unitName), CreateIfNotExists.YES)
                            .getGetter(getterName, CreateIfNotExists.YES)
                            .setReturnType(returnType)
                            .setOriginalGetterName(originalName);

                });
    }

    private void fillModelBySetters(RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(FromContext.class)
                .stream()
                .map(ExecutableElement.class::cast)
                .forEach(element -> {

                    Element type         = element.getEnclosingElement();
                    String  unitName     = type.toString();
                    String  setterName   = element.getSimpleName().toString();
                    String  originalName = setterName;

                    if (setterName.indexOf("set") == 0) {
                        if (setterName.length() < 4) {
                            String message = format(
                                    "setter \"%s\" of unit \"%s\" must not have only prefix \"set\"",
                                    setterName, unitName
                            );
                            throw new MediationCompileException(message);
                        }
                        setterName = setterName.substring(3, setterName.length());
                    }

                    List<? extends VariableElement> parameters = element.getParameters();

                    if (parameters == null || parameters.size() != 1) {
                        String message = format(
                                "setter \"%s\" of unit \"%s\" must have one argument only",
                                setterName, unitName);
                        throw new MediationCompileException(message);
                    }

                    model.getUnit(UnitClassName.of(unitName), CreateIfNotExists.YES)
                            .getSetter(setterName, CreateIfNotExists.YES)
                            .setArgumentType(parameters.get(0).asType().toString())
                            .setOriginalSetterName(originalName);
                });
    }

    private void fillModelByCommands(RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(RunnableMethod.class)
                .stream()
                .map(ExecutableElement.class::cast)
                .forEach(element -> {

                    Element type         = element.getEnclosingElement();
                    String  unitName     = type.toString();
                    String  originalName = element.getSimpleName().toString();

                    model.getUnit(UnitClassName.of(unitName), CreateIfNotExists.YES)
                            .getCommand(originalName, CreateIfNotExists.YES)
                            .setOriginalName(originalName);

                });
    }

}
