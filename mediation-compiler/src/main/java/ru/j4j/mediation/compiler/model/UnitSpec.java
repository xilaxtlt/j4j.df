package ru.j4j.mediation.compiler.model;

import ru.j4j.mediation.compiler.MediationCompileException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class UnitSpec {
    private final String unitClassName;
    private final Map<String, UnitSetter> setters = new HashMap<>();
    private final Map<String, UnitGetter> getters = new HashMap<>();
    private final Map<String, UnitMethod> methods = new HashMap<>();

    UnitSpec(String unitClassName) {
        this.unitClassName = unitClassName;
    }

    public String getUnitClassName() {
        return unitClassName;
    }

    public Set<String> getAllUnitGettersNames() {
        return Collections.unmodifiableSet(getters.keySet());
    }

    public Set<String> getAllUnitSettersNames() {
        return Collections.unmodifiableSet(setters.keySet());
    }

    public Set<String> getAllUnitMethodsNames() {
        return Collections.unmodifiableSet(methods.keySet());
    }

    void registerGetter(String getterName, UnitGetter unitGetter) {
        if (getters.containsKey(getterName)) {
            throw new MediationCompileException(format("Unit getter \"%s\" already exists", getterName));
        }
        getters.put(getterName, unitGetter);
    }

    public UnitGetter getUnitGetter(String getterName) {
        return getters.get(getterName);
    }

    void registerSetter(String setterName, UnitSetter unitSetter) {
        if (setters.containsKey(setterName)) {
            throw new MediationCompileException(format("Unit setter \"%s\" already exists", setterName));
        }
        setters.put(setterName, unitSetter);
    }

    public UnitSetter getUnitSetter(String setterName) {
        return setters.get(setterName);
    }

    void registerUnitMethod(String commandName, UnitMethod unitMethod) {
        if (setters.containsKey(commandName)) {
            throw new MediationCompileException(format("Unit command \"%s\" already exists", commandName));
        }
        methods.put(commandName, unitMethod);
    }

    public UnitMethod getUnitMethod(String unitMethod) {
        return methods.get(unitMethod);
    }

}
