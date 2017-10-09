package ua.com.juja.vadim.sqlcmd.view;

import dnl.utils.text.table.TextTable;
import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;

import java.util.Scanner;


public class Console implements View {
    @Override
    public void write(CommandOutput data) {
        Object[][] body = data.getBody();

        if (data.isTable()) {
            TextTable table = new TextTable(data.getHeader(), body);
            table.setAddRowNumbering(data.isRowNumbers());
            table.printTable();
        } else {
            Integer xSize = body.length;
            if (xSize > 0) {
                Integer ySize = body[0].length;
                for (int x = 0; x < xSize; x++) {
                    System.out.println();
                    for (int y = 0; y < ySize; y++) {
                        System.out.print(body[x][y] + " ");
                    }

                }
            }
        }
    }

    @Override
    public String read() {
        Scanner userinput = new Scanner(System.in);
        return userinput.nextLine();
    }
}
