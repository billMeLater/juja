package ua.com.juja.vadim.sqlcmd.model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.*;

import java.sql.*;
import java.util.*;


public class MySQLDatabaseManager implements DatabaseManager {
    private Connection connection;
    private String dbName;
    private String dbUser;
    private SortedMap<String, Command> commands;

    public MySQLDatabaseManager() {
        dbName = "";
        dbUser = "";
        commands = new TreeMap<>();
        commands.put("connect", new Connect());
        commands.put("disconnect", new Disconnect());
        commands.put("tables", new ShowTables());
        commands.put("create", new CreateTable());
        commands.put("drop", new DropTable());
        commands.put("clear", new ClearTable());
        commands.put("find", new ShowRecords());
        commands.put("insert", new AddRecord());
        commands.put("delete", new RemoveRecords());
        commands.put("update", new UpdateRecords());
        commands.put("help", new Help());
        commands.put("exit", new Exit());
    }

    @Override
    public CommandOutput connect(DatabaseManager databaseManager, List<String> params) {
        String dbName = params.get(0);
        String dbUser = params.get(1);
        String dbPass = params.get(2);

        final String DBURL = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false";

        CommandOutput result = new CommandOutput();
        if (databaseManager._isConnected() && this.dbName.equals(dbName) && this.dbUser.equals(dbUser)) {
            result.add("Already connected to DB '" + dbName + "' as '" + dbUser + "'");
        } else {
            try {
                Driver driver = new FabricMySQLDriver();
                DriverManager.registerDriver(driver);
                ((MySQLDatabaseManager) databaseManager).setConnection(DriverManager.getConnection(DBURL, dbUser, dbPass));

                if (!databaseManager._getConnection().isClosed()) {
                    this.dbName = dbName;
                    this.dbUser = dbUser;
                    result.add("Connection to DB '" + dbName + "' established");
                } else {
                    result.add("Connection to DB '" + dbName + "' failed!");
                }
            } catch (SQLException e) {
                result.add(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public CommandOutput disconnect(DatabaseManager databaseManager) {
        CommandOutput result = new CommandOutput();
        if (databaseManager._isConnected()) {
            try {
                databaseManager._getConnection().close();
                result.add("Connection closed");
            } catch (SQLException e) {
                result.add(Arrays.toString(e.getStackTrace()));
            }
        } else {
            result.add("Connection does not exist. Connect to DB first.");
        }
        this.dbName = "";
        this.dbUser = "";
        return result;
    }

    @Override
    public void exit(DatabaseManager databaseManager) {
        this.disconnect(databaseManager);
        System.exit(0);
    }

    @Override
    public CommandOutput help(DatabaseManager databaseManager) {
        CommandOutput result = new CommandOutput();
        for (Command command : commands.values()) {
            result.add(command._info());
        }
        return result;
    }

    @Override
    public CommandOutput createTable(DatabaseManager databaseManager, List<String> params) {
        CommandOutput result = new CommandOutput();
        String tableName = params.get(0);
        StringBuilder fields = new StringBuilder();
        StringBuilder fieldTypes = new StringBuilder();

        if (databaseManager._isConnected()) {
            for (int i = 1; i < params.size(); i++) {
                fields.append(params.get(i));
                fieldTypes.append(params.get(i)).append(" VARCHAR(50)");
                if (i + 1 < params.size()) {
                    fieldTypes.append(", ");
                    fields.append(", ");
                }
            }
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("create table " + tableName + "(" + fieldTypes + ")");
                result.add("table '" + tableName + "' with fields (" + fields + ") created");
            } catch (SQLException e) {
                result.add("create table '" + tableName + "' with fields (" + fields + ") - FAILED!");
                result.add(e.getMessage());
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public CommandOutput dropTable(DatabaseManager databaseManager, List<String> params) {
        CommandOutput result = new CommandOutput();
        if (databaseManager._isConnected()) {
            for (Object tableName : params) {
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("drop table if exists " + tableName);
                    result.add("table '" + tableName + "' deleted");
                } catch (SQLException e) {
                    result.add("delete table '" + tableName + "' - FAILED!");
                    result.add(e.getMessage());
                }
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public CommandOutput clearTable(DatabaseManager databaseManager, List<String> params) {
        CommandOutput result = new CommandOutput();
        if (databaseManager._isConnected()) {
            for (Object tableName : params) {
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("delete from " + tableName);
                    result.add("table '" + tableName + "' cleared");
                } catch (SQLException e) {
                    result.add("clear table '" + tableName + "' - FAILED!");
                    result.add(e.getMessage());
                }
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public CommandOutput showTables(DatabaseManager databaseManager) {
        CommandOutput result;
        if (_isConnected()) {
            result = new CommandOutput(true, new String[]{"Existing tables for DB '" + dbName + "'"}, true);
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("show tables");
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
            } catch (SQLException e) {
                result.add(e.getMessage());
            }
        } else {
            result = new CommandOutput();
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public CommandOutput addRecord(DatabaseManager databaseManager, List<String> params) {
        String tableName = params.get(0);
        StringBuilder fields = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();

        CommandOutput result = new CommandOutput();

        if (databaseManager._isConnected()) {
            for (int i = 1; i < params.size() - 1; i = i + 2) {
                fields.append(params.get(i));
                fieldValues.append("\"" + params.get(i + 1) + "\"");
                if (i + 2 < params.size()) {
                    fields.append(", ");
                    fieldValues.append(", ");
                }
            }
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("insert into " + tableName + "(" + fields + ") values(" + fieldValues + ")");
                result.add("data added into table '" + tableName + "'");
            } catch (SQLException e) {
                result.add("data insert into table '" + tableName + "' - FAILED!");
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public CommandOutput removeRecords(DatabaseManager databaseManager, List<String> params) {
        String tableName = params.get(0);
        StringBuilder where = new StringBuilder(" where ");

        CommandOutput result = new CommandOutput();

        if (databaseManager._isConnected()) {

            for (int i = 1; i < params.size() - 1; i = i + 2) {
                where.append(params.get(i)).append("='").append(params.get(i + 1)).append("'");
                if (i + 2 < params.size()) {
                    where.append(" and ");
                }
            }
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("select * from " + tableName + where + " order by 1");
                ResultSetMetaData metaData = rs.getMetaData();

                int columnCount = metaData.getColumnCount();
                String[] header = new String[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    header[columnIndex - 1] = metaData.getColumnName(columnIndex);
                }
                result = new CommandOutput(true, header, true);
                while (rs.next()) {
                    String[] body = new String[columnCount];
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        body[columnIndex - 1] = rs.getString(columnIndex);
                    }
                    result.add(body);
                }
                stmt.executeUpdate("delete from " + tableName + where);
                result.add("above record(s) removed from table '" + tableName + "'");
            } catch (SQLException e) {
                result.add("remove record(s) from table '" + tableName + "' - FAILED!");
            }
        } else {
            result = new CommandOutput();
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public CommandOutput updateRecords(DatabaseManager databaseManager, List<String> params) {
        String tableName = params.get(0);
        StringBuilder where = new StringBuilder(" where ").append(params.get(1)).append("='").append(params.get(2)).append("' ");
        StringBuilder set = new StringBuilder(" set ").append(params.get(3)).append("='").append(params.get(4)).append("' ");

        CommandOutput result = new CommandOutput();

        if (databaseManager._isConnected()) {
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("select * from " + tableName + where + " order by 1");
                ResultSetMetaData metaData = rs.getMetaData();

                int columnCount = metaData.getColumnCount();
                String[] header = new String[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    header[columnIndex - 1] = metaData.getColumnName(columnIndex);
                }
                result = new CommandOutput(true, header, true);
                while (rs.next()) {
                    String[] body = new String[columnCount];
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        body[columnIndex - 1] = rs.getString(columnIndex);
                    }
                    result.add(body);
                }
                stmt.executeUpdate("update " + tableName + set + where);
                result.add("above record(s) where updated for table '" + tableName + "'");
            } catch (SQLException e) {
                System.out.println("update " + tableName + set + where);
                result.add("update record(s) for table '" + tableName + "' - FAILED!");
            }
        } else {
            result = new CommandOutput();
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public CommandOutput showRecords(DatabaseManager databaseManager, List<String> params) {
        String tableName = params.get(0);
        String limit = params.get(1);
        String offset = params.get(2);

        CommandOutput result;
        if (databaseManager._isConnected()) {
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("select * from " + tableName
                        + " order by 1 limit " + limit + " offset " + offset);
                ResultSetMetaData metaData = rs.getMetaData();

                int columnCount = metaData.getColumnCount();
                String[] header = new String[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    header[columnIndex - 1] = metaData.getColumnName(columnIndex);
                }
                result = new CommandOutput(true, header, true);
                while (rs.next()) {
                    String[] body = new String[columnCount];
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        body[columnIndex - 1] = rs.getString(columnIndex);
                    }
                    result.add(body);
                }
            } catch (SQLException e) {
                result = new CommandOutput();
                result.add("Show records for table '" + tableName + "' failed!");
                result.add(e.getMessage());
            }
        } else {
            result = new CommandOutput();
            result.add("Connect to DB first");
        }
        return result;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection _getConnection() {
        return connection;
    }

    @Override
    public CommandOutput _connectionInfo() {
        CommandOutput result = new CommandOutput();
        result.add(dbUser + "@" + dbName + " > ");
        return result;
    }

    @Override
    public boolean _isConnected() {
        if (!dbName.isEmpty()) {
            try {
                if (!connection.isClosed()) {
                    return true;
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    @Override
    public Map<String, Command> _getCommands() {
        return this.commands;
    }
}