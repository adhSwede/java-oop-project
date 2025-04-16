import controllers.MainController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MainController mainMenu = new MainController();
        mainMenu.runMenu();
    }
}