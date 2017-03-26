package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class UnitSetter {
    private String type;

    public String getArgumentType() {
        return type;
    }

    public UnitSetter setArgumentType(String type) {
        this.type = type;
        return this;
    }
}
