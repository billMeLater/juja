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
    public List _connectionInfo(String string) {
        List result = new ArrayList(1);
        result.add(DBUSER + "@" + DBNAME + " > ");
        return result;
    }

    @Override
    public List listTables(String params) {
        List result = new ArrayList();
        if (_isConnected()) {
            result.add("Existing tables for DB '" + DBNAME + "'");
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("show tables");
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
            } catch (SQLException e) {
                result.add(e.getMessage());
            }
        } else {
            result.add("Connect to DB first");
        }
        return result;
    }

//    @Override
//    public void showRecords(String table) {
//        if (!DBNAME.isEmpty()) {
//            if (!table.isEmpty()) {
//                String limit = "100";
//                String offset = "0";
//
//                String[] parameters = table.split("\\|");
//                String tableName = parameters[0];
//                ;
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
//                    ResultSet rowsCount = stmt.executeQuery("select count(*) from "
//                            + "(select 1 from " + tableName + " limit " + limit + " offset " + offset + ") as count");
//                    rowsCount.next();
//                    int tableSize = rowsCount.getInt(1);
//
//                    ResultSet rs = stmt.executeQuery("select * from " + tableName
//                            + " order by 1 limit " + limit + " offset " + offset);
//                    ResultSetMetaData metaData = rs.getMetaData();
//
//                    int columnCount = metaData.getColumnCount();
//                    String[][] tableBody = new String[tableSize + 1][columnCount];
//                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//                        tableBody[0][columnIndex - 1] = metaData.getColumnName(columnIndex);
//                    }
//
//                    int j = 1;
//                    while (rs.next()) {
//                        tableBody[j] = new String[columnCount];
//
//                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//                            tableBody[j][columnIndex - 1] = rs.getString(columnIndex);
//                        }
//                        j++;
//                    }
//                    view.drawTable(tableBody);
//
//                } catch (SQLException e) {
//                    view.write("Show records for table '" + table + "' failed!\n" + e.getMessage());
//                    this.listTables("");
//                }
//            } else {
//                view.write("usage: showRecords tableName|limit|offset\n\tlimit and offset are not mandatory,"
//                        + " safely to skip both or any one\n");
//            }
//        } else {
//            view.write("Connect to DB first\n");
//        }
//    }



}
