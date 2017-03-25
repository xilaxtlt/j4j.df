package ru.j4j.mediation.examples.impl;

/**
 * @author Artemiy.Shchekotov
 * @since 3/22/2017
 */
public final class RunDataFlowPipeLine {

    private RunDataFlowPipeLine() {
    }

    public static void main(String[] args) {
        Result result = _ExampleDataFlow_.exampleLine(new Event("ExampleEvent", "this is event text"));
        System.out.println(result);
    }

}
