package ua.com.juja.vadim.sqlcmd.controller.command;

import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Help extends Command {
    public Help() {
        this.name = "help";
        this.info = "\tList of commands with description";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        return databaseManager.help(databaseManager);
    }
}
