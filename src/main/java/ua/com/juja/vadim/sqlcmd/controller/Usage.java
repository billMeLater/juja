package ua.com.juja.vadim.sqlcmd.controller;

import java.util.ArrayList;
import java.util.List;

public class Usage {

    public static List usage(String usage, String info) {
        List result = new ArrayList();
        result.add("\n" + usage + " ");
        result.add(info);
        return result;
    }
}
