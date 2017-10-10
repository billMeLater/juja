package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class UpdateRecords extends Command {

    public UpdateRecords() {
        name = "update";
        info = "\tupdate record(s) for table 'tableName' set 'column2' into 'value2' where 'column1' eq 'value1'";
        defaultParam = "|tableName|column1|value1|column2|value2";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        if (validatedParams(params, defaultParam, false)) {
            return databaseManager.updateRecords(databaseManager, params);
        } else {
            return _usage();
        }
    }

}
