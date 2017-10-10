package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class AddRecord extends Command {

    public AddRecord() {
        name = "insert";
        info = "\tAddRecord record into table 'tableName' with 'column'='value' pairs,"
                + " 'tableName' or 'column' can't be empty";
        defaultParam = "|tableName|column1|value1|...|columnN|valueN";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        if (this.validatedParams(params, defaultParam, true)) {
            return databaseManager.addRecord(databaseManager, params);
        } else {
            return this._usage();
        }
    }

}
