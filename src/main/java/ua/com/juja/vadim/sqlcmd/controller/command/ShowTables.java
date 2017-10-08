package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class ShowTables extends Command {
    public ShowTables() {
        name = "tables";
        info = "\tShow list of existing tables";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        return databaseManager.showTables(databaseManager);
    }
}
