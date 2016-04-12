package controller;

import model.DatabaseManager;
import model.Help;
import model.MySQLDatabaseManager;
import model.Utils;
import view.View;

import java.util.Scanner;

/**
 * Created by vadim on 4/12/16.
 */
public class MainController {

    private View view;
    private DatabaseManager databaseManager;

    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    public void run() {
        System.out.println("Hi, please type command or ? for help ");
        Scanner userinput = new Scanner(System.in);
        DatabaseManager mydb = new MySQLDatabaseManager("", "");

        while (true) {
            System.out.print(mydb._connectionInfo(""));
            String command = userinput.nextLine();
            if (command.equals("?")) {
                Help.commandList();
            }
            if (!Utils.readline(mydb, command)) {
                System.out.println("Command not found!");
            }
        }
    }

}
