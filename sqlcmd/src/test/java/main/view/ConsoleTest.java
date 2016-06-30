package main.view;

import org.junit.Test;
import view.Console;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ConsoleTest {
    Console view = new Console();

//    @Test
//    public void read() {
//
//    }

    @Test
    public void write() {
        String param = "hi there";
        PrintStream out = mock(PrintStream.class);
        System.setOut(out);
        List result = new ArrayList(1);
        result.add(param);
        view.write(result);
        verify(out).print(eq("\n" + param));
    }

//    @Test
//    public void drawTable() {
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        List result = new ArrayList(1);
//        result.add("_drawTable_");
//        result.add(true);
//        result.add(true);
//        result.add(new String[]{"Existing tables for DB 'test'"});
//        result.add(new String[]{"address"});
//        result.add(new String[]{"user"});
//        view.write(result);
//        verify(out).print(eq(""));
//    }
}
