/**
 * SCell represents a cell in a spreadsheet that can store text, numbers, or formulas.
 * It handles formula evaluation, data type detection, and references to other cells.
 *
 * Key methods:
 * - setData(String s, Sheet sheet): Updates the cell's content and recalculates its value and type.
 * - getData(): Returns the original content of the cell.
 * - updateTypeAndValue(): Identifies the data type (text, number, or formula) and updates its value.
 * - evalForm(String form, Sheet sheet): Evaluates a formula, replacing cell references with their values.
 * - evalExpr(String exp): Evaluates mathematical expressions, handling nested parentheses and operators.
 */
public class SCell implements Cell {
    private String data; // The calculated value displayed in the cell
    private String originalData; // The original value entered
    private int type;
    private Sheet sheet;

    // Constructor: Creates a new cell with initial data
    public SCell(String data) {
        setData(data);
    }

    @Override
    public String getData() {
        return originalData; // Returns the original value entered
    }

    @Override
    public void setData(String s, Sheet sheet) {
        this.originalData = s; // Saves the original value
        this.sheet = sheet;
        updateTypeAndValue(); // Updates the cell type and calculated result
    }

    @Override
    public void setData(String s) {
        this.originalData = s; // Saves the original value
        updateTypeAndValue(); // Updates the cell type and calculated result
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        this.type = t;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void setOrder(int t) {
    }

    public void updateTypeAndValue() {
        if (originalData == null || originalData.isEmpty()) {
            // Empty cell
            data = "";
            type = Ex2Utils.TEXT;
        } else if (isFormula(originalData)) {
            // Formula detection
            type = Ex2Utils.FORM;

            // Calculate formula value
            if (sheet == null) {
                // No sheet -> error
                data = Ex2Utils.ERR_FORM;
                type = Ex2Utils.ERR_FORM_FORMAT;
            } else {
                String result = evalForm(originalData, sheet); // Formula calculation
                if (result.equals("ERROR") || result.equals(Ex2Utils.ERR_FORM)) {
                    // Formula value error
                    data = Ex2Utils.ERR_FORM;
                    type = Ex2Utils.ERR_FORM_FORMAT;
                } else {
                    // Valid formula result
                    data = result;
                }
            }
        } else if (isNumber(originalData)) {
            // Number detection
            try {
                double number = Double.parseDouble(originalData);
                data = String.valueOf(number);
                type = Ex2Utils.NUMBER;
            } catch (NumberFormatException e) {
                // On error -> text
                data = originalData;
                type = Ex2Utils.TEXT;
            }
        } else {
            // Anything else -> plain text
            data = originalData;
            type = Ex2Utils.TEXT;
        }
    }

    public boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFormula(String str) {
        return str.startsWith("=");
    }

    public static String evalForm(String form, Sheet sheet) {
        try {
            if (form.startsWith("=")) {
                form = form.substring(1).trim();
            }

            // Replace cell references with their values
            form = replaceCellReferences(form, sheet);

            // If there’s ERR_FORM, the function stops and returns the error
            if (form.contains(Ex2Utils.ERR_FORM)) {
                return Ex2Utils.ERR_FORM;
            }

            double result = evalExpr(form);
            return String.valueOf(result);
        } catch (Exception e) {
            return "ERROR";
        }
    }

    private static String replaceCellReferences(String form, Sheet sheet) {
        StringBuilder updatedForm = new StringBuilder();
        String[] tokens = form.split("(?<=[-+*/()])|(?=[-+*/()])"); // חלוקה לפי אופרטורים ותווים
        for (String token : tokens) {
            token = token.trim();
            if (token.matches("[A-Za-z]+\\d+")) { // אם זה כתובת תא
                Cell cell = sheet.get(token);
                if (cell == null || cell.getData().isEmpty()) {
                    // תא ריק או לא קיים -> שגיאה
                    updatedForm.append(Ex2Utils.ERR_FORM);
                } else {
                    updatedForm.append(cell.toString()); // משתמש בערך המחושב
                }
            } else {
                updatedForm.append(token); // תו רגיל
            }
        }
        return updatedForm.toString();
    }


    public static double evalExpr(String exp) {
        exp = exp.replaceAll("\\s", ""); // Removes unnecessary spaces

        while (exp.startsWith("(") && exp.endsWith(")") && isMatchingParentheses(exp)) {
            exp = exp.substring(1, exp.length() - 1);
        }

        if (exp.matches("-?\\d+(\\.\\d+)?")) {
            return Double.parseDouble(exp);
        }

        int openIndex = exp.indexOf('(');
        while (openIndex != -1) {
            int closeIndex = findClosingParenthesis(exp, openIndex);
            String innerExp = exp.substring(openIndex + 1, closeIndex);
            double innerResult = evalExpr(innerExp);

            exp = exp.substring(0, openIndex) + innerResult + exp.substring(closeIndex + 1);

            openIndex = exp.indexOf('(');
        }

        int opIndex = indOfMainOp(exp);
        if (opIndex == -1) {
            return Double.parseDouble(exp);
        }

        String left = exp.substring(0, opIndex);
        String right = exp.substring(opIndex + 1);

        char operator = exp.charAt(opIndex);
        double leftValue = evalExpr(left);
        double rightValue = evalExpr(right);

        return switch (operator) {
            case '+' -> leftValue + rightValue;
            case '-' -> leftValue - rightValue;
            case '*' -> leftValue * rightValue;
            case '/' -> leftValue / rightValue;
            default -> throw new IllegalArgumentException("ERR");
        };
    }

    public static int indOfMainOp(String expression) {
        int mainOpIndex = -1;
        double lowestPrecedence = Double.MAX_VALUE;
        int parenthesesCounter = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '(') {
                parenthesesCounter++;
            } else if (c == ')') {
                parenthesesCounter--;
            }

            if (parenthesesCounter > 0) {
                continue;
            }

            double precedence = getPrecedence(c);

            if (precedence != -1 && precedence <= lowestPrecedence) {
                lowestPrecedence = precedence;
                mainOpIndex = i;
            }
        }

        return mainOpIndex;
    }

    private static int findClosingParenthesis(String exp, int openIndex) {
        int counter = 0;
        for (int i = openIndex; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (c == '(') {
                counter++;
            } else if (c == ')') {
                counter--;
                if (counter == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("ERR");
    }

    private static boolean isMatchingParentheses(String expression) {
        int counter = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') counter++;
            if (c == ')') counter--;
            if (counter < 0) return false;
        }
        return counter == 0;
    }

    private static double getPrecedence(char op) {
        return switch (op) {
            case '+', '-' -> 0;
            case '*', '/' -> 0.5;
            default -> -1;
        };
    }

    @Override
    public String toString() {
        return data;
    }
}
