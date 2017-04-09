package ru.j4j.mediation.compiler;

import com.squareup.javapoet.JavaFile;
import ru.j4j.mediation.compiler.builder.MediationCompiler;
import ru.j4j.mediation.compiler.config.MediationConfigLoader;
import ru.j4j.mediation.compiler.model.MediationModelBuilder;
import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;
import ru.j4j.mediation.core.annotations.ToContext;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Arrays.asList;
import static ru.j4j.mediation.compiler.utils.ExceptionUtils.tryIt;

/**
 * @author Artemiy.Shchekotov
 * @since 3/21/2017
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    private final AtomicBoolean doProcessFlag = new AtomicBoolean(Boolean.FALSE);

    private ProcessingEnvironment processingEnv;

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
        this.processingEnv = processingEnv;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver() && doProcessFlag.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            MediationModelBuilder modelBuilder = new MediationModelBuilder(roundEnv);
            MediationConfigLoader configLoader = new MediationConfigLoader();
            MediationCompiler     compiler     = new MediationCompiler(configLoader.load(), modelBuilder.build());
            Collection<JavaFile>  javaFiles    = compiler.compile();
            javaFiles.forEach(javaFile -> tryIt(() -> javaFile.writeTo(processingEnv.getFiler())));
        }
        return false;
    }

}
