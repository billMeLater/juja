package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;
import ua.com.juja.vadim.sqlcmd.validator.DBname;
import ua.com.juja.vadim.sqlcmd.validator.DBpass;
import ua.com.juja.vadim.sqlcmd.validator.DBuser;

import java.util.List;

public class Connect extends Command {

    public Connect() {
        name = "connect";
        info = "\tConnect to DB. All parameters mandatory";
        defaultParam = "|dbName|dbUser|password";

    }

    public CommandOutput webExecute(DatabaseManager databaseManager, List<String> params) {
        if (this.validParams(params)) {
            return databaseManager.connect(databaseManager, params);
        } else {
            return this._usage();
        }
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        if (this.validatedParams(params, defaultParam, false)) {
            return databaseManager.connect(databaseManager, params);
        } else {
            return this._usage();
        }
    }

    protected boolean validParams(List<String> params) {
        if ((params.size() == 3)) {
            DBname dbName = new DBname();
            DBuser dbUser = new DBuser();
            DBpass dbPass = new DBpass();

            return dbName.valid(params.get(0)) || dbUser.valid(params.get(1)) || dbPass.valid(params.get(2));
        } else {
            return false;
        }
    }
}
