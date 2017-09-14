package ru.j4j.mediation.compiler.utils;

import java.util.function.Supplier;

public class MethodUtils {

    public static String cutMethodPrefix(String prefix, String methodName, Supplier<RuntimeException> exceptionSupplier) {
        int nameLen   = methodName.length();
        int prefixLen = prefix.length();
        if (nameLen < prefixLen + 1) {
            throw exceptionSupplier.get();
        }
        return methodName.substring(prefixLen, nameLen);
    }

}
