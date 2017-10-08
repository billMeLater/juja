package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Exit extends Command {
    public Exit() {
        this.name = "exit";
        this.info = "\tExit from SQLcmd";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        databaseManager.exit(databaseManager);
        return new CommandOutput();
    }
}
