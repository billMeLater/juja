package model;

/**
 * Created by vadim on 4/12/16.
 */
public interface DatabaseManager {
    void connect(String params);

    void disconnect(String params);

    void exit(String params);

    void listTables(String params);

    void showRecords(String table);

    String _connectionInfo(String string);
}
