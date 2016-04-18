package controller;

import model.DatabaseManager;
import model.MySQLDatabaseManager;
import view.Cosole;
import view.View;

/**
 * Created by v.kalitsev on 3/25/2016.
 */
public class Main {

    public static void main(String[] args) {


        View view = new Cosole();
        DatabaseManager databaseManager = new MySQLDatabaseManager("", "");

        MainController mainController = new MainController(view, databaseManager);
        mainController.run();

    }

}
