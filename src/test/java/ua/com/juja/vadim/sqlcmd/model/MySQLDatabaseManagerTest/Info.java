package ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManagerTest;

import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;
import ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManager;

public class Info {
    private DatabaseManager manager;
    private String DB_NAME;
    private String DB_USER;
    private String DB_PASS;

    public Info() {
        this.manager = new MySQLDatabaseManager();
        this.DB_NAME = "juja";
        this.DB_USER = "jujauser";
        this.DB_PASS = "password";
    }

    public DatabaseManager getManager() {
        return manager;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public String getDB_USER() {
        return DB_USER;
    }

    public String getDB_PASS() {
        return DB_PASS;
    }
}
