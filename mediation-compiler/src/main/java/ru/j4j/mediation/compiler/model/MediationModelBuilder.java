package ru.j4j.mediation.compiler.model;

import ru.j4j.mediation.compiler.MediationCompileException;
import ru.j4j.mediation.compiler.utils.CreateIfNotExists;
import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;
import ru.j4j.mediation.core.annotations.ToContext;
import ru.j4j.mediation.core.annotations.Unit;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

import static java.lang.String.format;
import static ru.j4j.mediation.compiler.utils.MethodUtils.cutMethodPrefix;
import static ru.j4j.mediation.compiler.utils.TypeUtils.extractClassName;

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
        fillModelByMethods(model);
        return model;
    }

    // ***************************************************************************************************************
    // Getters
    // ***************************************************************************************************************

    private void fillModelByGetters(MediationModel model) {
        roundEnv.getElementsAnnotatedWith(ToContext.class)
                .stream()
                .map(ExecutableElement.class::cast)
                .forEach(element -> {

                    Element  type               = element.getEnclosingElement();
                    String   unitClassName      = type.toString();
                    String   unitName           = getUnitName(type, unitClassName);
                    UnitSpec unitSpec           = getUnitSpec(model, unitName, unitClassName);
                    String   returnClassName    = element.getReturnType().toString();
                    String   originalGetterName = element.getSimpleName().toString();
                    String   getterName         = getGetterName(originalGetterName, unitClassName);

                    unitSpec.registerGetter(getterName, new UnitGetter(originalGetterName, returnClassName));

                });
    }

    private String getGetterName(String originalGetterName, String unitClassName) {
        if (originalGetterName.indexOf("get") == 0) {
            return cutMethodPrefix("get", originalGetterName, () -> new MediationCompileException(
                    format("getter \"%s\" of unit \"%s\" must have size > 3", originalGetterName, unitClassName)));
        } else if (originalGetterName.indexOf("is") == 0) {
            return cutMethodPrefix("is", originalGetterName, () -> new MediationCompileException(
                    format("getter \"%s\" of unit \"%s\" must have size > 2", originalGetterName, unitClassName)));
        }
        return originalGetterName;
    }

    // ***************************************************************************************************************
    // Setters
    // ***************************************************************************************************************

    private void fillModelBySetters(MediationModel model) {
        roundEnv.getElementsAnnotatedWith(FromContext.class)
                .stream()
                .map(ExecutableElement.class::cast)
                .forEach(element -> {

                    Element  type               = element.getEnclosingElement();
                    String   unitClassName      = type.toString();
                    UnitSpec unitSpec           = getUnitSpec(model, unitClassName, unitClassName);
                    String   originalSetterName = element.getSimpleName().toString();
                    String   setterName         = getSetterName(originalSetterName, unitClassName);
                    String   setterArgType      = getSetterArgumentType(element, setterName, unitClassName);

                    unitSpec.registerSetter(setterName, new UnitSetter(originalSetterName, setterArgType));

                });
    }

    private String getSetterArgumentType(ExecutableElement element, String setterName, String unitClassName) {
        List<? extends VariableElement> parameters = element.getParameters();
        if (parameters == null || parameters.size() != 1) {
            String message = format(
                    "setter \"%s\" of unit \"%s\" must have one argument only",
                    setterName, unitClassName);
            throw new MediationCompileException(message);
        }
        return parameters.get(0).asType().toString();
    }

    private String getSetterName(String originalSetterName, String unitClassName) {
        if (originalSetterName.indexOf("set") == 0) {
            return cutMethodPrefix("set", originalSetterName, () -> new MediationCompileException(
                    format("setter \"%s\" of unit \"%s\" must have size > 3", originalSetterName, unitClassName)));
        }
        return originalSetterName;
    }

    // ***************************************************************************************************************
    // Methods
    // ***************************************************************************************************************

    private void fillModelByMethods(MediationModel model) {
        roundEnv.getElementsAnnotatedWith(RunnableMethod.class)
                .stream()
                .map(ExecutableElement.class::cast)
                .forEach(element -> {

                    Element type         = element.getEnclosingElement();
                    String  unitName     = type.toString();
                    String  originalName = element.getSimpleName().toString();

                    model.getUnit(UnitClass.of(unitName), CreateIfNotExists.YES)
                            .getCommand(originalName, CreateIfNotExists.YES)
                            .setOriginalName(originalName);

                });
    }

    // ***************************************************************************************************************
    // Utils
    // ***************************************************************************************************************

    private String getUnitName(Element type, String  unitClassName) {
        Unit unit = type.getAnnotation(Unit.class);
        return unit == null || unit.value().isEmpty() ? extractClassName(unitClassName) : unit.value();
    }

    private UnitSpec getUnitSpec(MediationModel model, String unitName, String  unitClassName) {
        UnitSpec unitSpec = model.getUnit(unitName);
        if (unitSpec == null) {
            unitSpec = new UnitSpec(unitClassName);
            model.registerUnit(unitName, unitSpec);
        }
        return unitSpec;
    }

}
