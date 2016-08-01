package ua.com.juja.vadim.sqlcmd.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class ConsoleTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    Console view = new Console();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void write() {
        List result = new ArrayList(1);
        String param = "hi there";
        result.add(param);
        view.write(result);
        assertEquals("\n" + param, outContent.toString());

    }

    @Test
    public void drawTable() {
        List result = new ArrayList(1);
        result.add("_drawTable_");
        result.add(true);
        result.add(true);
        result.add(new String[]{"Existing tables for DB 'test'"});
        result.add(new String[]{"address"});
        result.add(new String[]{"user"});
        view.write(result);
        assertEquals("   _______________________________\n" +
                "   | Existing tables for DB 'test'|\n" +
                "   |==============================|\n" +
                "1. | address                      |\n" +
                "2. | user                         |\n", outContent.toString());
    }
}
