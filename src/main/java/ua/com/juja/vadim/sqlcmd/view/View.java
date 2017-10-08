package ua.com.juja.vadim.sqlcmd.view;

import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;


public interface View {
    void write(CommandOutput data);

    String read();
}
