/**
 * Represents a spreadsheet cell that can store text, numbers, or formulas.
 * Handles formula evaluation, data type detection, and referencing other cells.
 *
 * Methods:
 * - setData(String s, Sheet sheet): Updates cell content and recalculates its type and value.
 * - getData(): Returns the original input of the cell.
 * - updateTypeAndValue(): Determines cell type (text, number, or formula) and updates its value.
 * - evalForm(String form, Sheet sheet): Evaluates a formula using a sheet for cell references.
 * - evalExpr(String exp): Evaluates mathematical expressions with nested parentheses and operators.
 */

public class SCell implements Cell {
    private String data; // הערך המחושב המוצג בתא
    private String originalData; // הערך המקורי שהוזן
    private int type;
    private Sheet sheet;

    // בנאי: יוצר תא חדש עם נתונים התחלתיים
    public SCell(String data) {
        setData(data);
    }

    @Override
    public String getData() {
        return originalData; // מחזיר את הערך המקורי שהוזן
    }


    @Override
    public void setData(String s, Sheet sheet) {
        this.originalData = s; // שמירת הערך המקורי
        this.sheet = sheet;
        updateTypeAndValue(); // עדכון סוג התא והתוצאה המחושבת
    }
    @Override
    public void setData(String s) {
        this.originalData = s; // שמירת הערך המקורי
        updateTypeAndValue(); // עדכון סוג התא והתוצאה המחושבת
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
        return 0; // לא נדרש יישום
    }

    @Override
    public void setOrder(int t) {
        // לא נדרש יישום
    }
    public void updateTypeAndValue() {
        if (originalData == null || originalData.isEmpty()) {
            // תא ריק
            data = "";
            type = Ex2Utils.TEXT;
        } else if (isFormula(originalData)) {
            // זיהוי נוסחה
            type = Ex2Utils.FORM;

            // חישוב ערך הנוסחה
            if (sheet == null) {
                // אין גיליון -> שגיאה
                data = Ex2Utils.ERR_FORM;
                type = Ex2Utils.ERR_FORM_FORMAT;
            } else {
                String result = evalForm(originalData, sheet); // חישוב הנוסחה
                if (result.equals("ERROR") || result.equals(Ex2Utils.ERR_FORM)) {
                    // שגיאה בערך הנוסחה
                    data = Ex2Utils.ERR_FORM;
                    type = Ex2Utils.ERR_FORM_FORMAT;
                } else {
                    // תוצאה תקינה של נוסחה
                    data = result;
                }
            }
        } else if (isNumber(originalData)) {
            // זיהוי מספר
            try {
                double number = Double.parseDouble(originalData);
                data = String.valueOf(number);
                type = Ex2Utils.NUMBER;
            } catch (NumberFormatException e) {
                // במקרה של שגיאה -> טקסט
                data = originalData;
                type = Ex2Utils.TEXT;
            }
        } else {
            // כל דבר אחר -> טקסט רגיל
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

            // החלפת כתובות תאים בערכים שלהם
            form = replaceCellReferences(form, sheet);

            // אם יש ERR_FORM, אז הפונקציה תעצר ותשיב את השגיאה
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
        String[] tokens = form.split("(?<=[-+*/()])|(?=[-+*/()])"); // פיצול לפי אופרטורים
        for (String token : tokens) {
            token = token.trim();
            if (token.matches("[A-Za-z]+\\d+")) { // זיהוי כתובת תא
                Cell cell = sheet.get(token);
                if (cell != null) {
                    // אם התא קיים, נבדוק אם הוא ריק או לא
                    String cellValue = cell.getData().isEmpty() ? Ex2Utils.ERR_FORM : cell.getData();
                    updatedForm.append(cellValue);
                } else {
                    updatedForm.append("0"); // תא ריק
                }
            } else {
                updatedForm.append(token); // טוקן רגיל
            }
        }
        return updatedForm.toString();
    }



    public static double evalExpr(String exp) {
        exp = exp.replaceAll("\\s", ""); // מסיר רווחים מיותרים

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
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
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
        throw new IllegalArgumentException("Unmatched parentheses in expression: " + exp);
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
        return data; // מחזיר את התוצאה המחושבת המוצגת בתא
    }


}
