package ru.j4j.mediation.compiler.model;

import ru.j4j.mediation.compiler.utils.CreateIfNotExists;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class Unit {
    private final String unitType;
    private final Map<String, UnitSetter> setters = new HashMap<>();
    private final Map<String, UnitGetter> getters = new HashMap<>();
    private final Map<String, Command> commands = new HashMap<>();

    Unit(String unitType) {
        this.unitType = unitType;
    }

    public Map<String, UnitGetter> getAllGetters() {
        return Collections.unmodifiableMap(getters);
    }

    public Map<String, UnitSetter> getAllSetters() {
        return Collections.unmodifiableMap(setters);
    }

    public Map<String, Command> getAllCommands() {
        return Collections.unmodifiableMap(commands);
    }

    UnitGetter getGetter(String getterName, CreateIfNotExists createIfNotExists) {
        return ModelUtils.get(
                getters,
                getterName,
                UnitGetter::new,
                CreateIfNotExists.YES == createIfNotExists,
                () -> new IllegalStateException(format("Unit \"%s\" has not getter \"%s\"", unitType, getterName)));
    }

    UnitSetter getSetter(String setterName, CreateIfNotExists createIfNotExists) {
        return ModelUtils.get(
                setters,
                setterName,
                UnitSetter::new,
                CreateIfNotExists.YES == createIfNotExists,
                () -> new IllegalStateException(format("Unit \"%s\" has not setter \"%s\"", unitType, setterName)));
    }

    Command getCommand(String commandName, CreateIfNotExists createIfNotExists) {
        return ModelUtils.get(
                commands,
                commandName,
                Command::new,
                CreateIfNotExists.YES == createIfNotExists,
                () -> new IllegalStateException(
                        format("Unit \"%s\" has not runnable method \"%s\"", unitType, commandName)));
    }
}
