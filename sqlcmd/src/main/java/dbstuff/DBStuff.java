package dbstuff;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBStuff {

    public void connect(String DBURL, String DBUSER, String DBPASS) {
        Connection connection;

        try

        {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            if (!connection.isClosed()) {
                System.out.println("connection to DB is open");
            }

            connection.close();

            if (connection.isClosed()) {
                System.out.println("connection to DB was closed");
            }
        } catch (
                SQLException e
                )

        {
            e.printStackTrace();
        }

    }
}
