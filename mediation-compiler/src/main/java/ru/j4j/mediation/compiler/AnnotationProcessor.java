package ru.j4j.mediation.compiler;

import org.yaml.snakeyaml.Yaml;
import ru.j4j.mediation.compiler.builder.MediationCompiler;
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
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * @author Artemiy.Shchekotov
 * @since 3/21/2017
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    private static final String DEFAULT_CONFIG_FILE_NAME = "j4j-mediation.yaml";

    private final ClassLoader classLoader = AnnotationProcessor.class.getClassLoader();

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
        if (!roundEnv.processingOver()) {
            fillModel(roundEnv);
        } else {
            new MediationCompiler(processingEnv, mediationConfig, model).compile();
        }
        return false;
    }

    private MediationConfig loadConfig() {
        return new Yaml().loadAs(classLoader.getResourceAsStream(DEFAULT_CONFIG_FILE_NAME), MediationConfig.class);
    }

    private void fillModel(RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(FromContext.class).forEach(element -> {
            Element type = element.getEnclosingElement();
            model.getUnit(UnitClassName.of(type.toString()), CreateIfNotExists.YES)
                    .getGetter(element.getSimpleName().toString(), CreateIfNotExists.YES);
            //TODO
        });
        roundEnv.getElementsAnnotatedWith(ToContext.class).forEach(element -> {
            Element type = element.getEnclosingElement();
            model.getUnit(UnitClassName.of(type.toString()), CreateIfNotExists.YES)
                    .getSetter(element.getSimpleName().toString(), CreateIfNotExists.YES);
            //TODO
        });
    }

}
