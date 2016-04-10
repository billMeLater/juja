package dbstuff;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

/**
 * Created by vadim on 4/5/16.
 */
public class DBStuff {

    public Connection connection;
    private String DBNAME;
    private String DBUSER;

    public DBStuff(String DBNAME, String DBUSER) {
        this.DBNAME = DBNAME;
        this.DBUSER = DBUSER;
    }

    public void connect(String params) {
        String[] dbParam = params.split("\\|");
        final String DBURL = "jdbc:mysql://localhost:3306/" + dbParam[0] + "?useSSL=false";

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DBURL, dbParam[1], dbParam[2]);

            if (!connection.isClosed()) {
                System.out.println("connection to DB is open");
                DBNAME = dbParam[0];
                DBUSER = dbParam[1];
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void disconnect(String params) {
        try {
            connection.close();
            DBNAME = "";
            DBUSER = "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exit(String params) {
        System.exit(0);
    }

    public void listTables(String params) {
        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery("show tables");

            while (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showRecords(String table) {
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
            e.printStackTrace();
        }
    }

    public String connectionInfo(String string) {
        return DBUSER + "@" + DBNAME + " > ";
    }

}
