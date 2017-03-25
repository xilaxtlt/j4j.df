package ru.j4j.mediation.examples.impl;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class Result {
    private final String resultText;

    public Result(String resultText) {
        this.resultText = resultText;
    }

    public String getResultText() {
        return resultText;
    }

    @Override
    public String toString() {
        return this.resultText;
    }
}
