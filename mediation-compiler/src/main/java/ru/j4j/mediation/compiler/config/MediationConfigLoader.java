package ru.j4j.mediation.compiler.config;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Artemiy.Shchekotov
 * @since 4/9/2017
 */
public final class MediationConfigLoader {
    private static final String DEFAULT_CONFIG_FILE_NAME = "j4j-mediation.yaml";

    public MediationConfig load() {
        ClassLoader classLoader = MediationConfigLoader.class.getClassLoader();
        return new Yaml().loadAs(classLoader.getResourceAsStream(DEFAULT_CONFIG_FILE_NAME), MediationConfig.class);
    }

}
