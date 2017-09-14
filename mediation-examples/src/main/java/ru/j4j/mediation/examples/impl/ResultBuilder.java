package ru.j4j.mediation.examples.impl;

import ru.j4j.mediation.core.annotations.FromContext;
import ru.j4j.mediation.core.annotations.RunnableMethod;
import ru.j4j.mediation.core.annotations.ToContext;
import ru.j4j.mediation.core.annotations.Unit;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
@Unit("resultBuilder")
public class ResultBuilder {
    private Event event;
    private Result result;

    @FromContext
    public void setEvent(Event event) {
        this.event = event;
    }

    @ToContext
    public Result getResult() {
        return this.result;
    }

    @RunnableMethod
    public void buildResult() {
        this.result = new Result("result: " + event.getText());
    }

}
