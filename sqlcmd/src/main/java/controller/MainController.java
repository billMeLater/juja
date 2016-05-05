package controller;

import model.DatabaseManager;
import view.View;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private View view;
    private DatabaseManager databaseManager;
    private List commands = new ArrayList();

    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;

        Method[] declaredMethods = databaseManager.getClass().getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (!m.getName().startsWith("_")) {
                commands.add(m.getName());
            }
        }

    }

    private List executeCommand(String command) {
        String[] tokens = command.split(" ");
        String commandParams = "";
        for (int i = 1; i < tokens.length; i++) {
            commandParams += tokens[i];
        }
        try {
            return (List) databaseManager.getClass().getDeclaredMethod(tokens[0], new String().getClass())
                                        .invoke(databaseManager, commandParams);
        } catch (Exception e) {
        }

        List result = new ArrayList(1);
        result.add("Execution error!");
        return result;
    }

    public void run() {
        List hello = new ArrayList(1);
        hello.add("Hi, please type command or ? for help");
        view.write(hello);

        while (true) {
            view.write(databaseManager._connectionInfo(""));
            String command = view.read();

            String[] tokens = command.split(" ");
            String commandParams = "";
            for (int i = 1; i < tokens.length; i++) {
                commandParams += tokens[i];
            }

            if (command.equals("?")) {
                for (Object commandName : commands) {
                    view.write(executeCommand(commandName+" _usage"));
                }
                continue;
            }

            if (commands.contains(tokens[0])) {
                view.write(executeCommand(command));
            } else {
                List help = new ArrayList(1);
                help.add("command not found");
                view.write(help);
            }
        }
    }
}
