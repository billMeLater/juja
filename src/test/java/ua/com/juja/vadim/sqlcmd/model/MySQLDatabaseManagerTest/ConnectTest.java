package ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManagerTest;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.Connect;
import ua.com.juja.vadim.sqlcmd.controller.command.Disconnect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ConnectTest {
    private Info connectionData;
    private Command connect;
    private Command disconnect;
    private List param;
    private List result;

    @Before
    public void initialize() {
        connectionData = new Info();
        connect = new Connect();
        param = new ArrayList();
        result = new ArrayList();
    }

    @Test
    public void connectNotAllParams() {
        // not all parameters specified
        result.add("USAGE:");
        result.add("\tconnect|dbName|dbUser|password");
        assertEquals(result, connect.execute(connectionData.getManager(), param));
    }

    @Test
    public void connectBadData() {
        // bad data
        param.add(connectionData.getDB_NAME());
        param.add(connectionData.getDB_USER());
        param.add("d u m m y");
        result.add("Access denied for user '" + connectionData.getDB_USER() + "'@'localhost' (using password: YES)");
        assertEquals(result, connect.execute(connectionData.getManager(), param));
    }

    @Test
    public void connect() {
        // good data
        param.add(connectionData.getDB_NAME());
        param.add(connectionData.getDB_USER());
        param.add(connectionData.getDB_PASS());
        result.add("Connection to DB '" + connectionData.getDB_NAME() + "' established");
        assertEquals(result, connect.execute(connectionData.getManager(), param));
    }
}
