package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class RemoveRecords extends Command {

    public RemoveRecords() {
        name = "delete";
        info = "\tRemove record(s) from table 'tableName' with clause 'column'='value' pairs.";
        defaultParam = "|tableName|column1|value1|...|columnN|valueN";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        if (this.validatedParams(params, defaultParam, true)) {
            return databaseManager.removeRecords(databaseManager, params);
        } else {
            return _usage();
        }
    }

}
