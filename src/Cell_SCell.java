import java.util.ArrayList;
import java.util.List;

public class Cell_SCell implements Cell {
        private String data;
        private int type;

        // בנאי: יוצר תא חדש עם נתונים התחלתיים
    public Cell_SCell(String data) {
            setData(data);
        }

        @Override
        public String getData() {
            return data;
        }

        @Override
        public void setData(String data) {
            this.data = data;
            updateType();
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
            // לא נדרש יישום כרגע
        }

        public void updateType() {
            if (data == null || data.isEmpty()) {      //אם הנתון ריק=
                type = Ex2Utils.TEXT;                 // יהיה טקסט
            } else if (isFormula(data)) {              // אם הנתון הוא מקיים את isFormula כלומר מתחיל ב"="
                String result = evalForm(data);
                if (result.equals("ERROR")) {
                    type = Ex2Utils.ERR_FORM_FORMAT;
                    data = Ex2Utils.ERR_FORM;
                } else {
                    data = result;
                    type = Ex2Utils.NUMBER;
                }
            } else if (isNumber(data)) {
                type = Ex2Utils.NUMBER;
            } else {
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

        public String evalForm(String form) {
            try {
                if (form.startsWith("=")) {                          // אם הנוסחא מתחילה ב"="
                    form = form.substring(1).trim();  //  מוחק את השווה הזה substring(1) ו-trim() מוחק רווחים מיותרים
                }                                                       // עכשיו אין =! יש רק את הנוסחא.
                double result = evalExpr(form);            // עכשיו נשלח את הנוסחא המוכנה שלנו לפונקצייה שמחשבת אותה נחזיר כדאבל!!.
                return String.valueOf(result);                          //
            } catch (Exception e) {                               //אם במהלך ההערכה מתרחשת שגיאה כלשהי
                return "ERROR";                                  // הפונקציה תתפוס את החריגה ותחזיר את המילה "ERROR" במקום תוצאה מספרית.
            }
        }

        public static double evalExpr(String exp) {
            exp = exp.replaceAll("\\s", "");                    //  מוחק לנו את כל הרווחים, ומחליף אותם למחרוזת ריקה=מוחק וקורא לזה exp

            if (exp.startsWith("(") && exp.endsWith(")")) {                      //   בדיקה ראשונה : אם יש סוגריים חיצוניות לביטוי
                return evalExpr(exp.substring(1, exp.length() - 1));              // תתחיל את המחרוזת החדשה מהמקום השני(1) ותסיים באורך המחרוזת -1
            }

            int OpIndex = indOfMainOp(exp);
            if (OpIndex == -1) {
                return Double.parseDouble(exp);
            }

            String left = exp.substring(0, OpIndex);
            String right = exp.substring(OpIndex + 1);

            char operator = exp.charAt(OpIndex);
            double leftValue = evalExpr(left);
            double rightValue = evalExpr(right);

            switch (operator) {
                case '+': return leftValue + rightValue;
                case '-': return leftValue - rightValue;
                case '*': return leftValue * rightValue;
                case '/': return leftValue / rightValue;
                default: throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        }

        public static int indOfMainOp(String expression) {
            int mainOpIndex = -1;
            int lowestPrecedence = Integer.MAX_VALUE;
            int parenthesesLevel = 0;

            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);

                if (c == '(') {
                    parenthesesLevel++;
                } else if (c == ')') {
                    parenthesesLevel--;
                }

                if (parenthesesLevel == 0) {
                    int precedence = getPrecedence(c);
                    if (precedence != -1 && precedence <= lowestPrecedence) {
                        lowestPrecedence = precedence;
                        mainOpIndex = i;
                    }
                }
            }

            return mainOpIndex;
        }

        private static int getPrecedence(char op) {
            return switch (op) {
                case '+', '-' -> 1;
                case '*', '/' -> 2;
                default -> -1;
            };
        }

        private static boolean isFullyParenthesized(String expression) {
            int parenthesesLevel = 0;
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (c == '(') {
                    parenthesesLevel++;
                } else if (c == ')') {
                    parenthesesLevel--;
                    if (parenthesesLevel == 0 && i != expression.length() - 1) {
                        return false;
                    }
                }
            }
            return parenthesesLevel == 0;
        }

        @Override
        public String toString() {
            return data;
        }
    }