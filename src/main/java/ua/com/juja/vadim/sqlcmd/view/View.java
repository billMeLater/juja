package ua.com.juja.vadim.sqlcmd.view;

import java.util.List;

public interface View {
    void write(List message);

    String read();
}
