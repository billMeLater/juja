package ua.com.juja.vadim.sqlcmd.controller;

import ua.com.juja.vadim.sqlcmd.controller.command.CommandOutput;
import ua.com.juja.vadim.sqlcmd.model.DatabaseManager;

import java.util.List;

public class Command {
    protected String name;
    protected String info;
    protected String defaultParam = "";

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Command.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Command other = (Command) obj;
        return (this.name == null) ? (other.name == null) : this.name.equals(other.name);
    }

    public CommandOutput execute(DatabaseManager databaseManager, List<String> params) {
        return new CommandOutput();
    }

    protected boolean validatedParams(List params, String required, Boolean multi) {
        if (multi) {
            return params.size() >= required.split("\\|...\\|", 0)[0].split("\\|").length - 1;
        }
        return params.size() >= required.split("\\|").length - 1;
    }

    protected CommandOutput _usage() {
        CommandOutput result = new CommandOutput();
        result.add("USAGE");
        result.add("\t" + name + defaultParam);
        return result;
    }

    public String _info() {
        return name + defaultParam + " - " + info;
    }

}
