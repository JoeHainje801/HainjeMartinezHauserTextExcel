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
                        System.out.println(0);

                    } else {
                        fail = true;
                    }
                }


                if (parts.length > 2) {

                    if (parts.length == 3) {
                        if (isCell(parts[0]) && parts[1].equalsIgnoreCase("=") && isDouble(parts[2])) {
                            // SET AS NUMBER CELL ------------------------------
                            System.out.println(1);

                        } else {
                            fail = true;
                        }

                    } else if (isCell(parts[0]) && parts[1].equalsIgnoreCase("=") && isString(parts[2], parts[parts.length - 1]) ) {
                        // SET AS STRING CELL ------------------------------
                        System.out.println(2);


                    } else if (isCell(parts[0]) && parts[1].equalsIgnoreCase("=") && parts[2].equalsIgnoreCase("(") && parts[parts.length - 1].equalsIgnoreCase(")")) {
                        // TEST if formula; if so, set as formula cell ---------------------------
                        System.out.println(3);

                    } else if (parts[0].equalsIgnoreCase("sorta") && parts.length == 4) {
                        if (isCell(parts[1]) && isCell(parts[3]) && parts[2].equalsIgnoreCase("-")) {
                            // SORTA COMMAND -----------------------------
                            System.out.println(4);

                        } else {
                            fail = true;
                        }

                    } else if (parts[0].equalsIgnoreCase("sortb") && parts.length == 4) {
                        if (isCell(parts[1]) && isCell(parts[3]) && parts[2].equalsIgnoreCase("-")) {
                            // SORTB COMMAND ------------------------------
                            System.out.println(5);

                        } else {
                            fail = true;
                        }
                        
                    } else if (parts[0].equalsIgnoreCase("clear") && parts.length == 4) {
                        if (isCell(parts[1]) && isCell(parts[3]) && parts[2].equalsIgnoreCase("-")) {
                            // CLEAR RANGE COMMAND ------------------------------
                            System.out.println(6);

                        } else {
                            fail = true;
                        }

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
    public static boolean isDouble(String input) {
        int fail = 0;
        int periods = 0;
        for (int i = 0; i < input.length(); i++) {
            if (i == 0) {
                if ((input.charAt(i) < '0' || input.charAt(i) > '9') && !(input.charAt(i) == '.' || input.charAt(i) == '-')) {
                    fail++;

                }
                if (input.charAt(i) == '.') {
                    periods++;
                }
            } else {
                if ((input.charAt(i) < '0' || input.charAt(i) > '9') && !(input.charAt(i) == '.')) {
                    fail++;
                }
                if (input.charAt(i) == '.') {
                    periods++;
                }
                if (periods > 1) {
                    fail++;
                }
            }
        }
        if (fail == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isString (String a, String b) {
        if (a.charAt(0) == '"' && b.charAt(b.length() - 1) == '"') {
            return true;
        } else {
            return false;
        }
    }

}