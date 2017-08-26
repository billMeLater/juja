package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Tables extends Command {
    public Tables() {
        this.name = "tables";
        this.INFO = "\tShow list of existing tables";
    }

    @Override
    public List execute(DatabaseManager databaseManager, List params) {
        return databaseManager.showTables(databaseManager);
    }
}
