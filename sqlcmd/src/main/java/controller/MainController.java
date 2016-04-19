package controller;

import model.DatabaseManager;
import model.Help;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private View view;
    private DatabaseManager databaseManager;

    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    private List executeCommand(String command) {
        String[] tokens = command.split(" ");
        String commandParams = "";
        for (int i = 1; i < tokens.length; i++) {
            commandParams += tokens[i];
        }

        Method[] declaredMethods = databaseManager.getClass().getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (m.getName().equals(tokens[0])) {
                try {
                    return (List) m.invoke(databaseManager, commandParams);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        List result = new ArrayList();
        result.add("Command not found!");
        result.add("type ? for help");
        return result;
    }

    public void run() {
        List hello = new ArrayList(1);
        hello.add("Hi, please type command or ? for help");
        view.write(hello);

        while (true) {
            view.write(databaseManager._connectionInfo(""));
            String command = view.read();
            if (command.equals("?")) {
                view.write(Help.commandList());
                continue;
            }

            view.write(executeCommand(command));
        }
    }
}
