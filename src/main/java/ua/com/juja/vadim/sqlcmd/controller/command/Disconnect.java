package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Disconnect extends Command {

    public Disconnect() {
        name = "disconnect";
        info = "\tClose connection to DB";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List params) {
        return databaseManager.disconnect(databaseManager);
    }
}
