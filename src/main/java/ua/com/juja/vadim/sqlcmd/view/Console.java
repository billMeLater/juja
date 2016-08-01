package ua.com.juja.vadim.sqlcmd.view;

import dnl.utils.text.table.TextTable;

import java.util.List;
import java.util.Scanner;

public class Console implements View {

    @Override
    public void write(List messages) {
        if (messages.get(0).toString().equals("_drawTable_")) {
            String[] header = new String[1];
            if ((Boolean) messages.get(1)) {
                header = (String[]) messages.get(3);
            }
            Object[][] body = new Object[messages.size() - 4][1];
            for (int i = 4; i < messages.size(); i++) {
                body[i - 4] = (Object[]) messages.get(i);
            }
            TextTable table = new TextTable(header, body);
            table.setAddRowNumbering((Boolean) messages.get(2));
            table.printTable();
        } else {
            for (Object message : messages) {
                System.out.print("\n" + message.toString());
            }
        }
    }

    @Override
    public String read() {
        Scanner userinput = new Scanner(System.in);
        return userinput.nextLine();
    }
}
