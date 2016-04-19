package view;

import dnl.utils.text.table.TextTable;

import java.util.Scanner;

/**
 * Created by v.kalitsev on 4/12/2016.
 */
public class Cosole implements View {

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void drawTable(String[][] data) {
        Object[][] body = new Object[data.length - 1][data[0].length];
        for (int i = 1; i < data.length; i++) {
            body[i - 1] = data[i];
        }

        TextTable table = new TextTable(data[0], body);
        table.setAddRowNumbering(true);
        table.printTable();
    }

    @Override
    public String read() {
        Scanner userinput = new Scanner(System.in);
        return userinput.nextLine();
    }
}
