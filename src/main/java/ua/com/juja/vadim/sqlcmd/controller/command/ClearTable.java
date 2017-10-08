package ua.com.juja.vadim.sqlcmd.controller.command;

import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class ClearTable extends Command {
    public ClearTable() {
        name = "clear";
        defaultParam = "|tableName";
        info = "\tRemove all records from 'tableName'";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List params) {
        if (this.validatedParams(params, defaultParam)) {
            return databaseManager.clearTable(databaseManager, params);
        } else {
            return this._usage();
        }
    }
}
