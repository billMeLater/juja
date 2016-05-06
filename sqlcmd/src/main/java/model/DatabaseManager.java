package model;

import java.sql.Connection;
import java.util.List;

public interface DatabaseManager {
    List connect(String params);

    List disconnect(String params);

    List exit(String params);

//    List createTable(String params);

//    List dropTable(String params);

    List showTables(String params);

    List addRecord(String params);

    List removeRecord(String params);

    List showRecords(String table);

    List _usage(String methodName, String parameter, String info);

    List _connectionInfo(String string);

    boolean _isConnected();

    Connection _getConnection();
}
