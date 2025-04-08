package Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());

    public static void handleSQLException(Exception e, String action) {
        LOGGER.log(Level.SEVERE, "Error while " + action, e);
        System.out.println("An error occurred while " + action + ". Please try again later.");
    }

    public static void handleGeneralException(Exception e, String action) {
        LOGGER.log(Level.SEVERE, "Error during " + action, e);
        System.out.println("An unexpected error occurred. Please contact support.");
    }
}
