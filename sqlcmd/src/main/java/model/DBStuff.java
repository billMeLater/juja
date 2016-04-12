package model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

/**
 * Created by vadim on 4/5/16.
 */
public class DBStuff {

    private Connection connection;
    private String DBNAME;
    private String DBUSER;

    public DBStuff(String DBNAME, String DBUSER) {
        this.DBNAME = DBNAME;
        this.DBUSER = DBUSER;
    }

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
                    System.out.println("Connection to DB '" + dbName + "' established");
                    DBNAME = dbName;
                    DBUSER = dbUser;
                }
            } catch (SQLException e) {
                System.out.println("Connection to DB '" + dbName + "' failed!\n" + e.getMessage());
            }
        } else {
            System.out.println("usage: connect dbName|dbUser|password");
        }
    }

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
            System.out.println("Connect to DB first");
        }
    }

    public void exit(String params) {
        System.exit(0);
    }

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

    public void showRecords(String table) {
        if (!DBNAME.isEmpty()) {
            if (!table.isEmpty()) {
                try (Statement stmt = connection.createStatement()) {

                    ResultSet rs = stmt.executeQuery("select * from " + table + " order by 1");
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    String delimiter = ",";
                    System.out.println("table = [" + table + "]");

                    while (rs.next()) {
                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                            if (columnIndex == columnCount) {
                                delimiter = ";\n";
                            }
                            System.out.printf("\t%s: ", metaData.getColumnName(columnIndex));
                            Object object = rs.getObject(columnIndex);
                            System.out.printf("%s" + delimiter + " ", object == null ? "NULL" : object.toString());
                        }
                        delimiter = ",";
                    }
                    System.out.println("--------------");

                } catch (SQLException e) {
                    System.out.println("Show records for table '" + table + "' failed!\n" + e.getMessage());
                    this.listTables("");
                }
            } else {
                System.out.println("usage:  showRecords tableName");
            }
        } else {
            System.out.println("Connect to DB first");
        }
    }

    public String _connectionInfo(String string) {
        return DBUSER + "@" + DBNAME + " > ";
    }

}
