import java.io.*;
// ייבוא מחלקות לעבודה עם קלט ופלט של קבצים: FileReader, FileWriter, BufferedReader, BufferedWriter וכו'.

// מחלקה Sheet_Ex2Sheet מממשת את הממשק Sheet
public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // מערך דו-ממדי של אובייקטים מסוג Cell, מייצג את הגיליון כטבלה.

    // בנאי שמאפשר ליצור גיליון בגודל מותאם אישית
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        // אתחול הטבלה כמערך דו-ממדי של Cell_SCell בגודל x על y.
        for (int i = 0; i < x; i++) {
            // לולאה לעבור על כל השורות.
            for (int j = 0; j < y; j++) {
                // לולאה לעבור על כל העמודות בשורה הנוכחית.
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
                // יצירת תא ריק והכנסתו למקום המתאים בטבלה.
            }
        }
    }

    public Ex2Sheet() {     // בנאי ברירת מחדל: יוצר גיליון עם גודל מוגדר מראש (רוחב וגובה)
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);             // קריאה לבנאי הראשי עם המידות הקבועות ב-Ex2Utils.

    }

    @Override
    public String value(int x, int y) {     // פונקציה שמחזירה את הערך של תא מסוים בגיליון

        if (isIn(x, y)) {                 // בדיקה אם המיקום (x, y) נמצא בגבולות הגיליון.
            return get(x, y).toString();            // מחזיר את הערך של התא כ-String.

        }
        return Ex2Utils.EMPTY_CELL;        // אם מחוץ לטווח, מחזיר ערך ריק (EMPTY_CELL).

    }

    @Override
    public Cell get(int x, int y) {       // פונקציה שמחזירה אובייקט Cell לפי המיקום (x, y)

        if (isIn(x, y)) {                             // בדיקה אם המיקום (x, y) נמצא בגבולות הטבלה.
            return table[x][y];                       // מחזיר את התא שנמצא במיקום זה.
        }
        return null;                                        // אם מחוץ לטווח, מחזיר null.
    }

    // פונקציה שמחזירה תא לפי מחרוזת של קואורדינטות (למשל "A1")
    @Override
    public Cell get(String cords) {
        CellEntry index = new CellEntry(cords);
        // ממיר את הקואורדינטות בפורמט טקסטואלי לאינדקסים מספריים.
        if (index.isValid()) {
            // בודק אם הקואורדינטות תקינות.
            int x = index.getX();
            // מקבל את אינדקס השורה.
            int y = index.getY();
            // מקבל את אינדקס העמודה.
            return get(x, y);
            // מחזיר את התא שבמיקום.
        }
        return null;
        // אם הקואורדינטות לא תקינות, מחזיר null.
    }

    // פונקציה שמחזירה את הרוחב (מספר השורות) של הטבלה
    @Override
    public int width() {
        return table.length;
        // מחזיר את אורך המערך (מספר השורות בטבלה).
    }

    // פונקציה שמחזירה את הגובה (מספר העמודות) של הטבלה
    @Override
    public int height() {
        return table[0].length;
        // מחזיר את מספר העמודות של השורה הראשונה.
    }

    // פונקציה שמגדירה ערך חדש לתא מסוים בגיליון
    @Override
    public void set(int x, int y, String s) {
        if (isIn(x, y)) {
            // בדיקה אם המיקום (x, y) בגבולות הטבלה.
            table[x][y] = new SCell(s);
            // יוצר תא חדש עם הערך הניתן ושומר אותו בטבלה.
            table[x][y].setData(s, this);
            // מעדכן את הנתונים של התא ומעביר הפניה לגיליון למקרה שיש תלות בתאים אחרים.
        }
    }

    // מבצע הערכה מחדש לכל התאים בגיליון
    @Override
    public void eval() {
        for (int i = 0; i < width(); i++) {
            // לולאה על כל השורות.
            for (int j = 0; j < height(); j++) {
                // לולאה על כל העמודות בשורה הנוכחית.
                Cell cell = get(i, j);
                // מקבל את התא הנוכחי.
                if (cell != null) {
                    // אם התא לא ריק.
                    cell.setData(cell.getData(), this);
                    // מעדכן את הנתונים של התא.
                }
            }
        }
    }

    // בודק אם קואורדינטות מסוימות בתחום הגיליון
    @Override
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && xx < width() && yy >= 0 && yy < height();
        // מחזיר true אם הקואורדינטות הן בטווח התקין.
    }

    @Override
    public int[][] depth() {                                       // מחזיר טבלה של "עומק" עבור כל תא בגיליון
        int[][] ans = new int[width()][height()];                  // יוצר טבלה חדשה בגודל הגיליון.
        for (int i = 0; i < width(); i++) {                        // לולאה על כל השורות.
            for (int j = 0; j < height(); j++) {                    // לולאה על כל העמודות בשורה הנוכחית.
                Cell cell = get(i, j);                              // מקבל את התא הנוכחי.
                if (cell != null) {                                 // אם התא לא ריק.

                    ans[i][j] = cell.getOrder();                    // מחשב את סדר העיבוד של התא ושומר אותו.

                }
            }
        }
        return ans;                                                 // מחזיר את טבלת העומקים.

    }

    @Override
    public void load(String fileName) throws IOException {                                 // טוען ערכים לגיליון מתוך קובץ טקסט
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {            // פותח את הקובץ לקריאה.
            for (int i = 0; i < width(); i++) {                                             // לולאה על כל השורות.
                for (int j = 0; j < height(); j++) {                                        // לולאה על כל העמודות בשורה הנוכחית.
                    String line = br.readLine();                                            // קורא שורה אחת מהקובץ.
                    if (line != null) {                                                    // אם יש שורה לקרוא.
                        set(i, j, line);                                                    // מעדכן את הערך בתא.

                    }
                }
            }
        }
    }

    // שומר את כל ערכי הגיליון לקובץ טקסט
    @Override
    public void save(String fileName) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // פותח את הקובץ לכתיבה.
            for (int i = 0; i < width(); i++) {
                // לולאה על כל השורות.
                for (int j = 0; j < height(); j++) {
                    // לולאה על כל העמודות בשורה הנוכחית.
                    bw.write(value(i, j));
                    // כותב את הערך של התא לקובץ.
                    bw.newLine();
                    // מוסיף שורה חדשה בקובץ.
                }
            }
        }
    }

    // מבצע הערכה מחדש ומחזיר את הערך של תא מסוים
    @Override
    public String eval(int x, int y) {
        if (isIn(x, y)) {
            // אם המיקום בתחום הגיליון.
            return value(x, y);
            // מחזיר את הערך.
        }
        return Ex2Utils.EMPTY_CELL;
        // מחזיר ערך ריק אם מחוץ לטווח.
    }
}
