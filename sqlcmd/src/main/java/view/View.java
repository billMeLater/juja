package view;

import java.util.List;

/**
 * Created by v.kalitsev on 4/12/2016.
 */
public interface View {
    void write(List message);

//    void drawTable(String[][] data);

    String read();
}
