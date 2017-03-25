package ru.j4j.mediation.examples.impl;

import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;

import static java.lang.String.format;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
@SuppressWarnings("unused")
public class EventNamePrinter {
    private String eventName;
    private String eventText;

    @FromContext
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @FromContext
    public void setEventText(String eventText) {
        this.eventText = eventText;
    }

    @RunnableMethod
    public void printEvent() {
        System.out.println(format("event: eventName=%s, eventText=%s", eventName, eventText));
    }

}
