package ua.com.juja.vadim.sqlcmd.controller;


import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;
import ua.com.juja.vadim.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainController {
    private View view;
    private DatabaseManager databaseManager;
    private Map<String, Command> commands;

    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
        this.commands = this.databaseManager._getCommands();
    }

    public void run() {
        List hello = new ArrayList(1);
        String helpCommand = "? for help";
        hello.add("enter a command or " + helpCommand);
        view.write(hello);

        while (true) {
            view.write(databaseManager._connectionInfo());
            List<String> tokens = new ArrayList<>(Arrays.asList(view.read().split("\\|")));
            String userCommand = "";

            if (tokens.size() > 0) {
                userCommand = tokens.get(0);
                tokens.remove(0);
                if (userCommand.equals("?")) {
                    userCommand = "help";
                }
            }

            if (commands.containsKey(userCommand)) {
                view.write(commands.get(userCommand).execute(databaseManager, tokens));
            } else {
                List help = new ArrayList(1);
                help.add("command not found. (" + helpCommand + ")");
                view.write(help);
            }
        }
    }
}
