package ru.j4j.mediation.examples.impl;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class _ExampleDataFlow_ {

    public static Result examplePipeline(Event ctx_event) {
        final EventParametersExtractor unit_eventParametersExtractor = new EventParametersExtractor();
        final EventNamePrinter unit_eventNamePrinter = new EventNamePrinter();
        final ResultBuilder unit_resultBuilder = new ResultBuilder();

        String ctx_eventName;
        String ctx_eventText;
        Result ctx_result;

        unit_eventParametersExtractor.setEvent(ctx_event);
        ctx_eventName = unit_eventParametersExtractor.getEventName();
        ctx_eventText = unit_eventParametersExtractor.getEventText();

        unit_eventNamePrinter.setEventName(ctx_eventName);
        unit_eventNamePrinter.setEventText(ctx_eventText);
        unit_eventNamePrinter.printEvent();

        unit_resultBuilder.setEvent(ctx_event);
        unit_resultBuilder.buildResult();
        ctx_result = unit_resultBuilder.getResult();

        return ctx_result;
    }

}
