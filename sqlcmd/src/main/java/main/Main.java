package main;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by v.kalitsev on 3/25/2016.
 */
public class Main {

    public static final String DBURL = "jdbc:mysql://localhost:3306/juja?useSSL=false";
    public static final String DBUSER = "jujauser";
    public static final String DBPASS = "password";

    public static void main(String[] args) {

        System.out.println("What is your name?");
        Scanner userName = new Scanner(System.in);
        System.out.println("Hi [" + userName.nextLine() + "]");

        Connection connection;

        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
