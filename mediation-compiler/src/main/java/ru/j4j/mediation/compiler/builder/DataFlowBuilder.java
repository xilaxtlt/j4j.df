package ru.j4j.mediation.compiler.builder;

import com.squareup.javapoet.*;
import ru.j4j.mediation.compiler.config.DataFlow;
import ru.j4j.mediation.compiler.config.MediationConfig;
import ru.j4j.mediation.compiler.model.MediationModel;
import ru.j4j.mediation.compiler.model.Unit;
import ru.j4j.mediation.compiler.model.UnitClassName;
import ru.j4j.mediation.compiler.utils.CreateIfNotExists;

import javax.lang.model.element.Modifier;

import static java.util.Optional.ofNullable;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
final class DataFlowBuilder {
    private static final String INDENT_STRING  = "    ";
    private static final String CONTEXT_PREFIX = "ctx_";
    private static final String UNIT_PREFIX    = "unit_";

    private final MediationConfig config;
    private final MediationModel model;
    private final String dataFlowName;
    private final DataFlow dataFlow;

    DataFlowBuilder(MediationConfig config,
                    MediationModel model,
                    String dataFlowName,
                    DataFlow dataFlow) {
        this.config       = config;
        this.model        = model;
        this.dataFlowName = dataFlowName;
        this.dataFlow     = dataFlow;
    }

    JavaFile build() {
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
            ofNullable(pipeline.getUnits()).ifPresent(units -> units.forEach(unitName -> {
                String unitType = config.getMandatoryUnitType(unitName);
                Unit   unit     = model.getUnit(UnitClassName.of(unitType), CreateIfNotExists.NO);
                unit.getAllSetters().forEach((setterName, setter) -> {
                    codeBuilder.addStatement("$1N.$2L($3N)",
                            UNIT_PREFIX + unitName,
                            setter.getOriginalSetterName(),
                            CONTEXT_PREFIX + setterName);
                });
                unit.getAllCommands().forEach((commandName, command) -> {
                    codeBuilder.addStatement("$1N.$2L()",
                            UNIT_PREFIX + unitName,
                            command.getOriginalName());
                });
                unit.getAllGetters().forEach((getterName, getter) -> {
                    codeBuilder.addStatement("$1N = $2N.$3L()",
                            CONTEXT_PREFIX + getterName,
                            UNIT_PREFIX + unitName,
                            getter.getOriginalGetterName());
                });
            }));

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
        return JavaFile.builder(dataFlow.getPackageName(), typeBuilder.build())
                .indent(INDENT_STRING)
                .build();
    }
}
