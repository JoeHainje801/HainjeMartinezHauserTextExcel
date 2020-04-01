package TextExcel;

import java.util.ArrayList;
import java.util.Objects;
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

    public static double calculate(ArrayList<Object> tokens) {
        double result = 0;
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

    // TODO

    /**
     * A test for formula cell
     * 
     * @param args
     */
    public static void main(String[] args) {
        // FormulaCell cell = new FormulaCell();
        System.out.println("Enter the mathematical expression with correct delimiters");
        Scanner inScanner = new Scanner(System.in);
        // cell.setValue(inScanner.nextLine());
        // cell.setValue("( 1 + 1 )");
        ArrayList<Object> tokens = parseTokens(inScanner.nextLine());
        // assert tokens.size() == 3 : "sizes should be same";
        // assert Objects.equals(tokens.get(0), 1) : "first token should be 1";
        // assert Objects.equals(tokens.get(1), "+");
        // assert Objects.equals(tokens.get(2), 1);
        // System.out.println("SUCCESS");
        System.out.println(tokens);
        System.out.println(calculate(tokens));
        inScanner.close();
    }
}