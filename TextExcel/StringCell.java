package TextExcel;
public class StringCell extends Cell {
    private String CellStr;

    public StringCell() {

    }

    @Override
    public String SheetString(Spreadsheet spreadsheet) {
        String CellStrSheet = this.CellStr;
        for (int i = CELL_STRING_WIDTH; i > 0; i--) {
            if (CellStrSheet.length() < 12) {
                if (i%2 == 0) {
                    CellStrSheet += " ";
                } else {
                    CellStrSheet = " " + CellStrSheet;
                }
            }
        }
        if (CellStrSheet.length() > 12) {
            CellStrSheet = CellStrSheet.substring(0,11) + ">";
        }
        return CellStrSheet;
    }
    
    public String getValue() {
        return ("\"" + this.CellStr + "\"");

    }

    public void setValue(String setVal) {
        this.CellStr = setVal;

    }
    
}