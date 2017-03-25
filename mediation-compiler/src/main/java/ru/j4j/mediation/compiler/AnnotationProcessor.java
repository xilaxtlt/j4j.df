package ru.j4j.mediation.compiler;

import org.yaml.snakeyaml.Yaml;
import ru.j4j.mediation.compiler.config.MediationConfig;
import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;
import ru.j4j.mediation.core.annotations.ToContext;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new HashSet<>(super.getSupportedAnnotationTypes());
        result.add(FromContext.class.getName());
        result.add(ToContext.class.getName());
        result.add(RunnableMethod.class.getName());
        return Collections.unmodifiableSet(result);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnv = processingEnv;
        InputStream configStream = classLoader.getResourceAsStream(DEFAULT_CONFIG_FILE_NAME);
        this.mediationConfig = new Yaml().loadAs(configStream, MediationConfig.class);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            System.out.println(processingEnv);
            System.out.println("is processingOver");
            //TODO finish and generate classes here
        } else {
            System.out.println("is not processingOver");
            //TODO collect model by annotations
        }
        return false;
    }

}
