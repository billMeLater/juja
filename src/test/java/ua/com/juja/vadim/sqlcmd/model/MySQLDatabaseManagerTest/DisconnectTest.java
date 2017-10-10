package ua.com.juja.vadim.sqlcmd.model.MySQLDatabaseManagerTest;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;
import ua.com.juja.vadim.sqlcmd.controller.command.Connect;
import ua.com.juja.vadim.sqlcmd.controller.command.Disconnect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;


public class DisconnectTest {

    private Info connectionData;
    private Command connect;
    private Command disconnect;
    private List param;

    @Before
    public void initialize() {
        connectionData = new Info();
        connect = new Connect();
        disconnect = new Disconnect();
        param = new ArrayList<String>();
    }

    @Test
    public void disconnectNoConnection() {
        // not connected
        CommandOutput result = new CommandOutput();
        result.add("Connection does not exist. Connect to DB first.");
        assertArrayEquals(result.getBody(), disconnect.execute(connectionData.getManager(), param).getBody());
    }

    @Test
    public void disconnect() {
        // connected
        CommandOutput result = new CommandOutput();
        param.add(connectionData.getDbName());
        param.add(connectionData.getDbUser());
        param.add(connectionData.getDbPass());
        connect.execute(connectionData.getManager(), param);
        result.add("Connection closed");
        assertArrayEquals(result.getBody(), disconnect.execute(connectionData.getManager(), param).getBody());
    }
}
