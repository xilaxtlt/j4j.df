package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/28/2017
 */
public final class UnitMethod {
    private String originalName;

    UnitMethod() {
    }

    public String getOriginalName() {
        return originalName;
    }

    UnitMethod setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }
}
