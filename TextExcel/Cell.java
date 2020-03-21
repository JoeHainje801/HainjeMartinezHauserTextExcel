package TextExcel;
public class Cell {
    public static int CELL_STRING_WIDTH;
    private String CellString;
    public Cell() {
        CellString = "            ";

    }
    public String toString() {
        for (int i = CELL_STRING_WIDTH; i > 0; i--) {
            if (CellString.length() < 12) {
                if (i%2 == 0) {
                    CellString += " ";
                } else {
                    CellString = " " + CellString;
                }
            }
        }
        return CellString;
    }
}