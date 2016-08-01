package ua.com.juja.vadim.sqlcmd.model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MySQLDatabaseManager implements DatabaseManager {
    private Connection connection;
    private String DBNAME;
    private String DBUSER;

    public MySQLDatabaseManager() {
        DBNAME = "";
        DBUSER = "";
    }

    @Override
    public List connect(String params) {
        final String DEFAULT_PARAM = "dbName|dbUser|password";
        final String INFO = "\tconnect to DB. All parameters mandatory";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }

        String[] dbParam = params.split("\\|");
        if (dbParam.length == DEFAULT_PARAM.split("\\|").length) {
            String dbName = dbParam[0];
            String dbUser = dbParam[1];
            String dbPass = dbParam[2];

            final String DBURL = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false";

            List result = new ArrayList();
            try {
                Driver driver = new FabricMySQLDriver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(DBURL, dbUser, dbPass);

                if (!connection.isClosed()) {
                    DBNAME = dbName;
                    DBUSER = dbUser;
                    result.add("Connection to DB '" + dbName + "' established");
                    return result;
                }
            } catch (SQLException e) {
                result.add("Connection to DB '" + dbName + "' failed!");
                result.add(e.getMessage());
                return result;
            }
        } else {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }
        return new ArrayList(0);
    }

    @Override
    public List disconnect(String params) {
        final String DEFAULT_PARAM = "";
        final String INFO = "\tclose existing DB connection";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }

        List result = new ArrayList();
        if (_isConnected()) {
            try {
                connection.close();
                DBNAME = "";
                DBUSER = "";
                result.add("Connection closed");
                return result;
            } catch (SQLException e) {
                result.add(e.getStackTrace());
            }
        }
        result.add("Connection does not exist. Connect to DB first.");
        return result;
    }

    @Override
    public List createTable(String params) {
        final String DEFAULT_PARAM = "tableName|fieldName1|fieldType1|...|fieldNameN|fieldTypeN";
        final String INFO = "\t create table 'tableName' with desired fields.";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }

        List result = new ArrayList();
        if (_isConnected()) {
            if (!params.isEmpty()) {
                String[] parameters = params.split("\\|");
                if (parameters.length < 3 || Arrays.asList(parameters).contains("") || parameters.length % 2 == 0) {
                    return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
                }

                String tableName = parameters[0];
                String fields = "";
                for (int i = 1; i < parameters.length; i = i + 2) {
                    fields += parameters[i] + " " + parameters[i + 1];
                    if (i + 2 < parameters.length) {
                        fields += ", ";
                    }
                }
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("create table " + tableName + "(" + fields + ")");
                    result.add("table '" + tableName + "' with fields (" + fields + ") created");
                } catch (SQLException e) {
                    result.add("create table '" + tableName + "' with fields (" + fields + ") - FAILED!");
                    result.add(e.getMessage());
                }
            } else {
                return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public List dropTable(String params) {
        final String DEFAULT_PARAM = "tableName";
        final String INFO = "\t delete table 'tableName'";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }

        List result = new ArrayList();
        if (_isConnected()) {
            if (!params.isEmpty()) {
                String[] parameters = params.split("\\|");
                if (parameters.length < 1 || Arrays.asList(parameters).contains("")) {
                    return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
                }

                String tableName = parameters[0];

                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("drop table if exists " + tableName);
                    result.add("table '" + tableName + "' deleted");
                } catch (SQLException e) {
                    result.add("delete table '" + tableName + "' - FAILED!");
                    result.add(e.getMessage());
                }
            } else {
                return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public List showTables(String params) {
        final String DEFAULT_PARAM = "";
        final String INFO = "\tshow list of existing tables";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }
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

    @Override
    public List addRecord(String params) {
        final String DEFAULT_PARAM = "tableName|fieldName1|value1|...|fieldNameN|valueN";
        final String INFO = "\t add record into table 'tableName' with 'fieldName'='value' pairs. "
                + "'tableName' or 'fieldName' can't be empty.";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }

        List result = new ArrayList();
        if (_isConnected()) {
            if (!params.isEmpty()) {
                String[] parameters = params.split("\\|");
                if (parameters.length < 3 || parameters[0].isEmpty() || parameters.length % 2 == 0) {
                    return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
                }

                String tableName = parameters[0];
                String fields = "";
                String values = "";
                for (int i = 1; i < parameters.length; i = i + 2) {
                    if (parameters[i].isEmpty()) {
                        return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
                    }
                    fields += parameters[i];
                    values += "'" + parameters[i + 1] + "'";
                    if (i + 2 < parameters.length) {
                        fields += ",";
                        values += ",";
                    }
                }

                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("insert into " + tableName + " (" + fields + ") values(" + values + ")");
                    result.add("record was added.");
                } catch (SQLException e) {
                    result.add("addRecord - FAILED!");
                    result.add(e.getMessage());
                }
            } else {
                return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public List removeRecord(String params) {
        final String DEFAULT_PARAM = "tableName|fieldName1|value1|...|fieldNameN|valueN";
        final String INFO = "\t remove records from table 'tableName' with clause 'fieldName'='value'. "
                + "Use 'removeRecord tableName|*|*' to remove all records from table 'tableName'.";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }

        List result = new ArrayList();
        if (_isConnected()) {
            if (!params.isEmpty()) {
                String[] parameters = params.split("\\|");
                if (parameters.length < 3 || Arrays.asList(parameters).contains("") || parameters.length % 2 == 0) {
                    return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
                }

                String tableName = parameters[0];
                String where = "";
                if (!(parameters[1].equals("*") && parameters[2].equals("*"))) {
                    where += " where ";
                    for (int i = 1; i < parameters.length; i = i + 2) {
                        where += parameters[i] + "='" + parameters[i + 1] + "'";
                        if (i + 2 < parameters.length) {
                            where += " and ";
                        }
                    }
                }

                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("delete from " + tableName + where);
                    result.add("record(s) removed.");
                } catch (SQLException e) {
                    result.add("removeRecord - FAILED!");
                    result.add(e.getMessage());
                }
            } else {
                return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public List showRecords(String params) {
        final String DEFAULT_PARAM = "tableName|limit|offset";
        final String INFO = "\t show records from table 'tableName'. "
                + "Limit and offset are natural numbers and not mandatory, safely to skip both or any one";

        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }

        List result = new ArrayList();
        if (_isConnected()) {
            if (!params.isEmpty()) {
                String limit = "100";
                String offset = "0";

                String[] parameters = params.split("\\|");
                String tableName = parameters[0];

                if (parameters.length == 3) {
                    if (!parameters[1].isEmpty()) {
                        limit = parameters[1];
                    }
                    offset = parameters[2];
                } else if (parameters.length == 2) {
                    limit = parameters[1];
                }

                try (Statement stmt = connection.createStatement()) {
                    ResultSet rs = stmt.executeQuery("select * from " + tableName
                            + " order by 1 limit " + limit + " offset " + offset);
                    ResultSetMetaData metaData = rs.getMetaData();

                    result.add("_drawTable_");
                    result.add(true);
                    result.add(true);

                    int columnCount = metaData.getColumnCount();
                    String[] header = new String[columnCount];
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        header[columnIndex - 1] = metaData.getColumnName(columnIndex);
                    }
                    result.add(header);

                    while (rs.next()) {
                        String[] body = new String[columnCount];
                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                            body[columnIndex - 1] = rs.getString(columnIndex);
                        }
                        result.add(body);
                    }
                    return result;
                } catch (SQLException e) {
                    result.add("Show records for table '" + params + "' failed!");
                    result.add(e.getMessage());
                    return result;
                }
            } else {
                return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

    @Override
    public List exit(String params) {
        final String DEFAULT_PARAM = "";
        final String INFO = "\texit from SQLCmd";
        if (params.equals("_usage")) {
            return _usage(Thread.currentThread().getStackTrace()[1].getMethodName(), DEFAULT_PARAM, INFO);
        }
        disconnect("");
        System.exit(0);
        return new ArrayList(0);
    }

    @Override
    public List _usage(String methodName, String parameter, String info) {
        List result = new ArrayList();
        result.add("\n" + methodName + " " + parameter);
        result.add(info);
        return result;
    }

    @Override
    public List _connectionInfo(String string) {
        List result = new ArrayList(1);
        result.add(DBUSER + "@" + DBNAME + " > ");
        return result;
    }

    @Override
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

    @Override
    public Connection _getConnection() {
        return connection;
    }
}