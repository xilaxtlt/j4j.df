package ru.j4j.mediation.compiler.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
@SuppressWarnings("unused")
public class MediationConfig {
    private Map<String, String> units;
    private Map<String, DataFlow> mediation;

    public void setUnits(Map<String, String> units) {
        this.units = units;
    }

    public void setMediation(Map<String, DataFlow> mediation) {
        this.mediation = mediation;
    }

    public Map<String, String> getUnits() {
        if (units == null) {
            units = new HashMap<>();
        }
        return units;
    }

    public Map<String, DataFlow> getMediation() {
        if (mediation == null) {
            mediation = new HashMap<>();
        }
        return mediation;
    }
}
