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
    private String DBPASS;

    public DBStuff(String DBNAME, String DBUSER, String DBPASS) {
        this.DBNAME = DBNAME;
        this.DBUSER = DBUSER;
        this.DBPASS = DBPASS;
    }

    public boolean connect() {
        final String DBURL = "jdbc:mysql://localhost:3306/" + DBNAME + "?useSSL=false";

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            if (!connection.isClosed()) {
                System.out.print("connection to DB is open\n" + DBUSER + "@" + DBNAME + " > ");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getdbname() {
        return DBNAME;
    }

    public String getdbuser() {
        return DBUSER;
    }
}
