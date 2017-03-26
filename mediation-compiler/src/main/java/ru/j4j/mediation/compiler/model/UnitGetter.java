package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class UnitGetter {
    private String returnType;

    public String getReturnType() {
        return returnType;
    }

    public UnitGetter setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }
}
