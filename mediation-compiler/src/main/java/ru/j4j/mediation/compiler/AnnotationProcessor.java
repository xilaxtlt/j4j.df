package ru.j4j.mediation.compiler;

import org.yaml.snakeyaml.Yaml;
import ru.j4j.mediation.core.annotations.DataFlowConfiguration;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

/**
 * @author Artemiy.Shchekotov
 * @since 3/21/2017
 */
@SupportedAnnotationTypes({"*"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

    private final ClassLoader classLoader = AnnotationProcessor.class.getClassLoader();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        roundEnv.getElementsAnnotatedWith(DataFlowConfiguration.class).forEach(element ->
                ofNullable(element.getAnnotation(DataFlowConfiguration.class)).ifPresent(config -> {
                    String configFile = config.value();
                    InputStream inputStream = classLoader.getResourceAsStream(configFile);
                    if (inputStream == null)  {
                        String elementName = element.getSimpleName().toString();
                        throw new ConfigurationFileNotFound(format(
                                "Configuration file %s not found. Configuration class name is %s",
                                configFile,
                                elementName
                        ));
                    }
                    Yaml yaml = new Yaml();
                    Map configuration = yaml.loadAs(inputStream, Map.class);
                    System.out.println(configuration);
                })
        );
        return false;
    }

}
