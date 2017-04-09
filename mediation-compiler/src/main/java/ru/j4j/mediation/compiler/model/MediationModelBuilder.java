package ru.j4j.mediation.compiler.model;

import ru.j4j.mediation.compiler.MediationCompileException;
import ru.j4j.mediation.compiler.utils.CreateIfNotExists;
import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;
import ru.j4j.mediation.core.annotations.ToContext;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Artemiy.Shchekotov
 * @since 4/9/2017
 */
public final class MediationModelBuilder {

    private final RoundEnvironment roundEnv;

    public MediationModelBuilder(RoundEnvironment roundEnv) {
        this.roundEnv = roundEnv;
    }

    public MediationModel build() {
        MediationModel model = new MediationModel();
        fillModelByGetters(model);
        fillModelBySetters(model);
        fillModelByCommands(model);
        return model;
    }


    private void fillModelByGetters(MediationModel model) {
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

    private void fillModelBySetters(MediationModel model) {
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

    private void fillModelByCommands(MediationModel model) {
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
