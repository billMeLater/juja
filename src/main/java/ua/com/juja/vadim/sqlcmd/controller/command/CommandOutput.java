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

    public void add(String record) {
        int xSize = body.length;
        int ySize = 1;

        if (xSize > 0) {
            ySize = body[0].length;
        }

        Object[][] tmpBody = new Object[xSize + 1][ySize];
        for (int x = 0; x < xSize; x++) {
            System.arraycopy(body[x], 0, tmpBody[x], 0, ySize);
        }
        tmpBody[xSize][0] = record;

        body = tmpBody;
    }

    public void add(String[] record) {
        int xSize = body.length;
        int ySize = record.length;

        if (xSize > 0) {
            ySize = body[0].length;
        }

        Object[][] tmpBody = new Object[xSize + 1][ySize];
        for (int x = 0; x < xSize; x++) {
            System.arraycopy(body[x], 0, tmpBody[x], 0, ySize);
        }
        System.arraycopy(record, 0, tmpBody[xSize], 0, ySize);

        body = tmpBody;
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
