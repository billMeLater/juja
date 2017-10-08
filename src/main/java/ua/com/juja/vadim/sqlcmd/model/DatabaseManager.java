package ua.com.juja.vadim.sqlcmd.model;

import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DatabaseManager {
    CommandOutput connect(DatabaseManager databaseManager, List<String> params);

    CommandOutput disconnect(DatabaseManager databaseManager);

    void exit(DatabaseManager databaseManager);

    CommandOutput help(DatabaseManager databaseManager);

    CommandOutput createTable(DatabaseManager databaseManager, List<String> params);

    CommandOutput dropTable(DatabaseManager databaseManager, List<String> params);

    CommandOutput clearTable(DatabaseManager databaseManager, List<String> params);

    CommandOutput showTables(DatabaseManager databaseManager);

    CommandOutput addRecord(DatabaseManager databaseManager, List<String> params);

//    List updateRecord(String params);

//    List removeRecord(String params);

    CommandOutput showRecords(DatabaseManager databaseManager, List<String> params);

    CommandOutput _connectionInfo();

    boolean _isConnected();

    Map<String, Command> _getCommands();

    Connection _getConnection();
}
