package TextExcel;
import java.util.Scanner;
public class TextExcelMain {
    public static void main(String[] args) {
        System.out.println("Welcome to Text Excel!");
        System.out.print("\nEnter a command:");
        Spreadsheet theSheet = new Spreadsheet();
        boolean fail;
       
        boolean done = false;
        while (!done) {
            Scanner inputScanner = new Scanner(System.in);
            String command_input = inputScanner.nextLine();
            fail = false;
           
            //checks for commands
            if (command_input.equalsIgnoreCase("print")) {
                System.out.println(theSheet.toString());
            } else if (command_input.equalsIgnoreCase("clear")) {
                theSheet.sheetClear();

            } else if (command_input.equalsIgnoreCase("quit")) {
                System.out.println("Farewell!");
                done = true;
                inputScanner.close();
            } else if (command_input.equalsIgnoreCase("test")) {
                FormulaCell.test();
                System.out.println("Test Pass");
            } else {

                //parse the input
                String[]parts = command_input.split(" ");

                //checks for and outputs the value of a single cell
                if (parts.length == 1) {
                    if (isCell(parts[0])) {
                       int column = parts[0].charAt(0) - 'A';
                       int row = Integer.parseInt(parts[0].substring(1)) - 1;
                       theSheet.cellAccess(row, column);

                    } else {
                        fail = true;
                    }

                }

                //clears a specified cell
                if (parts.length == 2) {
                    if (parts[0].equalsIgnoreCase("clear") && isCell(parts[1])) {
                        theSheet.cellClear(parts[1]);
                        

                    } else {
                        fail = true;
                    }
                }

                //checks for specific type of cell inputed
                if (parts.length > 2) {

                   
                     if (parts.length == 3 && isCell(parts[0]) && parts[1].equalsIgnoreCase("=") && isDouble(parts[2])) {
                            // SET AS NUMBER CELL ------------------------------
                            theSheet.toNumberCell(parts[0], parts[2]);
                            

                        } else if (isCell(parts[0]) && parts[1].equalsIgnoreCase("=") && isString(parts[2], parts[parts.length - 1]) ) {
                        // SET AS STRING CELL ------------------------------
                        
                          String theString = command_input.substring(command_input.indexOf("\"") + 1, command_input.length() - 1);
                          theSheet.toStringCell(parts[0], theString);
                          

                        //Dont take off parentheses for input value, only for display value
                        } else if (isCell(parts[0]) && parts[1].equalsIgnoreCase("=") && parts[2].equalsIgnoreCase("(") && parts[parts.length - 1].equalsIgnoreCase(")")) {
                        // TEST if formula; if so, set as formula cell ---------------------------
                           String formula = command_input.substring(command_input.indexOf("("));
                           if (testIfFormula(formula)) {
                               theSheet.toFormulaCell(parts[0], formula);

                            } else {
                            fail = true;
                           
                             }
                       

                    } else if (parts[0].equalsIgnoreCase("sorta") && parts.length == 4) {
                        if (isCell(parts[1]) && isCell(parts[3]) && parts[2].equalsIgnoreCase("-")) {
                            theSheet.sorta(parts[1], parts[3]);

                        } else {
                            fail = true;
                        }

                    } else if (parts[0].equalsIgnoreCase("sortb") && parts.length == 4) {
                        if (isCell(parts[1]) && isCell(parts[3]) && parts[2].equalsIgnoreCase("-")) {
                            // SORTB COMMAND ------------------------------
                            theSheet.sortb(parts[1], parts[3]);
                            
                            

                        } else {
                            fail = true;
                        }
                        
                    } else if (parts[0].equalsIgnoreCase("clear") && parts.length == 4) {
                        if (isCell(parts[1]) && isCell(parts[3]) && parts[2].equalsIgnoreCase("-")) {
                            theSheet.clearCells(parts[1], parts[3]);
                            

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
                System.out.print("Enter a command:");
            } else if(!done) {
                System.out.print("Enter a command:");
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

    public static boolean testIfFormula(String formula) {
        int fail = 0;
        String[]formulaParts = formula.split(" ");
        for (int i = 1; i < (formulaParts.length - 1); i++) {
            if (formulaParts[1].equalsIgnoreCase("avg") || formulaParts[1].equalsIgnoreCase("sum")) {
                if (isCell(formulaParts[2]) && isCell(formulaParts[4]) && formulaParts[3].equalsIgnoreCase("-")) {

                } else {
                    fail++;
                }


            } else {
                if (i % 2 == 0) {
                    if (formulaParts[i].equalsIgnoreCase("-") || formulaParts[i].equalsIgnoreCase("+") || formulaParts[i].equalsIgnoreCase("*") || formulaParts[i].equalsIgnoreCase("/")) {

                    } else {
                        fail++;
                    }

                }
                if (i%2==1) {
                    if (isCell(formulaParts[i]) || isDouble(formulaParts[i])) {

                    } else {
                        fail++;
                    }
                }


            }
            
        }


        if (fail == 0) {
            return true;
            
        } else {
            return false;
        }

    }

   
    

}