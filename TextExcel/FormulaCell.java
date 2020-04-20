package TextExcel;

import java.util.ArrayList;
import java.util.Scanner;

public class FormulaCell extends Cell {

    public FormulaCell() {
    }

    public static boolean isValid(String formula) {
        ArrayList<Object> tokens = parseTokens(formula);
        return tokens != null;
    }

    /**
     * Returns an array list of tokens for the formula from setValue
     * 
     * @return
     */
    public static ArrayList<Object> parseTokens(String formula) {
        ArrayList<Object> tokens = null;
        Scanner formulaScanner = new Scanner(formula);
        if (formulaScanner.next().equals("(")) {
            tokens = new ArrayList<Object>();
            while (formulaScanner.hasNext()) {
                Object token = nextToken(formulaScanner);
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        }
        formulaScanner.close();
        return tokens;
        // ( 1 + 1 )
    }

    private static Object nextToken(Scanner formulaScanner) {
        if (formulaScanner.hasNextDouble()) {
            return formulaScanner.nextDouble();
        }
        String token = formulaScanner.next();
        if (token.equals("+") || token.equals("-") || token.equals("/") || token.equals("*")) {
            return token;
        }
        if (token.equals("avg") || token.equals("sum")) {
            return token;
        }
        if (token.equals(")")) {
            return null;
        }
        return token;
    }

    /*public static ArrayList<Double> getValues(ArrayList<Object> tokens) {
        ArrayList<Double> numbers = new ArrayList<Double>();
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i) instanceof Double) {
                numbers.add((Double)tokens.get(i));
            }
        }
        return numbers;
    }*/

    public static double basicCalculate(Spreadsheet spreadsheet, ArrayList<Object> tokens) {
        double result = 0;
        if (tokens.size() == 1) {
            Object token = tokens.get(0);
            if (token instanceof Double) {
                result = (Double)token;
            } else {
                result = spreadsheet.getNumberValue((String)token);
            }
        }
        //loop for usual mathematical operations
        for (int i = 1; i < tokens.size(); i++) {
            while (i <= 2) {             
                if (tokens.get(i).equals("+")) {
                    result = result + (double)tokens.get(i - 1) + (double)tokens.get(i + 1);
                }
                if (tokens.get(i).equals("-")) {
                    result = result + (double)tokens.get(i - 1) - (double)tokens.get(i + 1); 
                }
                if (tokens.get(i).equals("/")) {
                    result = result + (double)tokens.get(i - 1) / (double)tokens.get(i + 1); 
                }
                if (tokens.get(i).equals("*")) {
                    result = result + (double)tokens.get(i - 1) * (double)tokens.get(i + 1); 
                }
                i++;
            }
            while (i > 2 && i < tokens.size()) {
                if (tokens.get(i).equals(")")) {
                    break;
                }
                if (tokens.get(i).equals("+")) {
                    result = result + (double)tokens.get(i + 1);
                }
                if (tokens.get(i).equals("-")) {
                    result = result - (double)tokens.get(i + 1); 
                }
                if (tokens.get(i).equals("/")) {
                    result = result / (double)tokens.get(i + 1); 
                }
                if (tokens.get(i).equals("*")) {
                    result = result * (double)tokens.get(i + 1); 
                }
                i++;
            }
        }
        return result;
    }

    public static double calcAvg(ArrayList<Double> numbers) {
        double result = 0;
        for (int j = 0; j < numbers.size(); j++){
            result = result + numbers.get(j);
        }
        result = result / numbers.size();
        return result;

    }

    public static double calcSum(ArrayList<Double> numbers) {
        double result = 0;
        for (int i = 0; i < numbers.size(); i++) {
            result = result + numbers.get(i);
        }
        return result;
    }

    public Double getNumberValue(Spreadsheet spreadsheet) {
        String formula = this.getValue();
        return basicCalculate(spreadsheet, parseTokens(formula));
    }

    public static void test() {
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.toNumberCell("B1", "3");
        testFormulaWorks(spreadsheet, "( 1 )", 1.0);
        testFormulaWorks(spreadsheet, "( 1 + 2 )", 3.0);
        testFormulaWorks(spreadsheet, "( 1 * 2 + 3 / 5 - 1 )", 0.0);
        testFormulaWorks(spreadsheet, "( B1 )", 3.0);
    }

    private static void testFormulaWorks(Spreadsheet spreadsheet, String text, double expectedValue) {
        FormulaCell cell = new FormulaCell();
        cell.setValue(text);
        double actualValue = cell.getNumberValue(spreadsheet);
        if (actualValue != expectedValue) {
            throw new RuntimeException(String.format(
                "Expected %f, got %f", expectedValue, actualValue
            ));
        }
    }
}