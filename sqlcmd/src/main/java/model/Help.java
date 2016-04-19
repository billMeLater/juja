package model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 4/5/16.
 */
public class Help {
    public static List commandList() {
        List result = new ArrayList();

        Method[] declaredMethods = MySQLDatabaseManager.class.getDeclaredMethods();
        result.add("Next commands available");
        for (Method m : declaredMethods) {
            if (m.getName().startsWith("_")) {
                continue;
            }
            result.add(m.getName());
        }
        return result;
    }
}
