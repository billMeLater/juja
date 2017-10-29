package ua.com.juja.vadim.sqlcmd.service;

import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class ServiceImpl implements Service {
    @Override
    public List<String> commandsList() {
        return null;
    }

    @Override
    public CommandOutput connect(DatabaseManager databaseManager, List<String> params) {
        return databaseManager.connect(databaseManager, params);
    }


}
