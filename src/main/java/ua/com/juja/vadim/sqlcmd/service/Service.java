package ua.com.juja.vadim.sqlcmd.service;

import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public interface Service {
    List<String> commandsList();

    CommandOutput connect(DatabaseManager databaseManager, List<String> params);
}
