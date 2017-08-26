package ua.com.juja.vadim.sqlcmd.controller;

import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class Command {

    protected String name;
    protected String INFO;
    protected String DEFAULT_PARAM;

    protected Command() {
        this.name = "command";
        this.INFO = "doing that";
        this.DEFAULT_PARAM = "";
    }

    private String getName() {
        return name;
    }

    private String getINFO() {
        return INFO;
    }

    private String getDEFAULT_PARAM() {
        return DEFAULT_PARAM;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Command.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Command other = (Command) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public List execute(DatabaseManager databaseManager, List params) {
        return new ArrayList(0);
    }

    protected boolean validatedParams(List params, String required) {
        return params.size() >= required.split("\\|").length - 1;
    }

    protected boolean validatedParams(List params, String required, Boolean multi) {
        if (multi) {
            System.out.println(required.split("\\|...\\|", 0));
        }
        return params.size() >= required.split("\\|").length - 1;
    }

    protected List _usage() {
        List<String> result = new ArrayList<>();
        result.add("USAGE:");
        result.add("\t" + this.getName() + this.getDEFAULT_PARAM());
        return result;
    }

    public String _info() {
        List<String> result = new ArrayList<>();
        result.add(this.getName() + this.getDEFAULT_PARAM());
        result.add(this.getINFO());
        return this.getName() + this.DEFAULT_PARAM + " - " + this.INFO;
    }

}
