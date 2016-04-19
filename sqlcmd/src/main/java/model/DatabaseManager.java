package model;

import java.util.List;

/**
 * Created by vadim on 4/12/16.
 */
public interface DatabaseManager {
    List connect(String params);

    List disconnect(String params);

    List exit(String params);

    List listTables(String params);

    List showRecords(String table);

    List _usage(String methodName, String parameter, String info);

    List _connectionInfo(String string);
}
