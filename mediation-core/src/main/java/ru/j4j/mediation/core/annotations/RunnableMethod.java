package ru.j4j.mediation.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
@Target(METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface RunnableMethod {
}
