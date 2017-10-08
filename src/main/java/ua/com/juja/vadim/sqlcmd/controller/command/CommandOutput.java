package ua.com.juja.vadim.sqlcmd.controller.command;


public class CommandOutput {
    private Boolean drawTable = false;
    private String[] header;
    private Boolean rowNumbers = false;
    private Object[][] body = new String[0][0];

    public CommandOutput() {
    }

    public CommandOutput(Boolean drawTable) {
        this.drawTable = drawTable;
    }

    public CommandOutput(Boolean drawTable, String[] header) {
        this.drawTable = drawTable;
        this.header = header;
    }

    public CommandOutput(Boolean drawTable, String[] header, Boolean rowNumbers) {
        this.drawTable = drawTable;
        this.header = header;
        this.rowNumbers = rowNumbers;
    }

    public Boolean add(String record) {
        Integer xSize = body.length;
        Integer ySize = 0;

        Object[][] tmpBody;

        if (xSize > 0) {
            ySize = body[0].length;
            tmpBody = new Object[xSize + 1][ySize];

            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    tmpBody[x][y] = body[x][y];
                }
            }
        } else {
            tmpBody = new Object[xSize + 1][ySize + 1];
        }
        tmpBody[xSize][0] = record;
        body = tmpBody;
        return true;
    }

    public Boolean add(String[] record) {

        return true;
    }

    public Boolean isTable() {
        return drawTable;
    }

    public Boolean isRowNumbers() {
        return rowNumbers;
    }

    public String[] getHeader() {
        return header;
    }

    public Object[][] getBody() {
        return body;
    }
}
