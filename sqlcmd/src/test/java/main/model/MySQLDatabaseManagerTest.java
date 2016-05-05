package main.model;

import model.DatabaseManager;
import model.MySQLDatabaseManager;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MySQLDatabaseManagerTest {
    DatabaseManager manager = new MySQLDatabaseManager();

    @Test
    public void usage() {
        DatabaseManager test = Mockito.mock(manager.getClass());
        List result;
        String param = "_usage";

        result = new ArrayList(2);
        result.add("\nconnect dbName|dbUser|password");
        result.add("\tconnect to DB. All parameters mandatory");
        when(test.connect(param)).thenReturn(result);
        assertEquals(test.connect(param), manager.connect(param));

        result = new ArrayList(2);
        result.add("\ndisconnect ");
        result.add("\tclose existing DB connection");
        when(test.disconnect(param)).thenReturn(result);
        assertEquals(test.disconnect(param), manager.disconnect(param));

        result = new ArrayList(2);
        result.add("\nexit ");
        result.add("\texit from SQLCmd");
        when(test.exit(param)).thenReturn(result);
        assertEquals(test.exit(param), manager.exit(param));

        result = new ArrayList(2);
        result.add("\nlistTables ");
        result.add("\tshow list of existing tables");
        when(test.listTables(param)).thenReturn(result);
        assertEquals(test.listTables(param), manager.listTables(param));

        result = new ArrayList(2);
        result.add("\nshowRecords tableName|limit|offset");
        result.add("\t show records from table 'tableName'. Limit and offset are not mandatory, safely to skip both or any one");
        when(test.showRecords(param)).thenReturn(result);
        assertEquals(test.showRecords(param), manager.showRecords(param));
    }

    @Test
    public void connectionInfo() {

    }

}
