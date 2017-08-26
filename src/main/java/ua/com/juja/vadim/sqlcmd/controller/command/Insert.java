package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Insert extends Command {

    public Insert() {
        this.name = "insert";
        this.INFO = "\tInsert record into table 'tableName' with 'column'='value' pairs."
                + " 'tableName' or 'column' can't be empty.";
        this.DEFAULT_PARAM = "|tableName|column1|value1|...|columnN|valueN";
    }

    @Override
    public List execute(DatabaseManager databaseManager, List params) {
        if (this.validatedParams(params, DEFAULT_PARAM, true)) {
            return databaseManager.connect(databaseManager, params);
        }
        return this._usage();
    }

}
