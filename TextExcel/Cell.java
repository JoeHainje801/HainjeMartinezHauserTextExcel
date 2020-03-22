package TextExcel;
public class Cell {
    public static int CELL_STRING_WIDTH = 12;
    private String CellString;
    private String value;
    public Cell() {
        CellString = "";
        value = "<empty>";

    }
    public String SheetString() {
        for (int i = CELL_STRING_WIDTH; i > 0; i--) {
            if (CellString.length() < 12) {
                if (i%2 == 0) {
                    CellString += " ";
                } else {
                    CellString = " " + CellString;
                }
            }
        }
        if (CellString.length() > 12) {
            CellString = CellString.substring(0,10) + ">";
        }
        return CellString;
    }
    
    public String getValue() {
        return this.value;

    }

}