package ru.j4j.mediation.compiler.config;

import ru.j4j.mediation.compiler.MediationCompileException;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
@SuppressWarnings("unused")
public final class MediationConfig {
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

    public String getMandatoryUnitType(String unitName) {
        return ofNullable(getUnits().get(unitName))
                .orElseThrow(() -> new MediationCompileException(format("Undefined unit name \"%s\"", unitName)));
    }

    public Map<String, DataFlow> getMediation() {
        if (mediation == null) {
            mediation = new HashMap<>();
        }
        return mediation;
    }
}
