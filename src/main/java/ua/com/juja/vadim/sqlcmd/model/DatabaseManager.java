package ua.com.juja.vadim.sqlcmd.model;

import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DatabaseManager {
    CommandOutput connect(DatabaseManager databaseManager, List params);

    CommandOutput disconnect(DatabaseManager databaseManager);

    void exit(DatabaseManager databaseManager);

    CommandOutput help(DatabaseManager databaseManager);

    CommandOutput createTable(DatabaseManager databaseManager, List params);

    CommandOutput dropTable(DatabaseManager databaseManager, List params);

    CommandOutput clearTable(DatabaseManager databaseManager, List params);

    CommandOutput showTables(DatabaseManager databaseManager);

    CommandOutput addRecord(DatabaseManager databaseManager, List params);

//    List updateRecord(String params);

//    List removeRecord(String params);

//    List showRecords(String table);

    CommandOutput _connectionInfo();

    boolean _isConnected();

    Map<String, Command> _getCommands();

    Connection _getConnection();
}
