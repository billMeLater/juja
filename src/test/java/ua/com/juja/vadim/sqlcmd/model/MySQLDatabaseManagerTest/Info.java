package ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManagerTest;

import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;
import ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManager;

public class Info {
    private DatabaseManager manager;
    private String dbName;
    private String dbUser;
    private String dbPass;

    public Info() {
        manager = new MySQLDatabaseManager();
        dbName = "juja";
        dbUser = "jujauser";
        dbPass = "password";
    }

    public DatabaseManager getManager() {
        return manager;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }
}
