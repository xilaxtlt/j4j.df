package ru.j4j.mediation.compiler.builder;

import com.squareup.javapoet.JavaFile;
import ru.j4j.mediation.compiler.config.DataFlow;
import ru.j4j.mediation.compiler.config.MediationConfig;
import ru.j4j.mediation.compiler.model.MediationModel;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class MediationCompiler {
    private final MediationConfig config;
    private final MediationModel model;

    public MediationCompiler(MediationConfig config, MediationModel model) {
        this.config = config;
        this.model  = model;
    }

    public Collection<JavaFile> compile() {
        return config.getMediation()
                .entrySet()
                .stream()
                .map(entry -> {
                    String          dataFlowName = entry.getKey();
                    DataFlow        dataFlow     = entry.getValue();
                    DataFlowBuilder builder      = new DataFlowBuilder(config, model, dataFlowName, dataFlow);
                    return builder.build();
                }).collect(Collectors.toList());
    }

}
