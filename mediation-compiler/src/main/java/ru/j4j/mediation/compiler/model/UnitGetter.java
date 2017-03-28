package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class UnitGetter {
    private String originalGetterName;
    private String returnType;

    public String getReturnType() {
        return returnType;
    }

    public UnitGetter setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }

    public String getOriginalGetterName() {
        return originalGetterName;
    }

    public UnitGetter setOriginalGetterName(String originalGetterName) {
        this.originalGetterName = originalGetterName;
        return this;
    }
}
