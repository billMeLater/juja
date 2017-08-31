package ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManagerTest;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.Connect;
import ua.com.juja.vadim.sqlcmd.controller.command.Disconnect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DisconnectTest {

    private Info connectionData;
    private Command connect;
    private Command disconnect;
    private List param;
    private List result;

    @Before
    public void initialize() {
        connectionData = new Info();
        connect = new Connect();
        disconnect = new Disconnect();
        param = new ArrayList();
        result = new ArrayList();
    }

    @Test
    public void disconnectNoConnection() {
        // not connected
        result.add("Connection does not exist. Connect to DB first.");
        assertEquals(result, disconnect.execute(connectionData.getManager(), param));
    }

    @Test
    public void disconnect() {
        // connected
        param.add(connectionData.getDB_NAME());
        param.add(connectionData.getDB_USER());
        param.add(connectionData.getDB_PASS());
        connect.execute(connectionData.getManager(), param);
        result.add("Connection closed");
        assertEquals(result, disconnect.execute(connectionData.getManager(), param));
    }
}
