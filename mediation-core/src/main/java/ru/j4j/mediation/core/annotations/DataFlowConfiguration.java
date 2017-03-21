package ru.j4j.mediation.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Artemiy.Shchekotov
 * @since 3/21/2017
 */
@Target({TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface DataFlowConfiguration {
    String value();
}
