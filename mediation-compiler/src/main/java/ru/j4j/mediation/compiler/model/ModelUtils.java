package ru.j4j.mediation.compiler.model;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
final class ModelUtils {

    private ModelUtils() {
    }

    static <K, V, E extends RuntimeException> V get(Map<K, V> map,
                                                    K key,
                                                    Supplier<V> valueSupplier,
                                                    boolean createIfNotExists,
                                                    Supplier<E> exceptionSupplier) {
        V result = map.get(key);
        if (result == null) {
            if (!createIfNotExists) {
                throw exceptionSupplier.get();
            }
            result = valueSupplier.get();
            map.put(key, result);
        }
        return result;
    }

}
