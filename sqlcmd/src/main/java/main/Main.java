package main;

import dbstuff.DBStuff;
import help.Help;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by v.kalitsev on 3/25/2016.
 */
public class Main {

    public static void main(String[] args) {

        System.out.print("Hi, please type command or ? for help\n> ");
        Scanner userinput = new Scanner(System.in);
        boolean allowed = false;

        while (true) {
            String command = userinput.nextLine();
            if (command.equals("exit")) {
                break;
            }
            String[] tokens = command.split(" ");
            DBStuff mydb = new DBStuff("juja", "jujauser", "password");

            Method[] declaredMethods = DBStuff.class.getDeclaredMethods();
            for (Method m : declaredMethods) {
                if (m.getName().equals(tokens[0])) {
                    allowed = true;
                    try {
                        m.invoke(mydb);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!allowed) {
                System.out.println("Command not found!");
                Help.commandList();
                continue;
            }
            try {
                while (!mydb.connection.isClosed()) {
                    String question = userinput.nextLine();
                    System.out.print(mydb.getdbuser() + "@" + mydb.getdbname() + " > " + question);
                    if (question.equals("disconnect")) {
                        mydb.connection.close();
                    }
                }
                System.out.print("\nConnection to DB was closed\n> ");
                allowed = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
