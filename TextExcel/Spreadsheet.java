package TextExcel;
public class Spreadsheet {

    public static int ROW = 10;
    public static int COLUMN = 7;
    public static int CELL_WIDTH = 12;
    public static int BORDER_WIDTH = 1; 
    public static int SPREADSHEET_WIDTH = (CELL_WIDTH + BORDER_WIDTH)*(COLUMN + 1);

    private Cell[][] spreadsheet;
    private String spreadsheetString;
    
    public Spreadsheet() {
        spreadsheet = new Cell[ROW][COLUMN];
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
              spreadsheet[row][column] = new Cell();

            }
        }

    }

    public String toString() {
        spreadsheetString = "";
        for (int xPos = 1; xPos <= SPREADSHEET_WIDTH; xPos++ ) {
            if ((xPos % (CELL_WIDTH+BORDER_WIDTH)-6 == 0) && (xPos > CELL_WIDTH+BORDER_WIDTH)) {

                spreadsheetString += (char)((xPos-6) / (CELL_WIDTH+BORDER_WIDTH) + 64);

            } else if (xPos % (CELL_WIDTH+BORDER_WIDTH) == 0) {
                spreadsheetString += "|";
            } else {
                spreadsheetString += " ";
            }
        }
        
            for (int row = 0; row < ROW; row++ ) {
               spreadsheetString += "\n";
               for (int f = 1; f <= COLUMN+1; f++) {
                for (int k = 1; k <= CELL_WIDTH; k++) {
                    spreadsheetString += "-";
                }
                spreadsheetString += "+";
               }
               spreadsheetString += "\n";
               for (int column = 0; column < COLUMN; column++) {
                  if (column==0) {
                    String sideCell = "";
                    int row_num = row+1;
                    sideCell += row_num;  
                    for (int s = CELL_WIDTH; s > 0; s--) {
                        if (sideCell.length() < 12) {
                            if (s%2 == 0) {
                                sideCell += " ";
                            } else {
                                sideCell = " " + sideCell;
                            }
                        }
                    }
                      spreadsheetString += sideCell;
                      spreadsheetString += "|";
                  }

                    spreadsheetString += spreadsheet[row][column].SheetString();//"            ";spreadsheet[row][column]; spreadsheet[row][column].toString();     
                    spreadsheetString += "|";
                    
               }
             }
             spreadsheetString += "\n";
             for (int f = 1; f <= COLUMN+1; f++) {
              for (int k = 1; k <= CELL_WIDTH; k++) {
                  spreadsheetString += "-";
              }
              spreadsheetString += "+";
             }

        return spreadsheetString;
    }

    public void cellAccess(int row, int column) {
        System.out.println(this.spreadsheet[row][column].getValue());
    }

    public void toNumberCell(String cell, String d) {
        int row = Integer.parseInt(cell.substring(1)) - 1;
        int column = cell.charAt(0) - 'A';
        spreadsheet[row][column] = new NumberCell();
        spreadsheet[row][column].setValue(d);

    }

    public void toStringCell(String cell, String s) {
        int row = Integer.parseInt(cell.substring(1)) - 1;
        int column = cell.charAt(0) - 'A';
        spreadsheet[row][column] = new StringCell();
        spreadsheet[row][column].setValue(s);
    }

    public void sheetClear() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                spreadsheet[i][j] = new Cell();
            }
        }
    }

    public void cellClear(String cell) {
        int row = Integer.parseInt(cell.substring(1)) - 1;
        int column = cell.charAt(0) - 'A';
        spreadsheet[row][column] = new Cell();

    }
}