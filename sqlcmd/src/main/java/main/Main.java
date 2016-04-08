package main;

import dbstuff.DBStuff;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by v.kalitsev on 3/25/2016.
 */
public class Main {

    public static void main(String[] args) {

        System.out.print("Hi, please type command or ? for help\n> ");
        Scanner userinput = new Scanner(System.in);

        DBStuff mydb = new DBStuff("juja", "jujauser", "password");
        mydb.connect();
        try {
            while (!mydb.connection.isClosed()) {
                String question = userinput.nextLine();
                System.out.print(mydb.getdbuser() + "@" + mydb.getdbname() + " > " + question);
                if (question.equals("disconnect")) {
                    mydb.connection.close();
                }
            }
            System.out.print("\nConnection to DB was closed\n> ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
