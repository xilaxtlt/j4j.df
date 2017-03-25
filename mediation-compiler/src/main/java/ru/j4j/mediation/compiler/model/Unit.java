package ru.j4j.mediation.compiler.model;

import ru.j4j.mediation.compiler.utils.CreateIfNotExists;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public class Unit {
    private final String unitType;
    private final Map<String, UnitSetter> setters = new HashMap<>();
    private final Map<String, UnitGetter> getters = new HashMap<>();

    public Unit(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }

    public UnitGetter getGetter(String getterName, CreateIfNotExists createIfNotExists) {
        return ModelUtils.get(
                getters,
                getterName,
                UnitGetter::new,
                CreateIfNotExists.YES == createIfNotExists,
                () -> new IllegalStateException(format("Unit \"%s\" has not getter \"%s\"", unitType, getterName)));
    }

    public UnitSetter getSetter(String setterName, CreateIfNotExists createIfNotExists) {
        return ModelUtils.get(
                setters,
                setterName,
                UnitSetter::new,
                CreateIfNotExists.YES == createIfNotExists,
                () -> new IllegalStateException(format("Unit \"%s\" has not setter \"%s\"", unitType, setterName)));
    }
}
