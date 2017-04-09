package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class UnitSetter {
    private String originalSetterName;
    private String argumentType;

    UnitSetter() {
    }

    public String getArgumentType() {
        return argumentType;
    }

    UnitSetter setArgumentType(String argumentType) {
        this.argumentType = argumentType;
        return this;
    }

    public String getOriginalSetterName() {
        return originalSetterName;
    }

    UnitSetter setOriginalSetterName(String originalSetterName) {
        this.originalSetterName = originalSetterName;
        return this;
    }
}
