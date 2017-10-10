package ua.com.juja.vadim.sqlcmd.controller.command;


import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class ShowRecords extends Command {
    public ShowRecords() {
        name = "find";
        info = "\tshow records from table 'tableName'. "
                + "Extended version: 'find|tableName|limit|offset'. "
                + "Limit and offset are natural numbers and not mandatory, safely to skip both or any one";
        defaultParam = "|tableName";
    }

    @Override
    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        if (this.validatedParams(params, defaultParam, false)) {
            String defaultLimit = "100";
            String defaultOffset = "0";

            if (params.size() == 3) {
                if (params.get(1).isEmpty()) {
                    params.set(1, defaultLimit);
                }
            } else if (params.size() == 2) {
                params.add(2, defaultOffset);
            } else {
                params.add(1, defaultLimit);
                params.add(2, defaultOffset);
            }

            return databaseManager.showRecords(databaseManager, params);
        } else {
            return this._usage();
        }
    }
}
