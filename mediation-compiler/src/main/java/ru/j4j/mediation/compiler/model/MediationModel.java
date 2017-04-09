package ru.j4j.mediation.compiler.model;

import ru.j4j.mediation.compiler.utils.CreateIfNotExists;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class MediationModel {
    private final Map<UnitClassName, Unit> units = new HashMap<>();

    MediationModel() {
    }

    public Unit getUnit(UnitClassName name, CreateIfNotExists createIfNotExists) {
        return ModelUtils.get(
                units,
                name,
                () -> new Unit(name.toString()),
                CreateIfNotExists.YES == createIfNotExists,
                () -> new IllegalStateException(format("Unit by type \"%s\" is not found", name))
        );
    }

}
