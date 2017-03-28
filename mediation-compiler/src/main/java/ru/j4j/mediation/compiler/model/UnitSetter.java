package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class UnitSetter {
    private String originalSetterName;
    private String argumentType;

    public String getArgumentType() {
        return argumentType;
    }

    public UnitSetter setArgumentType(String argumentType) {
        this.argumentType = argumentType;
        return this;
    }

    public String getOriginalSetterName() {
        return originalSetterName;
    }

    public UnitSetter setOriginalSetterName(String originalSetterName) {
        this.originalSetterName = originalSetterName;
        return this;
    }
}
