package model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import view.View;

import java.sql.*;


public class MySQLDatabaseManager implements DatabaseManager {
    private Connection connection;
    private View view;
    private String DBNAME;
    private String DBUSER;

    public MySQLDatabaseManager(View view) {
        DBNAME = "";
        DBUSER = "";
        this.view = view;
    }

    @Override
    public void connect(String params) {
        String[] dbParam = params.split("\\|");
        if (dbParam.length == 3) {
            String dbName = dbParam[0];
            String dbUser = dbParam[1];
            String dbPass = dbParam[2];

            final String DBURL = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false";

            try {
                Driver driver = new FabricMySQLDriver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(DBURL, dbUser, dbPass);

                if (!connection.isClosed()) {
                    view.write("Connection to DB '" + dbName + "' established\n");
                    DBNAME = dbName;
                    DBUSER = dbUser;
                }
            } catch (SQLException e) {
                view.write("Connection to DB '" + dbName + "' failed!\n" + e.getMessage());
            }
        } else {
            view.write("usage: connect dbName|dbUser|password\n");
        }
    }

    @Override
    public void disconnect(String params) {
        if (!DBNAME.isEmpty()) {
            try {
                connection.close();
                DBNAME = "";
                DBUSER = "";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            view.write("Connect to DB first\n");
        }
    }

    @Override
    public void exit(String params) {
        System.exit(0);
    }

    @Override
    public void listTables(String params) {
        if (!DBNAME.isEmpty()) {
            try {
                if (!connection.isClosed()) {
                    System.out.println("Existing tables for DB '" + DBNAME + "'");
                    try (Statement stmt = connection.createStatement()) {

                        ResultSet rs = stmt.executeQuery("show tables");

                        while (rs.next()) {
                            String tableName = rs.getString(1);
                            System.out.println("\t" + tableName);
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Connect to DB first");
        }
    }

    @Override
    public void showRecords(String table) {
        if (!DBNAME.isEmpty()) {
            if (!table.isEmpty()) {
                String limit = "100";
                String offset = "0";

                String[] parameters = table.split("\\|");
                String tableName = parameters[0];
                ;
                if (parameters.length == 3) {
                    if (!parameters[1].isEmpty()) {
                        limit = parameters[1];
                    }
                    offset = parameters[2];
                } else if (parameters.length == 2) {
                    limit = parameters[1];
                }

                try (Statement stmt = connection.createStatement()) {
                    ResultSet rowsCount = stmt.executeQuery("select count(*) from "
                            + "(select 1 from " + tableName + " limit " + limit + " offset " + offset + ") as count");
                    rowsCount.next();
                    int tableSize = rowsCount.getInt(1);

                    ResultSet rs = stmt.executeQuery("select * from " + tableName
                            + " order by 1 limit " + limit + " offset " + offset);
                    ResultSetMetaData metaData = rs.getMetaData();

                    int columnCount = metaData.getColumnCount();
                    String[][] tableBody = new String[tableSize + 1][columnCount];
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        tableBody[0][columnIndex - 1] = metaData.getColumnName(columnIndex);
                    }

                    int j = 1;
                    while (rs.next()) {
                        tableBody[j] = new String[columnCount];

                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                            tableBody[j][columnIndex - 1] = rs.getString(columnIndex);
                        }
                        j++;
                    }
                    view.drawTable(tableBody);

                } catch (SQLException e) {
                    view.write("Show records for table '" + table + "' failed!\n" + e.getMessage());
                    this.listTables("");
                }
            } else {
                view.write("usage: showRecords tableName|limit|offset\n\tlimit and offset are not mandatory,"
                        + " safely to skip both or any one\n");
            }
        } else {
            view.write("Connect to DB first\n");
        }
    }

    @Override
    public String _connectionInfo(String string) {
        return DBUSER + "@" + DBNAME + " > ";
    }

}
