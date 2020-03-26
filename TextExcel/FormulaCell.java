package TextExcel;

import java.util.ArrayList;
import java.util.Objects;

public class FormulaCell extends Cell {
    public FormulaCell() {
    }

    /**
     * Returns an array list of tokens for the formula from setValue
     * @return
     */
    private ArrayList<Object> parseTokens() {
        ArrayList<Object> tokens = new ArrayList<Object>();
        // TODO
        return tokens;
    }

    /**
     * A test for formula cell
     * @param args
     */
    public static void main(String[] args) {
        FormulaCell cell = new FormulaCell();
        cell.setValue("( 1 + 1 )");
        ArrayList<Object> tokens = cell.parseTokens();
        assert tokens.size() == 3 : "sizes should be same";
        assert Objects.equals(tokens.get(0), 1) : "first token should be 1";
        assert Objects.equals(tokens.get(1), "+");
        assert Objects.equals(tokens.get(2), 1);
        System.out.println("SUCCESS");
    }
}