package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/28/2017
 */
public class Command {
    private String originalName;

    public String getOriginalName() {
        return originalName;
    }

    public Command setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }
}
