package controller;

import model.DatabaseManager;
import model.Help;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


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

    private boolean executeCommand(String command) {

        String[] tokens = command.split(" ");
        String commandParams = "";
        for (int i = 1; i < tokens.length; i++) {
            commandParams += tokens[i];
        }

        Method[] declaredMethods = databaseManager.getClass().getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (m.getName().equals(tokens[0])) {
                try {
                    m.invoke(databaseManager, commandParams);
                    return true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void run() {
        view.write("Hi, please type command or ? for help\n");

        while (true) {
            view.write(databaseManager._connectionInfo(""));
            String command = view.read();
            if (command.equals("?")) {
                Help.commandList();
                continue;
            }

            if (!executeCommand(command)) {
                view.write("Command not found!\n type ? for help\n");
            }
        }
    }

}
