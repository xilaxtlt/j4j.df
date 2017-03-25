package ru.j4j.mediation.compiler;

import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;
import ru.j4j.mediation.core.annotations.ToContext;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author Artemiy.Shchekotov
 * @since 3/21/2017
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    private static final String DEFAULT_CONFIG_FILE_NAME = "j4j-mediation.yaml";

    private final ClassLoader classLoader = AnnotationProcessor.class.getClassLoader();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = super.getSupportedAnnotationTypes();
        result.add(FromContext.class.getName());
        result.add(ToContext.class.getName());
        result.add(RunnableMethod.class.getName());
        return result;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //TODO load configuration file
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            //TODO finish and generate classes here
        } else {
            //TODO collect model by annotations
        }
        return false;
    }

}
