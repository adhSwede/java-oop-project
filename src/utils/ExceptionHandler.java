package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());

    public static void handleSQLException(Exception e,
                                          String action) {
        LOGGER.log(Level.SEVERE,
                "Error while " + action,
                e);
        System.out.println("An error occurred while " + action + ". Please try again later.");
    }

    public static void handleGeneralException(Exception e,
                                              String action) {
        LOGGER.log(Level.SEVERE,
                "Error during " + action,
                e);
        System.out.println("An unexpected error occurred. Please contact support.");
    }

    public static void handleUserError(Exception e,
                                       String action) {
        System.out.println("⚠️  " + e.getMessage());
        LOGGER.log(Level.WARNING,
                "User error during " + action,
                e);
    }
}
