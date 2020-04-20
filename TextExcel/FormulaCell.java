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

    public Object evaluate() {
        ArrayList<Object> tokens = parseTokens(getValue());
        // TODO: Do the math.
        // double result = doTheMath(tokens);
        return 0;
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
        // TODO: Add conditions for cell references
        assert false;
        return null;
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

    public static double basicCalculate(ArrayList<Object> tokens) {
        double result = 0;
        if (tokens.size() == 1) {
            result = (Double)tokens.get(0);
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

    public double getCalculatedValue() {
        String formula = this.getValue();
        return basicCalculate(parseTokens(formula));
    }

    public static void test() {
        testFormulaWorks("( 1 )", 1.0);
        testFormulaWorks("( 1 + 2 )", 3.0);
    }

    private static void testFormulaWorks(String text, double expectedValue) {
        FormulaCell cell = new FormulaCell();
        cell.setValue(text);
        double actualValue = cell.getCalculatedValue();
        if (actualValue != expectedValue) {
            throw new RuntimeException(String.format(
                "Expected %f, got %f", expectedValue, actualValue
            ));
        }
    }
}