package ru.j4j.mediation.compiler.utils;

/**
 * @author Artemiy.Shchekotov
 * @since 3/25/2017
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static void tryIt(ThrowableCommand command) {
        try {
            command.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public interface ThrowableCommand {

        void run() throws Exception;

    }

}
