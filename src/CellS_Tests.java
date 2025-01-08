import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//public class CellS_Tests {

//    @Test
//    public void testIsNumber() {
//        // בדיקה עבור מספרים חיוביים ושליליים
//        assertTrue(new Cell_SCell("123").isNumber("123"));
//        assertTrue(new Cell_SCell("-123").isNumber("-123"));
//
//        // בדיקה עבור מספרים עשרוניים
//        assertTrue(new Cell_SCell("123.45").isNumber("123.45"));
//        assertTrue(new Cell_SCell("-123.45").isNumber("-123.45"));
//
//        // בדיקה עבור מחרוזות שאינן מספרים
//        assertFalse(new Cell_SCell("abc").isNumber("abc"));
//        assertFalse(new Cell_SCell("123abc").isNumber("123abc"));
//        assertFalse(new Cell_SCell("123,45").isNumber("123,45"));
//        assertFalse(new Cell_SCell("1/2").isNumber("1/2"));
//        assertFalse(new Cell_SCell("1+2").isNumber("1+2"));
//
//    }
//
//
//    @Test
//    public void testValidFormulasAndNumbers() {
//        Cell_SCell cell1 = new Cell_SCell("1");
//        assertEquals(Ex2Utils.NUMBER, cell1.getType());
//
//        Cell_SCell cell2 = new Cell_SCell("-3.14");
//        assertEquals(Ex2Utils.NUMBER, cell2.getType());
//
//        Cell_SCell cell3 = new Cell_SCell("=1+2");
//        assertEquals(Ex2Utils.FORM, cell3.getType());
//
//        Cell_SCell cell4 = new Cell_SCell("=A1+2");
//        assertEquals(Ex2Utils.FORM, cell4.getType());
//
//        Cell_SCell cell5 = new Cell_SCell("Hello");
//        assertEquals(Ex2Utils.TEXT, cell5.getType());
//    }
//    @Test
//    public void testEvaluateFormulaStringOutput() {
//        Cell_SCell cell = new Cell_SCell("");
//
//        // ביטויים פשוטים
//        assertEquals("3.0", cell.evaluateFormula("=1+2")); // חיבור
//        assertEquals("-1.0", cell.evaluateFormula("=1-2")); // חיסור
//        assertEquals("6.0", cell.evaluateFormula("=2*3")); // כפל
//        assertEquals("2.0", cell.evaluateFormula("=4/2")); // חילוק
//
//        // ביטויים עם סוגריים
//        assertEquals("9.0", cell.evaluateFormula("=(1+2)*3")); // חישוב לפי סדר פעולות חשבון
//        assertEquals("2.0", cell.evaluateFormula("=(8/(2+2))")); // חישוב מורכב
//
//        // ביטויים מורכבים יותר
//        assertEquals("5.0", cell.evaluateFormula("=1+2*2")); // בדיקת סדר פעולות חשבון
//        assertEquals("7.0", cell.evaluateFormula("=(1+2)*(2+1)/3+4")); // ביטוי מורכב
//
//        // בדיקת שגיאות
//        assertEquals("ERROR", cell.evaluateFormula("=1+")); // ביטוי לא חוקי
//        assertEquals("ERROR", cell.evaluateFormula("=1/0")); // חלוקה באפס
//        assertEquals("ERROR", cell.evaluateFormula("=1+2)")); // סוגריים לא תקינים
//    }
//

//
//    @Test
//        public void testInvalidFormulas() {
//            // תאים עם ערכים לא חוקיים
//            Cell_SCell cell1 = new Cell_SCell("a");
//            assertEquals(Ex2Utils.TEXT, cell1.getType());
//
//            Cell_SCell cell2 = new Cell_SCell("AB");
//            assertEquals(Ex2Utils.TEXT, cell2.getType());
//
//            Cell_SCell cell3 = new Cell_SCell("@2");
//            assertEquals(Ex2Utils.TEXT, cell3.getType());
//
//            Cell_SCell cell4 = new Cell_SCell("2+)");
//            assertEquals(Ex2Utils.ERR_FORM_FORMAT, cell4.getType());
//
//            Cell_SCell cell5 = new Cell_SCell("(3+1*2)-");
//            assertEquals(Ex2Utils.ERR_FORM_FORMAT, cell5.getType());
//        }
//
//        @Test
//        public void testCycleDetection() {
//            // בדיקה של מעגלי הפניה
//            Cell_SCell cell = new Cell_SCell("A0:A0");
//            assertEquals(Ex2Utils.ERR_CYCLE, cell.getType());
//        }
//    }
//
