package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Connect extends Command {

    public Connect() {
        this.name = "connect";
        this.INFO = "\tConnect to DB. All parameters mandatory";
        this.DEFAULT_PARAM = "|dbName|dbUser|password";
    }

    @Override
    public List execute(DatabaseManager databaseManager, List params) {
        if (this.validatedParams(params, DEFAULT_PARAM)) {
            return databaseManager.connect(databaseManager, params);
        }
        return this._usage();
    }

}
