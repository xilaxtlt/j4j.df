package ru.j4j.mediation.compiler.builder;

import com.squareup.javapoet.*;
import ru.j4j.mediation.compiler.config.DataFlow;
import ru.j4j.mediation.compiler.config.MediationConfig;
import ru.j4j.mediation.compiler.model.MediationModel;
import ru.j4j.mediation.compiler.model.UnitClassName;
import ru.j4j.mediation.compiler.utils.CreateIfNotExists;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import java.io.IOException;

import static java.util.Optional.ofNullable;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class DataFlowBuilder {
    private static final String INDENT_STRING  = "    ";
    private static final String CONTEXT_PREFIX = "ctx_";
    private static final String UNIT_PREFIX    = "unit_";

    private final Filer filer;
    private final MediationConfig config;
    private final MediationModel model;
    private final String dataFlowName;
    private final DataFlow dataFlow;

    public DataFlowBuilder(Filer filer,
                           MediationConfig config,
                           MediationModel model,
                           String dataFlowName,
                           DataFlow dataFlow) {
        this.filer        = filer;
        this.config       = config;
        this.model        = model;
        this.dataFlowName = dataFlowName;
        this.dataFlow     = dataFlow;
    }

    public void write() throws IOException {
        // ****************************
        // Type
        // ****************************
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(dataFlowName)
                .addModifiers(Modifier.PUBLIC);

        dataFlow.getPipelines().forEach((pipelineName, pipeline) -> {
            // ****************************
            // Method
            // ****************************
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(pipelineName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC);

            // ****************************
            // Input Values
            // ****************************
            ofNullable(pipeline.getInputValues()).ifPresent(values -> values.forEach((name, type) ->
                    methodBuilder.addParameter(ClassName.bestGuess(type), CONTEXT_PREFIX + name)
            ));

            // ****************************
            // Code
            // ****************************
            CodeBlock.Builder codeBuilder = CodeBlock.builder();

            codeBuilder.add("// ****************************\n");
            codeBuilder.add("// Init Units\n");
            codeBuilder.add("// ****************************\n");
            ofNullable(pipeline.getUnits()).ifPresent(units -> units.forEach(unitName -> {
                codeBuilder.addStatement("final $1T $2N = new $1T()",
                        ClassName.bestGuess(config.getMandatoryUnitType(unitName)), UNIT_PREFIX + unitName);
            }));

            codeBuilder.add("\n");
            codeBuilder.add("// ****************************\n");
            codeBuilder.add("// Init Context\n");
            codeBuilder.add("// ****************************\n");
            pipeline.getUnits().forEach(unitName ->
                    model.getUnit(UnitClassName.of(config.getMandatoryUnitType(unitName)), CreateIfNotExists.NO)
                            .getAllGetters()
                            .forEach((getterName, getter) ->
                                    codeBuilder.addStatement("$1T $2N",
                                            ClassName.bestGuess(getter.getReturnType()), CONTEXT_PREFIX + getterName))
            );

            codeBuilder.add("\n");
            codeBuilder.add("// ****************************\n");
            codeBuilder.add("// Perform Units\n");
            codeBuilder.add("// ****************************\n");
            //TODO

            codeBuilder.add("\n");
            codeBuilder.add("// ****************************\n");
            codeBuilder.add("// Output Value\n");
            codeBuilder.add("// ****************************\n");
            ofNullable(pipeline.getOutputValue()).ifPresent(outputValue -> {
                ClassName className = ClassName.bestGuess(outputValue.getType());
                codeBuilder.addStatement("return $1N", CONTEXT_PREFIX + outputValue.getName());
                methodBuilder.returns(className);
            });

            // ****************************
            // Finish Method
            // ****************************
            methodBuilder.addCode(codeBuilder.build());
            typeBuilder.addMethod(methodBuilder.build());
        });

        // ****************************
        // Java File
        // ****************************
        JavaFile.builder(dataFlow.getPackageName(), typeBuilder.build())
                .indent(INDENT_STRING)
                .build()
                .writeTo(filer);
    }
}
