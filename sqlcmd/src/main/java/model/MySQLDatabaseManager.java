package model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;
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
        final String INFO = "all parameters mandatory";

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
        List result = new ArrayList();
        if (_isConnected()) {
            try {
                connection.close();
                DBNAME = "";
                DBUSER = "";
                result.add("connection closed");
                return result;
            } catch (SQLException e) {
                result.add(e.getStackTrace());
            }
        }
        return result;
    }

    @Override
    public List listTables(String params) {
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
    public List showRecords(String table) {
        final String DEFAULT_PARAM = "tableName|limit|offset";
        final String INFO = "limit and offset are not mandatory, safely to skip both or any one";
        List result = new ArrayList();
        if (_isConnected()) {
            if (!table.isEmpty()) {
                String limit = "100";
                String offset = "0";

                String[] parameters = table.split("\\|");
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
                    result.add("Show records for table '" + table + "' failed!");
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
        disconnect("");
        System.exit(0);
        return new ArrayList(0);
    }

    @Override
    public List _usage(String methodName, String parameter, String info) {
        List result = new ArrayList();
        result.add("usage: " + methodName + " " + parameter);
        result.add(info);
        return result;
    }

    @Override
    public List _connectionInfo(String string) {
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
}
