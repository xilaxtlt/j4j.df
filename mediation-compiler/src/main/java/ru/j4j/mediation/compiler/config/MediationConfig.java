package ru.j4j.mediation.compiler.config;

import java.util.Map;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class MediationConfig {
    private Map<String, String> units;
    private Map<String, DataFlow> mediations;

    public void setUnits(Map<String, String> units) {
        this.units = units;
    }

    public void setMediations(Map<String, DataFlow> mediations) {
        this.mediations = mediations;
    }

    public Map<String, String> getUnits() {
        return units;
    }

    public Map<String, DataFlow> getMediations() {
        return mediations;
    }
}
