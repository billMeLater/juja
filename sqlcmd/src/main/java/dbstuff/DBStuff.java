package dbstuff;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public void disconnect(String string) {
        try {
            connection.close();
            DBNAME = "";
            DBUSER = "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exit(String string) {
        System.exit(0);
    }

    public String connectionInfo(String string) {
        return DBUSER + "@" + DBNAME + " > ";
    }

}
