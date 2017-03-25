package ru.j4j.mediation.compiler.model;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class UnitClassName {
    private final String className;

    public static UnitClassName of(String className) {
        return new UnitClassName(className);
    }

    private UnitClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitClassName that = (UnitClassName) o;

        return className != null ? className.equals(that.className) : that.className == null;
    }

    @Override
    public int hashCode() {
        return className != null ? className.hashCode() : 0;
    }
}
