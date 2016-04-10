package help;

import dbstuff.DBStuff;

import java.lang.reflect.Method;

/**
 * Created by vadim on 4/5/16.
 */
public class Help {
    public static void commandList() {
        Method[] declaredMethods = DBStuff.class.getDeclaredMethods();
        System.out.println("Only next available");
        for (Method m : declaredMethods) {
            System.out.println("\t" + m.getName());
        }
    }
}
