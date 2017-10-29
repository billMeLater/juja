package ua.com.juja.vadim.sqlcmd.service;

import java.util.List;

public interface Service {
    List<String> commandsList();

    void connect(String dbName, String dbUser, String dbPass);
}
