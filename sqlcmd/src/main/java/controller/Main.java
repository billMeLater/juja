package controller;

import model.DBStuff;
import model.Help;
import model.Utils;

import java.util.Scanner;

/**
 * Created by v.kalitsev on 3/25/2016.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Hi, please type command or ? for help ");
        Scanner userinput = new Scanner(System.in);
        DBStuff mydb = new DBStuff("", "");

        while (true) {
            System.out.print(mydb._connectionInfo(""));
            String command = userinput.nextLine();
            if (command.equals("?")) {
                Help.commandList();
            }
            if (!Utils.readline(mydb, command)) {
                System.out.println("Command not found!");
            }
        }
    }
}
