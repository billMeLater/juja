package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class Exit extends Command {
    public Exit() {
        this.name = "exit";
        this.INFO = "\tExit from SQLcmd";
    }

    @Override
    public List execute(DatabaseManager databaseManager, List params) {
        databaseManager.exit(databaseManager);
        return new ArrayList();
    }
}
