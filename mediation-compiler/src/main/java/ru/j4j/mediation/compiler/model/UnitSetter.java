package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class UnitSetter {
    private final String originalSetterName;
    private final String argumentType;

    UnitSetter(String originalSetterName, String argumentType) {
        this.originalSetterName = originalSetterName;
        this.argumentType       = argumentType;
    }

    public String getArgumentType() {
        return argumentType;
    }

    public String getOriginalSetterName() {
        return originalSetterName;
    }

}
