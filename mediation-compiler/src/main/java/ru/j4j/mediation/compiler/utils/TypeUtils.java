package ru.j4j.mediation.compiler.utils;

public class TypeUtils {

    public static String extractClassName(String className) {
        int lastIndex = className.lastIndexOf('.');
        return className.substring(lastIndex >= 0 ? lastIndex : 0);
    }

}
