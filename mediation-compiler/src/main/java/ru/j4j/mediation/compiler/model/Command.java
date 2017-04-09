package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/28/2017
 */
public final class Command {
    private String originalName;

    Command() {
    }

    public String getOriginalName() {
        return originalName;
    }

    Command setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }
}
