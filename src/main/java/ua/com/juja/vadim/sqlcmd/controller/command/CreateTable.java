package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class CreateTable extends Command {

    public CreateTable() {
        name = "create";
        defaultParam = "|tableName|fieldName1|...|fieldNameN";
        info = "\tCreate table 'tableName' with desired fields.";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        if (this.validatedParams(params, defaultParam, true)) {
            return databaseManager.createTable(databaseManager, params);
        } else {
            return this._usage();
        }
    }

}
