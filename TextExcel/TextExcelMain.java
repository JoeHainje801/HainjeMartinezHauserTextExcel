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
                    if (parts[0].length() > 1 && parts[0].length() < 4) {
                        String cellRow = parts[0].substring(0, 1);
                        String cellColumn = parts[0].substring(1);
                        
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

}