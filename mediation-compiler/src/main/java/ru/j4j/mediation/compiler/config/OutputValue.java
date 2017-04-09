package ru.j4j.mediation.compiler.config;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class OutputValue {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }
}
