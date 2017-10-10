package ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManagerTest;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;
import ua.com.juja.vadim.sqlcmd.controller.command.Connect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;


public class ConnectTest {
    private Info connectionData;
    private Command connect;
    private List param;

    @Before
    public void initialize() {
        connectionData = new Info();
        connect = new Connect();
        param = new ArrayList<String>();
    }

    @Test
    public void connectNotAllParams() {
        // not all parameters specified
        CommandOutput result = new CommandOutput();
        result.add("USAGE:");
        result.add("\tconnect|dbName|dbUser|password");
        assertArrayEquals(result.getBody(), connect.execute(connectionData.getManager(), param).getBody());
    }

    @Test
    public void connectBadData() {
        // bad data
        CommandOutput result = new CommandOutput();
        param.add(connectionData.getDbName());
        param.add(connectionData.getDbUser());
        param.add("d u m m y");
        result.add("Access denied for user '" + connectionData.getDbUser() + "'@'localhost' (using password: YES)");
        assertArrayEquals(result.getBody(), connect.execute(connectionData.getManager(), param).getBody());
    }

    @Test
    public void connect() {
        // good data
        CommandOutput result = new CommandOutput();
        param.add(connectionData.getDbName());
        param.add(connectionData.getDbUser());
        param.add(connectionData.getDbPass());
        result.add("Connection to DB '" + connectionData.getDbName() + "' established");
        assertArrayEquals(result.getBody(), connect.execute(connectionData.getManager(), param).getBody());
    }
}
