package ua.com.juja.vadim.sqlcmd.controller.command;

import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Clear extends Command {
    public Clear() {
        this.name = "clear";
        this.DEFAULT_PARAM = "|tableName";
        this.INFO = "\tRemove all records from 'tableName'";
    }

    @Override
    public List execute(DatabaseManager databaseManager, List params) {
        if (this.validatedParams(params, DEFAULT_PARAM)) {
            return databaseManager.clearTable(databaseManager, params);
        }
        return this._usage();
    }
}
