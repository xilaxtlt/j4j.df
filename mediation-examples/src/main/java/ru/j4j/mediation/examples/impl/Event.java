package ru.j4j.mediation.examples.impl;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class Event {
    private final String name;
    private final String text;

    public Event(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
