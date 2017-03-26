package ru.j4j.mediation.compiler.builder;

import ru.j4j.mediation.compiler.config.MediationConfig;
import ru.j4j.mediation.compiler.model.MediationModel;

import javax.annotation.processing.ProcessingEnvironment;

import static ru.j4j.mediation.compiler.utils.ExceptionUtils.wrapThrowable;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class MediationCompiler {
    private final ProcessingEnvironment processingEnv;
    private final MediationConfig config;
    private final MediationModel model;

    public MediationCompiler(ProcessingEnvironment processingEnv, MediationConfig config, MediationModel model) {
        this.processingEnv = processingEnv;
        this.config = config;
        this.model = model;
    }

    public void compile() {
        config.getMediation().forEach((dataFlowName, dataFlow) ->
                wrapThrowable(() ->
                        new DataFlowBuilder(processingEnv.getFiler(), config, model, dataFlowName, dataFlow).write())
        );
    }

}
