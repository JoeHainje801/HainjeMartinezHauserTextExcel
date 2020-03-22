package TextExcel;
import java.util.Scanner;
public class TextExcelMain {
    public static void main(String[] args) {
        Spreadsheet theSheet = new Spreadsheet();
        boolean fail;
       
        boolean done = false;
        while (!done) {
            Scanner inputScanner = new Scanner(System.in);
            String command_input = inputScanner.nextLine();
            fail = false;
           
            if (command_input.equalsIgnoreCase("print")) {
                System.out.println(theSheet.toString());
            } else if (command_input.equalsIgnoreCase("clear")) {

            } else if (command_input.equalsIgnoreCase("quit")) {
                done = true;
                inputScanner.close();
            } else {


                String[]parts = command_input.split(" ");

                if (parts.length == 1) {
                    if (isCell(parts[0])) {
                       int column = parts[0].charAt(0) - 'A';
                       int row = Integer.parseInt(parts[0].substring(1)) - 1;
                       theSheet.cellAccess(row, column);

                    } else {
                        fail = true;
                    }

                }


                if (parts.length == 2) {
                    if (parts[0].equalsIgnoreCase("clear") && isCell(parts[0])) {
                        // CLEAR CELL COMMAND ----------------------------

                    } else {
                        fail = true;
                    }
                }

            }


            if (fail) {
                System.out.println("Could not compute");
            }
        }
       


    }
    public static boolean isCell(String cell) {
        int fail = 0;
        if (cell.length() > 1 && cell.length() < 4) {
            char cellRow = cell.charAt(0);
            String cellColumn = cell.substring(1);
            if (cellRow < 'A' || cellRow > 'G') {
                fail++;
            } 
            for (int i = 0; i < cellColumn.length(); i++) {
                if (cellColumn.charAt(i) < '0' || cellColumn.charAt(i) > '9') {
                    fail++;
                }
                if (i == 1) {
                    if (!(cellColumn.charAt(0) == '1') || cellColumn.charAt(1) > '0') {
                        fail++;
                    }
                }
            }   
        } else {
           fail++;
        }
        if (fail == 0) {
            return true;
        } else {
            return false;
        }
    }

}