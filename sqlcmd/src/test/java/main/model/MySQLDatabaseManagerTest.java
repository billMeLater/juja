package main.model;

import model.DatabaseManager;
import model.MySQLDatabaseManager;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MySQLDatabaseManagerTest {
    private static final String DB_NAME = "juja";
    private static final String DB_USER = "jujauser";
    private static final String DB_PASS = "password";
    private static final String TEMP_TABLE = "sqlcmd_test";

    DatabaseManager manager = new MySQLDatabaseManager();
    DatabaseManager test = Mockito.mock(manager.getClass());

    @Test
    public void connection() {
        String param;
        List result;

        when(test._isConnected()).thenReturn(false);
        assertEquals(test._isConnected(), manager._isConnected());

        param = "_usage";
        result = new ArrayList(2);
        result.add("\nconnect dbName|dbUser|password");
        result.add("\tconnect to DB. All parameters mandatory");
        when(test.connect(param)).thenReturn(result);
        assertEquals(test.connect(param), manager.connect(param));

        param = "_usage";
        result = new ArrayList(2);
        result.add("\ndisconnect ");
        result.add("\tclose existing DB connection");
        when(test.disconnect(param)).thenReturn(result);
        assertEquals(test.disconnect(param), manager.disconnect(param));

        param = "";
        result = new ArrayList(1);
        result.add("Connection does not exist. Connect to DB first.");
        when(test.disconnect(param)).thenReturn(result);
        assertEquals(test.disconnect(param), manager.disconnect(param));

        param = "";
        result = new ArrayList(2);
        result.add("\nconnect dbName|dbUser|password");
        result.add("\tconnect to DB. All parameters mandatory");
        when(test.connect(param)).thenReturn(result);
        assertEquals(test.connect(param), manager.connect(param));

        param = DB_NAME + "|" + DB_USER + "|wrong";
        result = new ArrayList(2);
        result.add("Connection to DB '" + DB_NAME + "' failed!");
        result.add("Access denied for user '" + DB_USER + "'@'localhost' (using password: YES)");
        when(test.connect(param)).thenReturn(result);
        assertEquals(test.connect(param), manager.connect(param));

        param = DB_NAME + "|" + DB_USER + "|" + DB_PASS;
        result = new ArrayList(1);
        result.add("Connection to DB '" + DB_NAME + "' established");
        when(test.connect(param)).thenReturn(result);
        assertEquals(test.connect(param), manager.connect(param));

        result = new ArrayList(1);
        result.add(DB_USER + "@" + DB_NAME + " > ");
        when(test._connectionInfo("")).thenReturn(result);
        assertEquals(test._connectionInfo(""), manager._connectionInfo(""));

        when(test._isConnected()).thenReturn(true);
        assertEquals(test._isConnected(), manager._isConnected());

        result = new ArrayList(1);
        result.add("Connection closed");
        when(test.disconnect("")).thenReturn(result);
        assertEquals(test.disconnect(""), manager.disconnect(""));

        when(test._isConnected()).thenReturn(false);
        assertEquals(test._isConnected(), manager._isConnected());
    }

    @Test
    public void records() {
        List result;
        String param;

        param = "_usage";
        result = new ArrayList(2);
        result.add("\nshowRecords tableName|limit|offset");
        result.add("\t show records from table 'tableName'."
                + " Limit and offset are not mandatory, safely to skip both or any one");
        when(test.showRecords(param)).thenReturn(result);
        assertEquals(test.showRecords(param), manager.showRecords(param));

        param = "_usage";
        result = new ArrayList(2);
        result.add("\naddRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t add record into table 'tableName' with 'field'='value' pairs. 'tableName' or 'field' can't be empty.");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = "_usage";
        result = new ArrayList(2);
        result.add("\nremoveRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t remove records from table 'tableName' with clause 'field'='value'. "
                + "Use 'removeRecord tableName|*|*' to remove all records from table 'tableName'.");
        when(test.removeRecord(param)).thenReturn(result);
        assertEquals(test.removeRecord(param), manager.removeRecord(param));

        param = TEMP_TABLE + "|1|1";
        result = new ArrayList(1);
        result.add("Connect to DB first");
        when(test.showRecords(param)).thenReturn(result);
        assertEquals(test.showRecords(param), manager.showRecords(param));

        param = TEMP_TABLE + "|1|1";
        result = new ArrayList(1);
        result.add("Connect to DB first");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = TEMP_TABLE + "|1|1";
        result = new ArrayList(1);
        result.add("Connect to DB first");
        when(test.removeRecord(param)).thenReturn(result);
        assertEquals(test.removeRecord(param), manager.removeRecord(param));

        manager.connect(DB_NAME + "|" + DB_USER + "|" + DB_PASS);

        param = TEMP_TABLE;
        result = new ArrayList(1);
        result.add("Show records for table '" + TEMP_TABLE + "' failed!");
        result.add("Table '" + DB_NAME + "." + TEMP_TABLE + "' doesn't exist");
        when(test.showRecords(param)).thenReturn(result);
        assertArrayEquals(test.showRecords(param).toArray(), manager.showRecords(param).toArray());

        param = "";
        result = new ArrayList(2);
        result.add("\nshowRecords tableName|limit|offset");
        result.add("\t show records from table 'tableName'."
                + " Limit and offset are not mandatory, safely to skip both or any one");
        when(test.showRecords(param)).thenReturn(result);
        assertEquals(test.showRecords(param), manager.showRecords(param));

        param = "";
        result = new ArrayList(2);
        result.add("\naddRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t add record into table 'tableName' with 'field'='value' pairs. 'tableName' or 'field' can't be empty.");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = "";
        result = new ArrayList(2);
        result.add("\nremoveRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t remove records from table 'tableName' with clause 'field'='value'. "
                + "Use 'removeRecord tableName|*|*' to remove all records from table 'tableName'.");
        when(test.removeRecord(param)).thenReturn(result);
        assertEquals(test.removeRecord(param), manager.removeRecord(param));

        param = TEMP_TABLE + "|a|b|c";
        result = new ArrayList(2);
        result.add("\naddRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t add record into table 'tableName' with 'field'='value' pairs. 'tableName' or 'field' can't be empty.");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = TEMP_TABLE + "|a|b|c";
        result = new ArrayList(2);
        result.add("\nremoveRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t remove records from table 'tableName' with clause 'field'='value'. "
                + "Use 'removeRecord tableName|*|*' to remove all records from table 'tableName'.");
        when(test.removeRecord(param)).thenReturn(result);
        assertEquals(test.removeRecord(param), manager.removeRecord(param));

        param = TEMP_TABLE + "||b";
        result = new ArrayList(2);
        result.add("\naddRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t add record into table 'tableName' with 'field'='value' pairs. 'tableName' or 'field' can't be empty.");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = TEMP_TABLE + "||b";
        result = new ArrayList(2);
        result.add("\nremoveRecord tableName|field1|value1|...|fieldN|valueN");
        result.add("\t remove records from table 'tableName' with clause 'field'='value'. "
                + "Use 'removeRecord tableName|*|*' to remove all records from table 'tableName'.");
        when(test.removeRecord(param)).thenReturn(result);
        assertEquals(test.removeRecord(param), manager.removeRecord(param));

        Connection connection = manager._getConnection();
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TEMPORARY TABLE " + TEMP_TABLE +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(12), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        param = TEMP_TABLE;
        result = new ArrayList(4);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"id", "name"});
        when(test.showRecords(param)).thenReturn(result);
        assertArrayEquals(test.showRecords(param).toArray(), manager.showRecords(param).toArray());

        param = TEMP_TABLE + "|id|101|name|TestName101";
        result = new ArrayList(2);
        result.add("record was added.");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = TEMP_TABLE + "|id|111|name|TestName101";
        result = new ArrayList(2);
        result.add("record was added.");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = TEMP_TABLE + "|id1|1";
        result = new ArrayList(2);
        result.add("addRecord - FAILED!");
        result.add("Unknown column 'id1' in 'field list'");
        when(test.addRecord(param)).thenReturn(result);
        assertEquals(test.addRecord(param), manager.addRecord(param));

        param = TEMP_TABLE + "|id1|1";
        result = new ArrayList(2);
        result.add("removeRecord - FAILED!");
        result.add("Unknown column 'id1' in 'where clause'");
        when(test.removeRecord(param)).thenReturn(result);
        assertEquals(test.removeRecord(param), manager.removeRecord(param));

        param = TEMP_TABLE;
        result = new ArrayList(4);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"id", "name"});
        result.add(new String[]{"101", "TestName101"});
        result.add(new String[]{"111", "TestName101"});
        when(test.showRecords(param)).thenReturn(result);
        assertArrayEquals(test.showRecords(param).toArray(), manager.showRecords(param).toArray());

        param = TEMP_TABLE + "||1";
        result = new ArrayList(4);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"id", "name"});
        result.add(new String[]{"111", "TestName101"});
        when(test.showRecords(param)).thenReturn(result);
        assertArrayEquals(test.showRecords(param).toArray(), manager.showRecords(param).toArray());

        param = TEMP_TABLE + "|100|1";
        result = new ArrayList(4);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"id", "name"});
        result.add(new String[]{"111", "TestName101"});
        when(test.showRecords(param)).thenReturn(result);
        assertArrayEquals(test.showRecords(param).toArray(), manager.showRecords(param).toArray());

        param = TEMP_TABLE + "|1";
        result = new ArrayList(4);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"id", "name"});
        result.add(new String[]{"101", "TestName101"});
        when(test.showRecords(param)).thenReturn(result);
        assertArrayEquals(test.showRecords(param).toArray(), manager.showRecords(param).toArray());

        param = TEMP_TABLE + "|id|101|name|TestName101";
        result = new ArrayList(2);
        result.add("record(s) removed.");
        when(test.removeRecord(param)).thenReturn(result);
        assertEquals(test.removeRecord(param), manager.removeRecord(param));

        param = TEMP_TABLE;
        result = new ArrayList(4);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"id", "name"});
        result.add(new String[]{"111", "TestName101"});
        when(test.showRecords(param)).thenReturn(result);
        assertArrayEquals(test.showRecords(param).toArray(), manager.showRecords(param).toArray());

        manager.disconnect("");
    }

    @Test
    public void tables() {
        List result;
        String param;

        param = "_usage";
        result = new ArrayList(2);
        result.add("\nshowTables ");
        result.add("\tshow list of existing tables");
        when(test.showTables(param)).thenReturn(result);
        assertEquals(test.showTables(param), manager.showTables(param));

        param = "";
        result = new ArrayList(1);
        result.add("Connect to DB first");
        when(test.showTables(param)).thenReturn(result);
        assertEquals(test.showTables(param), manager.showTables(param));

        manager.connect(DB_NAME + "|" + DB_USER + "|" + DB_PASS);

        param = "";
        result = new ArrayList(1);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"Existing tables for DB '" + DB_NAME + "'"});
        result.add(new String[]{"address"});
        result.add(new String[]{"user"});
        when(test.showTables(param)).thenReturn(result);
        assertArrayEquals(test.showTables(param).toArray(), manager.showTables(param).toArray());

        manager.disconnect("");
    }

    @Test
    public void exit() {
        List result;
        String param = "_usage";

        result = new ArrayList(2);
        result.add("\nexit ");
        result.add("\texit from SQLCmd");
        when(test.exit(param)).thenReturn(result);
        assertEquals(test.exit(param), manager.exit(param));
    }
}
