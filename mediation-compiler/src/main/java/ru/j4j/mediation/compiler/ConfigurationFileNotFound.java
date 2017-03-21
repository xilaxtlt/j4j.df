package ru.j4j.mediation.compiler;

/**
 * @author Artemiy.Shchekotov
 * @since 3/22/2017
 */
public class ConfigurationFileNotFound extends RuntimeException {

    public ConfigurationFileNotFound(String message) {
        super(message);
    }

}
