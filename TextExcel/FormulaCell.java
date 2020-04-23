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

    /*
     * public static ArrayList<Double> getValues(ArrayList<Object> tokens) {
     * ArrayList<Double> numbers = new ArrayList<Double>(); for (int i = 0; i <
     * tokens.size(); i++) { if (tokens.get(i) instanceof Double) {
     * numbers.add((Double)tokens.get(i)); } } return numbers; }
     */

    public double basicCalculate(Spreadsheet spreadsheet, ArrayList<Object> tokens) {
        if (tokens.get(0).equals("sum")) {
            return calcSum(spreadsheet, (String) tokens.get(1), (String) tokens.get(3));
        } else if (tokens.get(0).equals("avg")) {
            return calcAvg(spreadsheet, (String) tokens.get(1), (String) tokens.get(3));
        }
        double result = getTokenNumValue(spreadsheet, tokens.get(0));
        boolean expectOperator = true;
        for (int i = 1; i < tokens.size(); i++) {
            if (expectOperator) {
                expectOperator = false;
            } else {
                double rightValue = getTokenNumValue(spreadsheet, tokens.get(i));
                Object operator = tokens.get(i - 1);
                if (operator.equals("+")) {
                    result += rightValue;
                } else if (operator.equals("-")) {
                    result -= rightValue;
                } else if (operator.equals("*")) {
                    result *= rightValue;
                } else if (operator.equals("/")) {
                    result /= rightValue;
                }
                expectOperator = true;
            }

        }
        return result;
    }

    /*
     * if (tokens.size() == 1) { result = getTokenNumValue(spreadsheet,
     * tokens.get(0)); } // loop for usual mathematical operations for (int i = 1; i
     * < tokens.size(); i++) { Object operator = tokens.get(i); double rightValue =
     * getTokenNumValue(spreadsheet, tokens.get(i + 1));
     * 
     * while (i <= 2) { double leftValue = getTokenNumValue(spreadsheet,
     * tokens.get(i - 1)); if (operator.equals("+")) { result = result + leftValue +
     * rightValue; } else if (operator.equals("-")) { result = result + leftValue -
     * rightValue; } else if (operator.equals("/")) { result = result + leftValue /
     * rightValue; } else if (operator.equals("*")) { result = result + leftValue *
     * rightValue; } i++; } while (i > 2 && i < tokens.size()) { if
     * (operator.equals(")")) { break; } else if (operator.equals("+")) { result =
     * result + rightValue; } else if (operator.equals("-")) { result = result -
     * rightValue; } else if (operator.equals("/")) { result = result / rightValue;
     * } else if (operator.equals("*")) { result = result * rightValue; } i++; } }
     * return result;
     */

    private static double getTokenNumValue(Spreadsheet spreadsheet, Object token) {
        if (token instanceof Double) {
            return (Double) token;
        } else {
            return spreadsheet.getNumberValue((String) token);
        }
    }

    public double calcSum(Spreadsheet spreadsheet, String startCell, String endCell) {
        double result = 0;
        ArrayList<Double> values = spreadsheet.getCellRange(startCell, endCell);
        for (int i = 0; i < values.size(); i++) {
            result += values.get(i);
        }
        return result;
    }

    /*
     * private static ArrayList<Double> getNumList(Spreadsheet spreadsheet,
     * ArrayList<Object> cellsInRange) { ArrayList<Double> numValues = new
     * ArrayList<>(); for (int i = 0; i < cellsInRange.size(); i++) { Double value =
     * spreadsheet.getNumberValue((String) cellsInRange.get(i));
     * numValues.add(value); } return numValues; }
     */
    public static double calcAvg(Spreadsheet spreadsheet, String startCell, String endCell) {
        double result = 0;
        ArrayList<Double> values = spreadsheet.getCellRange(startCell, endCell);
        for (int i = 0; i < values.size(); i++) {
            result += values.get(i);
        }
        return result / values.size();
    }

    /*
     * public static double calcAvg(ArrayList<Double> numbers) { double result = 0;
     * for (int j = 0; j < numbers.size(); j++) { result = result + numbers.get(j);
     * } result = result / numbers.size(); return result;
     * 
     * }
     */
    /*
     * public static double calcSum(ArrayList<Double> numbers) { double result = 0;
     * for (int i = 0; i < numbers.size(); i++) { result = result + numbers.get(i);
     * } return result; }
     */

    public Double getNumberValue(Spreadsheet spreadsheet) {
        String formula = this.getValue();
        return basicCalculate(spreadsheet, parseTokens(formula));
    }

    @Override //tells to override the super method
    public String SheetString(Spreadsheet spreadsheet) {
        String sheetStringF = this.getNumberValue(spreadsheet).toString();
        for (int i = CELL_STRING_WIDTH; i > 0; i--) {
            if (sheetStringF.length() < 12) {
                if (i%2 == 0) {
                    sheetStringF += " ";
                } else {
                    sheetStringF = " " + sheetStringF;
                }
            }
        }
        if (sheetStringF.length() > 12) {
            sheetStringF = sheetStringF.substring(0,11) + ">";
        }
        return sheetStringF;
    }

    public static void test() {
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.toNumberCell("A1", "5");
        spreadsheet.toNumberCell("A2", "4");
        spreadsheet.toNumberCell("B1", "2");
        spreadsheet.toNumberCell("B2", "3");
        spreadsheet.toNumberCell("B3", "4");
        spreadsheet.toNumberCell("C1", "2");
        spreadsheet.toFormulaCell("D1", "( sum A1 - B1 )");
        spreadsheet.toFormulaCell("D2", "( sum A2 - B2 )");

        testFormulaWorks(spreadsheet, "( sum A1 - B2 )", 14.0);
        testFormulaWorks(spreadsheet, "( sum A1 - A1 )", 5.0);
        testFormulaWorks(spreadsheet, "( avg A1 - C1 )", 3.0);
        testFormulaWorks(spreadsheet, "( avg B1 - B3 )", 3.0);
        testFormulaWorks(spreadsheet, "( sum B1 - B3 )", 9.0);
        testFormulaWorks(spreadsheet, "( sum A1 - C1 )", 9.0);
        testFormulaWorks(spreadsheet, "( 1 )", 1.0);
        testFormulaWorks(spreadsheet, "( 1 + 2 )", 3.0);
        testFormulaWorks(spreadsheet, "( 1 * 2 + 3 / 5 - 1 )", (0.0));
        testFormulaWorks(spreadsheet, "( B1 )", 2.0);
        testFormulaWorks(spreadsheet, "( B1 + 1 )", 3.0);
        testFormulaWorks(spreadsheet, "( 1 + B1 )", 3.0);
        testFormulaWorks(spreadsheet, "( 1 - B1 )", -1.0);
        testFormulaWorks(spreadsheet, "( 1 * B1 )", 2.0);
        testFormulaWorks(spreadsheet, "( 1 / B1 )", 0.5);
        testFormulaWorks(spreadsheet, "( D1 + D2 )", 14);
        testFormulaWorks(spreadsheet, "( sum D1 - D2 )", 14);

    }

    private static void testFormulaWorks(Spreadsheet spreadsheet, String text, double expectedValue) {
        FormulaCell cell = new FormulaCell();
        cell.setValue(text);
        double actualValue = cell.getNumberValue(spreadsheet);
        if (actualValue != expectedValue) {
            throw new RuntimeException(String.format("Expected %f, got %f", expectedValue, actualValue));
        }
    }
}