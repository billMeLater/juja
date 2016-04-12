package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by vadim on 4/10/16.
 */
public class Utils {

    public static boolean readline(DatabaseManager mydb, String command) {

        String[] tokens = command.split(" ");
        String commandParams = "";
        for (int i = 1; i < tokens.length; i++) {
            commandParams += tokens[i];
        }

        Method[] declaredMethods = MySQLDatabaseManager.class.getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (m.getName().equals(tokens[0])) {
                try {
                    m.invoke(mydb, commandParams);
                    return true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

}
