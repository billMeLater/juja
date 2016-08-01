package ua.com.juja.vadim.sqlcmd;

import ua.com.juja.vadim.sqlcmd.controller.MainController;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;
import ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManager;
import ua.com.juja.vadim.sqlcmd.view.Console;
import ua.com.juja.vadim.sqlcmd.view.View;

public class Main {

    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager databaseManager = new MySQLDatabaseManager();

        MainController mainController = new MainController(view, databaseManager);
        mainController.run();
    }
}
