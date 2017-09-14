package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class UnitGetter {
    private final String originalGetterName;
    private final String returnClassName;

    UnitGetter(String originalGetterName, String returnClassName) {
        this.originalGetterName = originalGetterName;
        this.returnClassName    = returnClassName;
    }

    public String getReturnClassName() {
        return returnClassName;
    }

    public String getOriginalGetterName() {
        return originalGetterName;
    }

}
