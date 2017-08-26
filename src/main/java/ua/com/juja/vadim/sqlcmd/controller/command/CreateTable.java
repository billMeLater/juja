package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class CreateTable extends Command {

    public CreateTable() {
        this.name = "create";
        this.DEFAULT_PARAM = "|tableName|fieldName1|...|fieldNameN";
        this.INFO = "\tCreate table 'tableName' with desired fields.";
    }

    @Override
    public List execute(DatabaseManager databaseManager, List params) {
        if (this.validatedParams(params, DEFAULT_PARAM, true)) {
            return databaseManager.createTable(databaseManager, params);
        }
        return this._usage();
    }

}
