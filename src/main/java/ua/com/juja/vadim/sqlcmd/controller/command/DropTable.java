package ua.com.juja.vadim.sqlcmd.controller.command;

import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class DropTable extends Command {
    public DropTable() {
        name = "drop";
        defaultParam = "|tableName";
        info = "\tRemove table 'tableName'";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        if (this.validatedParams(params, defaultParam)) {
            return databaseManager.dropTable(databaseManager, params);
        } else {
            return this._usage();
        }
    }
}
