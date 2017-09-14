package ru.j4j.mediation.examples.impl;

import ru.j4j.mediation.core.annotations.ToContext;
import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.Unit;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
@SuppressWarnings("unused")
@Unit("eventParametersExtractor")
public class EventParametersExtractor {
    private Event event;

    @FromContext
    public void setEvent(Event event) {
        this.event = event;
    }

    @ToContext
    public String getEventName() {
        return event.getName();
    }

    @ToContext
    public String getEventText() {
        return event.getText();
    }

}
