package ru.j4j.mediation.compiler.model;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class MediationModel {
    private final Map<String, UnitSpec> units = new HashMap<>();

    MediationModel() {
    }

    public void registerUnit(String unitName, UnitSpec unitSpec) {
        UnitSpec registered = units.get(unitName);
        if (registered != null) {
            throw new IllegalStateException(format("UnitSpec by name \"%s\" already exists", unitName));

        }
        units.put(unitName, unitSpec);
    }

    public UnitSpec getUnit(String unitName) {
        return units.get(unitName);
    }

}
