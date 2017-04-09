package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class UnitGetter {
    private String originalGetterName;
    private String returnType;

    UnitGetter() {
    }

    public String getReturnType() {
        return returnType;
    }

    UnitGetter setReturnType(String returnType) {
        this.returnType = returnType;
        return this;
    }

    public String getOriginalGetterName() {
        return originalGetterName;
    }

    UnitGetter setOriginalGetterName(String originalGetterName) {
        this.originalGetterName = originalGetterName;
        return this;
    }
}
