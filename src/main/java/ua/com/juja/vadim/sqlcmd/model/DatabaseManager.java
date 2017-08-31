package ua.com.juja.vadim.sqlcmd.model;

import ua.com.juja.vadim.sqlcmd.controller.Command;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DatabaseManager {
    List connect(DatabaseManager databaseManager, List params);

    List disconnect(DatabaseManager databaseManager);

    void exit(DatabaseManager databaseManager);

    List help(DatabaseManager databaseManager);

    List createTable(DatabaseManager databaseManager, List params);

    List dropTable(DatabaseManager databaseManager, List params);

    List clearTable(DatabaseManager databaseManager, List params);

    List showTables(DatabaseManager databaseManager);

//    List addRecord(DatabaseManager databaseManager, List params);
//
//    List updateRecord(String params);
//
//    List removeRecord(String params);
//
//    List showRecords(String table);

    List _connectionInfo();

    boolean _isConnected();

    Map<String, Command> _getCommands();

    Connection _getConnection();
}
