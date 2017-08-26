package ua.com.juja.vadim.sqlcmd.controller.command;

import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Drop extends Command {
    public Drop() {
        this.name = "drop";
        this.DEFAULT_PARAM = "|tableName";
        this.INFO = "\tRemove table 'tableName'";
    }

    @Override
    public List execute(DatabaseManager databaseManager, List params) {
        if (this.validatedParams(params, DEFAULT_PARAM)) {
            return databaseManager.dropTable(databaseManager, params);
        }
        return this._usage();
    }
}
