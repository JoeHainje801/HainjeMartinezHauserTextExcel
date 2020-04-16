package TextExcel;
public class NumberCell extends Cell {
    private double valueNum;
    private String stringNum;
    public NumberCell() {

    }
    public void setValue(String setVal) {
        double val = Double.parseDouble(setVal);
        this.valueNum = val;
        this.stringNum = "" + this.valueNum;
    }public String getValue() {
        return this.stringNum;

    
    }

    public String SheetString() {
        String sheetstringN = this.stringNum;
        for (int i = CELL_STRING_WIDTH; i > 0; i--) {
            if (sheetstringN.length() < 12) {
                if (i%2 == 0) {
                    sheetstringN += " ";
                } else {
                    sheetstringN = " " + sheetstringN;
                }
            }
        }
        if (sheetstringN.length() > 12) {
            sheetstringN = sheetstringN.substring(0,11) + ">";
        }
        return sheetstringN;
    }

}