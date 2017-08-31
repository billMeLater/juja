package ua.com.juja.vadim.sqlcmd.model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ua.com.juja.vadim.sqlcmd.controller.Command;
import ua.com.juja.vadim.sqlcmd.controller.command.*;

import java.sql.*;
import java.util.*;


public class MySQLDatabaseManager implements DatabaseManager {
    private Connection connection;
    private String DBNAME;
    private String DBUSER;
    private Map<String, Command> commands;

    public MySQLDatabaseManager() {
        DBNAME = "";
        DBUSER = "";
        this.commands = new HashMap<>();
        this.commands.put("connect", new Connect());
        this.commands.put("disconnect", new Disconnect());
        this.commands.put("tables", new Tables());
        this.commands.put("create", new CreateTable());
        this.commands.put("drop", new Drop());
        this.commands.put("clear", new Clear());
        this.commands.put("help", new Help());
        this.commands.put("exit", new Exit());
    }

    public List connect(DatabaseManager databaseManager, List params) {
        String dbName = params.get(0).toString();
        String dbUser = params.get(1).toString();
        String dbPass = params.get(2).toString();

        final String DBURL = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false";

        List<String> result = new ArrayList<>();
        if (databaseManager._isConnected() && this.DBNAME.equals(dbName) && this.DBUSER.equals(dbUser)) {
            result.add("Already connected to DB '" + dbName + "' as '" + dbUser + "'");
        } else {
            try {
                Driver driver = new FabricMySQLDriver();
                DriverManager.registerDriver(driver);
                ((MySQLDatabaseManager) databaseManager).setConnection(DriverManager.getConnection(DBURL, dbUser, dbPass));

                if (!databaseManager._getConnection().isClosed()) {
                    this.DBNAME = dbName;
                    this.DBUSER = dbUser;
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

    public List disconnect(DatabaseManager databaseManager) {
        List<String> result = new ArrayList<>();

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
        this.DBNAME = "";
        this.DBUSER = "";
        return result;
    }

    public void exit(DatabaseManager databaseManager) {
        this.disconnect(databaseManager);
        System.exit(0);
    }

    public List help(DatabaseManager databaseManager) {
        List<String> result = new ArrayList<>();
        for (Command command : this.commands.values()) {
            result.add(command._info());
        }
        return result;
    }

    public List createTable(DatabaseManager databaseManager, List params) {
        List result = new ArrayList();
        String tableName = params.get(0).toString();
        String fields = "";
        String fieldTypes = "";

        if (databaseManager._isConnected()) {
            for (int i = 1; i < params.size(); i = i + 1) {
                fields += params.get(i);
                fieldTypes += params.get(i) + " VARCHAR(10)";
                if (i + 1 < params.size()) {
                    fieldTypes += ", ";
                    fields += ", ";
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

    public List dropTable(DatabaseManager databaseManager, List params) {
        List<String> result = new ArrayList<>();
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

    public List clearTable(DatabaseManager databaseManager, List params) {
        List<String> result = new ArrayList<>();
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

    public List showTables(DatabaseManager databaseManager) {
        List result = new ArrayList();
        if (_isConnected()) {
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("show tables");
                result.add("_drawTable_");
                result.add(true);
                result.add(true);
                result.add(new String[]{"Existing tables for DB '" + DBNAME + "'"});
                while (rs.next()) {
                    result.add(new String[]{rs.getString(1)});
                }
            } catch (SQLException e) {
                result.add(e.getMessage());
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

//    public List addRecord(DatabaseManager databaseManager, List params) {
//        throw new NotImplementedException();
//        List result = new ArrayList();
//        String tableName = params.get(0).toString();
//        String fields = "";
//        String fieldTypes = "";

//        if (_isConnected()) {
//            if (!params.isEmpty()) {
//                String[] parameters = params.split("\\|");
//                if (parameters.length < 3 || parameters[0].isEmpty() || parameters.length % 2 == 0) {
//                    return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
//                }

//        if (databaseManager._isConnected()) {
//            for (int i = 1; i < params.size(); i = i + 1) {
//                fields += params.get(i);
//                fieldTypes += params.get(i) + " VARCHAR(10)";
//                if (i + 1 < params.size()) {
//                    fieldTypes += ", ";
//                    fields += ", ";
//                }
//            }
//            try (Statement stmt = connection.createStatement()) {
//                stmt.executeUpdate("create table " + tableName + "(" + fieldTypes + ")");
//                result.add("table '" + tableName + "' with fields (" + fields + ") created");
//            } catch (SQLException e) {
//                result.add("create table '" + tableName + "' with fields (" + fields + ") - FAILED!");
//                result.add(e.getMessage());
//            }
//        } else {
//            result.add("Connect to DB first");
//        }
//        return result;
//    }

//    @Override
//    public List removeRecord(String params) {
//        final String DEFAULT_PARAM = "tableName|fieldName1|value1|...|fieldNameN|valueN";
//        final String INFO = "\t remove records from table 'tableName' with clause 'fieldName'='value'. "
//                + "Use 'removeRecord tableName|*|*' to remove all records from table 'tableName'.";
//
//        if (params.equals("_usage")) {
//            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
//        }
//
//        List result = new ArrayList();
//        if (_isConnected()) {
//            if (!params.isEmpty()) {
//                String[] parameters = params.split("\\|");
//                if (parameters.length < 3 || Arrays.asList(parameters).contains("") || parameters.length % 2 == 0) {
//                    return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
//                }
//
//                String tableName = parameters[0];
//                String where = "";
//                if (!(parameters[1].equals("*") && parameters[2].equals("*"))) {
//                    where += " where ";
//                    for (int i = 1; i < parameters.length; i = i + 2) {
//                        where += parameters[i] + "='" + parameters[i + 1] + "'";
//                        if (i + 2 < parameters.length) {
//                            where += " and ";
//                        }
//                    }
//                }
//
//                try (Statement stmt = connection.createStatement()) {
//                    stmt.executeUpdate("delete from " + tableName + where);
//                    result.add("record(s) removed.");
//                } catch (SQLException e) {
//                    result.add("removeRecord - FAILED!");
//                    result.add(e.getMessage());
//                }
//            } else {
//                return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
//            }
//        } else {
//            result.add("Connect to DB first");
//        }
//        return result;
//    }
//
//    @Override
//    public List showRecords(String params) {
//        final String DEFAULT_PARAM = "tableName|limit|offset";
//        final String INFO = "\t show records from table 'tableName'. "
//                + "Limit and offset are natural numbers and not mandatory, safely to skip both or any one";
//
//        if (params.equals("_usage")) {
//            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
//        }
//
//        List result = new ArrayList();
//        if (_isConnected()) {
//            if (!params.isEmpty()) {
//                String limit = "100";
//                String offset = "0";
//
//                String[] parameters = params.split("\\|");
//                String tableName = parameters[0];
//
//                if (parameters.length == 3) {
//                    if (!parameters[1].isEmpty()) {
//                        limit = parameters[1];
//                    }
//                    offset = parameters[2];
//                } else if (parameters.length == 2) {
//                    limit = parameters[1];
//                }
//
//                try (Statement stmt = connection.createStatement()) {
//                    ResultSet rs = stmt.executeQuery("select * from " + tableName
//                            + " order by 1 limit " + limit + " offset " + offset);
//                    ResultSetMetaData metaData = rs.getMetaData();
//
//                    result.add("_drawTable_");
//                    result.add(true);
//                    result.add(true);
//
//                    int columnCount = metaData.getColumnCount();
//                    String[] header = new String[columnCount];
//                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//                        header[columnIndex - 1] = metaData.getColumnName(columnIndex);
//                    }
//                    result.add(header);
//
//                    while (rs.next()) {
//                        String[] body = new String[columnCount];
//                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//                            body[columnIndex - 1] = rs.getString(columnIndex);
//                        }
//                        result.add(body);
//                    }
//                    return result;
//                } catch (SQLException e) {
//                    result.add("Show records for table '" + params + "' failed!");
//                    result.add(e.getMessage());
//                    return result;
//                }
//            } else {
//                return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
//            }
//        } else {
//            result.add("Connect to DB first");
//        }
//        return result;
//    }


    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection _getConnection() {
        return connection;
    }

    public List _connectionInfo() {
        List result = new ArrayList(1);
        result.add(DBUSER + "@" + DBNAME + " > ");
        return result;
    }

    public boolean _isConnected() {
        if (!DBNAME.isEmpty()) {
            try {
                if (!connection.isClosed()) {
                    return true;
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public Map<String, Command> _getCommands() {
        return this.commands;
    }
}