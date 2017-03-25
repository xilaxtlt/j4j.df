package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class UnitGetter {
    private String name;
    private String returnType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
