package TextExcel;

import java.util.ArrayList;

public class Spreadsheet {

    public static int ROW = 10;
    public static int COLUMN = 7;
    public static int CELL_WIDTH = 12;
    public static int BORDER_WIDTH = 1;
    public static int SPREADSHEET_WIDTH = (CELL_WIDTH + BORDER_WIDTH) * (COLUMN + 1);

    private Cell[][] spreadsheet;

    public Spreadsheet() {
        spreadsheet = new Cell[ROW][COLUMN];
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                spreadsheet[row][column] = new Cell();

            }
        }

    }

    public String toString() {
        String spreadsheetString = "";
        //Adds row of column labels
        for (int xPos = 1; xPos <= SPREADSHEET_WIDTH; xPos++) {
            if ((xPos % (CELL_WIDTH + BORDER_WIDTH) - 6 == 0) && (xPos > CELL_WIDTH + BORDER_WIDTH)) {

                spreadsheetString += (char) ((xPos - 6) / (CELL_WIDTH + BORDER_WIDTH) + 64);

            } else if (xPos % (CELL_WIDTH + BORDER_WIDTH) == 0) {
                spreadsheetString += "|";
            } else {
                spreadsheetString += " ";
            }
        }

        for (int row = 0; row < ROW; row++) {
            spreadsheetString += "\n";
            //Adds borders above a row
            for (int f = 1; f <= COLUMN + 1; f++) {
                for (int k = 1; k <= CELL_WIDTH; k++) {
                    spreadsheetString += "-";
                }
                spreadsheetString += "+";
            }
            spreadsheetString += "\n";
            //Adds borders between cells for a whole row
            for (int column = 0; column < COLUMN; column++) {
                if (column == 0) {
                    String sideCell = "";
                    int row_num = row + 1;
                    sideCell += row_num;
                    for (int s = CELL_WIDTH; s > 0; s--) {
                        if (sideCell.length() < 12) {
                            if (s % 2 == 0) {
                                sideCell += " ";
                            } else {
                                sideCell = " " + sideCell;
                            }
                        }
                    }
                    spreadsheetString += sideCell;
                    spreadsheetString += "|";
                }

                spreadsheetString += spreadsheet[row][column].SheetString(this);// Adds a cells display value to the sheet
                                                                            // spreadsheet[row][column].toString();
                spreadsheetString += "|";

            }
        }
        spreadsheetString += "\n";
        for (int f = 1; f <= COLUMN + 1; f++) {
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

    public void toFormulaCell(String cell, String formula) {
        int row = Integer.parseInt(cell.substring(1)) - 1;
        int column = cell.charAt(0) - 'A';
        spreadsheet[row][column] = new FormulaCell();
        spreadsheet[row][column].setValue(formula);
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

    public void clearCells(String cellStart, String cellEnd) {
        int columnS = cellStart.charAt(0) - 'A';
        int rowS = Integer.parseInt(cellStart.substring(1)) - 1;
        int columnE = cellEnd.charAt(0) - 'A';
        int rowE = Integer.parseInt(cellEnd.substring(1)) - 1;
        for (int i = rowS; i <= rowE; i++) {
            for (int j = columnS; j <= columnE; j++) {
                spreadsheet[i][j] = new Cell();
            }
        }

    }

    public void sorta(String cellStart, String cellEnd) {
        if (cellStart.charAt(0) == cellEnd.charAt(0)
                && !(cellStart.substring(1).equalsIgnoreCase(cellEnd.substring(1)))) {
            int column = cellStart.charAt(0) - 'A';
            int rowStart = Integer.parseInt(cellStart.substring(1)) - 1;
            int rowEnd = Integer.parseInt(cellEnd.substring(1));
            double dubArray[] = new double[rowEnd - rowStart];
            for (int i = rowStart; i < rowEnd; i++) {
                dubArray[i - rowStart] = spreadsheet[i][column].getNumberValue(this);

            }
            sortforA(dubArray, dubArray.length);
            for (int i = rowStart; i < rowEnd; i++) {
                spreadsheet[i][column].setValue("" + dubArray[i - rowStart]);

            }

        } else if (cellStart.substring(1).equalsIgnoreCase(cellEnd.substring(1))
                && !(cellStart.charAt(0) == cellEnd.charAt(0))) {
            int row = Integer.parseInt(cellStart.substring(1)) - 1;
            int colStart = cellStart.charAt(0) - 'A';
            int colEnd = cellEnd.charAt(0) - 'A' + 1;
            double dubArray[] = new double[colEnd - colStart];
            for (int i = colStart; i < colEnd; i++) {
                dubArray[i - colStart] = spreadsheet[row][i].getNumberValue(this);

            }
            // Have to write a sort, cannot use Array.sort
            sortforA(dubArray, dubArray.length);
            for (int i = colStart; i < colEnd; i++) {
                spreadsheet[row][i].setValue("" + dubArray[i - colStart]);

            }

        }

    }

    public void sortb(String cellStart, String cellEnd) {
        if (cellStart.charAt(0) == cellEnd.charAt(0)
                && !(cellStart.substring(1).equalsIgnoreCase(cellEnd.substring(1)))) {
            int column = cellStart.charAt(0) - 'A';
            int rowStart = Integer.parseInt(cellStart.substring(1)) - 1;
            int rowEnd = Integer.parseInt(cellEnd.substring(1));
            double dubArray[] = new double[rowEnd - rowStart];
            for (int i = rowStart; i < rowEnd; i++) {
                dubArray[i - rowStart] = spreadsheet[i][column].getNumberValue(this);

            }
            sortforD(dubArray, dubArray.length);
            /*
             * for (int i = rowEnd; i >= rowStart; i--) { spreadsheet[rowStart + rowEnd -
             * i][column].setValue("" + dubArray[i-rowStart]);
             * 
             * }
             */
            for (int i = rowStart; i < rowEnd; i++) {
                spreadsheet[i][column].setValue("" + dubArray[i - rowStart]);

            }

        } else if (cellStart.substring(1).equalsIgnoreCase(cellEnd.substring(1))
                && !(cellStart.charAt(0) == cellEnd.charAt(0))) {
            int row = Integer.parseInt(cellStart.substring(1)) - 1;
            int colStart = cellStart.charAt(0) - 'A';
            int colEnd = cellEnd.charAt(0) - 'A' + 1;
            double dubArray[] = new double[colEnd - colStart];
            for (int i = colStart; i < colEnd; i++) {
                dubArray[i - colStart] = spreadsheet[row][i].getNumberValue(this);

            }
            // Have to write a sort, cannot use Array.sort
            sortforD(dubArray, dubArray.length);
            for (int i = colStart; i < colEnd; i++) {
                spreadsheet[row][i].setValue("" + dubArray[i - colStart]);

            }

        }

    }

    private static void sortforA(double theArray[], int l) {
        for (int i = 1; i < l; i++) {
            int j = i;
            double currentNum = theArray[i];
            while ((j > 0) && (theArray[j - 1] > currentNum)) {
                theArray[j] = theArray[j - 1];
                j--;
            }
            theArray[j] = currentNum;
        }
    }

    private static void sortforD(double theArray[], int l) {
        for (int i = 1; i < l; i++) {
            int j = i;
            double currentNum = theArray[i];
            while ((j > 0) && (theArray[j - 1] < currentNum)) {
                theArray[j] = theArray[j - 1];
                j--;
            }
            theArray[j] = currentNum;
        }
    }

    public ArrayList<Double> getAllReferences(String formula) {
        return null;
    }

    public Double getNumberValue(String cell) {
        int row = Integer.parseInt(cell.substring(1)) - 1;
        int col = cell.charAt(0) - 'A';
        return spreadsheet[row][col].getNumberValue(this);
    }

    public ArrayList<Double> getCellRange(String startCell, String endCell) {
        int INDEX_OFFSET = 1;
        int startCol = startCell.charAt(0) - 'A';
        int endCol = endCell.charAt(0) - 'A';
        int startRow = Integer.parseInt(startCell.substring(1)) - INDEX_OFFSET;
        int endRow = Integer.parseInt(endCell.substring(1)) - INDEX_OFFSET;
        ArrayList<Double> cellsInRange = new ArrayList<>();
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                cellsInRange.add(spreadsheet[i][j].getNumberValue(this));
            }
        }
        return cellsInRange;
    }
}