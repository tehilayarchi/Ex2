import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellS_Tests {

    @Test
    public void testIsNumber() {
        // בדיקה עבור מספרים חיוביים ושליליים
        assertTrue(new Cell_SCell("123").isNumber("123"));
        assertTrue(new Cell_SCell("-123").isNumber("-123"));

        // בדיקה עבור מספרים עשרוניים
        assertTrue(new Cell_SCell("123.45").isNumber("123.45"));
        assertTrue(new Cell_SCell("-123.45").isNumber("-123.45"));

        // בדיקה עבור מחרוזות שאינן מספרים
        assertFalse(new Cell_SCell("abc").isNumber("abc"));
        assertFalse(new Cell_SCell("123abc").isNumber("123abc"));
        assertFalse(new Cell_SCell("123,45").isNumber("123,45"));
        assertFalse(new Cell_SCell("1/2").isNumber("1/2"));
        assertFalse(new Cell_SCell("1+2").isNumber("1+2"));

    }
    @Test
    public void testSimpleAdditionAndSubtraction() {
        // יצירת גיליון בגודל 3x3 (כדוגמה, תוכל לבחור את הגודל המתאים)
        Sheet_Ex2Sheet sheet = new Sheet_Ex2Sheet(3, 3);

        // יצירת תא עם נוסחה של חיבור
        Cell_SCell cell1 = new Cell_SCell("=3+2");
        cell1.setData("=3+2", sheet);  // העברת הגיליון כפרמטר לפונקציה setData
        assertEquals(5.0, Double.parseDouble(cell1.getData()), 0.0);  // בודק אם התוצאה היא 5 (החיבור בוצע כראוי)

        Cell_SCell cell2 = new Cell_SCell("=10-4"); // יצירת תא עם נוסחה של חיסור
        cell2.setData("=10-4", sheet);  // העברת הגיליון כפרמטר לפונקציה setData

        assertEquals(6.0, Double.parseDouble(cell2.getData()), 0.0);  // בודק אם התוצאה היא 6 (החיסור בוצע כראוי)

    }

    @Test
    public void testOrderOfOperations() {
        // יצירת גיליון עם 3 שורות ו-3 עמודות
        Sheet_Ex2Sheet sheet = new Sheet_Ex2Sheet(3, 3);
        Cell_SCell cell = new Cell_SCell("=3+2*2");        // יצירת תא עם נוסחה
        cell.setData("=3+2*2", sheet);  // אם setData לא מקבלת גיליון, תוודא שהיא תומכת בכך   // הגדרת ערך התא והגיליון
        assertEquals(7.0, Double.parseDouble(cell.getData()), 0.0);  // בודק אם התוצאה היא 7.0 (הכפל בוצע קודם)

    }












    @Test
    public void testValidFormulasAndNumbers() {
        Cell_SCell cell1 = new Cell_SCell("1");
        assertEquals(Ex2Utils.NUMBER, cell1.getType());

        Cell_SCell cell2 = new Cell_SCell("-3.14");
        assertEquals(Ex2Utils.NUMBER, cell2.getType());

        Cell_SCell cell3 = new Cell_SCell("=1+2");
        assertEquals(Ex2Utils.FORM, cell3.getType());

        Cell_SCell cell4 = new Cell_SCell("=A1+2");
        assertEquals(Ex2Utils.FORM, cell4.getType());

        Cell_SCell cell5 = new Cell_SCell("Hello");
        assertEquals(Ex2Utils.TEXT, cell5.getType());
    }



    @Test
        public void testInvalidFormulas() {
            // תאים עם ערכים לא חוקיים
            Cell_SCell cell1 = new Cell_SCell("a");
            assertEquals(Ex2Utils.TEXT, cell1.getType());

            Cell_SCell cell2 = new Cell_SCell("AB");
            assertEquals(Ex2Utils.TEXT, cell2.getType());

            Cell_SCell cell3 = new Cell_SCell("@2");
            assertEquals(Ex2Utils.TEXT, cell3.getType());

            Cell_SCell cell4 = new Cell_SCell("2+)");
            assertEquals(Ex2Utils.ERR_FORM_FORMAT, cell4.getType());

            Cell_SCell cell5 = new Cell_SCell("(3+1*2)-");
            assertEquals(Ex2Utils.ERR_FORM_FORMAT, cell5.getType());
        }

        @Test
        public void testCycleDetection() {
            // בדיקה של מעגלי הפניה
            Cell_SCell cell = new Cell_SCell("A0:A0");
            assertEquals(Ex2Utils.ERR_CYCLE, cell.getType());
        }
    }

