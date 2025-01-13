import java.awt.*;
import java.io.IOException;

/**
 * ArielU. מבוא למדעי המחשב, תרגיל 2: https://docs.google.com/document/d/1-18T-dj00apE4k1qmpXGOaqttxLn-Kwi/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * אין לשנות את הממשק הזה!!
 * זו אינה מחלקת Junit - מכיוון שהיא בודקת רכיבי GUI, שאותם אין לבדוק באמצעות Junit.
 *
 * הקוד משתמש במחלקת STDDraw:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 * הערה: נוספו שינויים קלים למחלקת STDDraw כדי להתאים את הלוגיקה של תרגיל 2.
 * @author boaz.benmoshe
 *
 */
public class Ex2GUI {

	private static Sheet table; // הנתונים הראשיים (מימוש של ממשק Sheet).
	private static Index2D cord = null; // רשומת גיליון המשמשת את ה-GUI להגדרת ערך תא / נוסחה

	public Ex2GUI() {;}  // בנאי ריק (מיותר).

	/** הפונקציה הראשית להרצת תרגיל 2 */
	public static void main(String[] a) {
		table = new Sheet_Ex2Sheet(Ex2Utils.WIDTH, Ex2Utils.HEIGHT); // יצירת גיליון אלקטרוני חדש
		testSimpleGUI(table); // קריאה לפונקציה המפנה את הגיליון לגרפיקה
	}

	/**
	 * פונקציה זו מריצה את הלולאה הראשית (האינסופית) של ה-GUI.
	 * @param table גיליון אלקטרוני - הערה: מחלקה זו נכתבה כמימוש נאיבי של "סינגלטון" (כלומר, הכל סטטי).
	 */
	public static void testSimpleGUI(Sheet table) {
		// אתחול פרמטרים עבור הגרפיקה
		StdDrawEx2.setCanvasSize(Ex2Utils.WINDOW_WIDTH, Ex2Utils.WINDOW_HEIGHT); // הגדרת גודל חלון
		StdDrawEx2.setScale(0, Ex2Utils.MAX_X); // הגדרת טווח הצירים
		StdDrawEx2.setPenRadius(Ex2Utils.PEN_RADIUS); // קביעת רדיוס עט הציור
		StdDrawEx2.enableDoubleBuffering(); // הפעלת דחיסת גרפיקה (למנוע הבזקים)
		table.eval(); // חישוב וערכת הגיליון
		// לולאה אינסופית (GUI)
		while (true) {
			StdDrawEx2.clear(); // מנקה את ה-GUI (חלון תרגיל 2).
			drawFrame(); // מצייר את הקווים של הגיליון.
			drawCells(); // מצייר את התאים.
			StdDrawEx2.show(); // מציג את התמונה על המסך.
			int xx = StdDrawEx2.getXX(); // מקבל את קואורדינטת ה-x של לחיצה
			int yy = StdDrawEx2.getYY(); // מקבל את קואורדינטת ה-y של לחיצה
			inputCell(xx,yy); // אם הלחיצה בתא תקף, ייפתח חלון קלט לעריכת התא.
			StdDrawEx2.pause(Ex2Utils.WAIT_TIME_MS); // ממתין כמה מילישניות - נניח 30 fps מספיק.
		}
	}

	// פונקציה לשמירה של הגיליון לקובץ
	public static void save(String fileName){
		try {
			table.save(fileName); // שמירה לגיליון
		}
		catch (IOException e) {
			e.printStackTrace(); // טיפול בשגיאה במידה וקרתה בעיה בשמירה
		}
	}

	// פונקציה לטעינה של הגיליון מקובץ
	public static void load(String fileName){
		try {
			table.load(fileName); // טוען את הגיליון מקובץ
		}
		catch (IOException e) {
			e.printStackTrace(); // טיפול בשגיאה במידה וקרתה בעיה בטעינה
		}
	}

	// פונקציה להחזרת צבע בהתאם לסוג התא
	private static Color getColorFromType(int t) {
		Color ans = Color.GRAY; // ברירת מחדל: צבע אפור
		if(t == Ex2Utils.NUMBER) {ans = Color.BLACK;} // אם מדובר במספר, הצבע יהיה שחור
		if(t == Ex2Utils.FORM) {ans = Color.BLUE;} // אם מדובר בנוסחה, הצבע יהיה כחול
		if(t == Ex2Utils.ERR_FORM_FORMAT) {ans = Color.RED;} // אם יש טעות בנוסחה, הצבע יהיה אדום
		if(t == Ex2Utils.ERR_CYCLE_FORM) {ans = StdDrawEx2.BOOK_RED;} // אם יש לולאת נוסחה, הצבע יהיה אדום כהה
		return ans; // מחזיר את הצבע המתאים
	}

	/**
	 * מצייר את הקווים של הגיליון האלקטרוני.
	 */
	private static void drawFrame() {
		StdDrawEx2.setPenColor(StdDrawEx2.BLACK); // קביעת צבע הקו לשחור
		int max_y = table.height(); // גובה הגיליון
		double x_space = Ex2Utils.GUI_X_SPACE, x_start = Ex2Utils.GUI_X_START; // הגדרת רווחים והתחלת קואורדינטת ה-x
		double y_height = Ex2Utils.GUI_Y_TEXT_START; // הגדרת קו הטקסט על ציר ה-y
		for (int y = 0; y < max_y; y++) {
			double xs = y * x_space;
			double xc = x_start + y * x_space;
			StdDrawEx2.line(0, y + 1, Ex2Utils.MAX_X, y + 1); // ציור קווים אופקיים
			StdDrawEx2.line(xs, 0, xs, max_y); // ציור קווים אנכיים
			int yy = max_y - (y + 1);
			StdDrawEx2.text(1, y + y_height, "" + (yy)); // הוספת טקסט לקווי ה-y
			StdDrawEx2.text(xc, max_y + y_height, "" + Ex2Utils.ABC[y]); // הוספת טקסט לקווי ה-x
		}
	}

	/**
	 * מצייר את התוכן של כל תא (שאינו ריק).
	 */
	private static void drawCells() {
		StdDrawEx2.setPenColor(StdDrawEx2.BLACK); // קביעת צבע הטקסט לשחור
		int max_y = table.height(); // גובה הגיליון
		int maxx = table.width(); // רוחב הגיליון
		double x_space = Ex2Utils.GUI_X_SPACE, x_start = Ex2Utils.GUI_X_START; // הגדרת רווחים והתחלת קואורדינטת ה-x
		double y_height = Ex2Utils.GUI_Y_TEXT_START; // הגדרת קו הטקסט על ציר ה-y
		for (int x = 0; x < maxx; x++) {
			double xc = x_start + x * x_space; // חישוב קואורדינטת ה-x עבור כל תא
			for (int y = 0; y < max_y; y++) {
				String w = table.value(x, y); // ערך התא
				Cell cc = table.get(x, y); // קבלת תא הגיליון
				int t = cc.getType(); // קבלת סוג התא (מספר, נוסחה, טעות, וכו')
				StdDrawEx2.setPenColor(getColorFromType(t)); // הגדרת צבע הטקסט לפי סוג התא
				int max = Math.min(Ex2Utils.MAX_CHARS, w.length()); // הגבלת כמות התוים שמוצגים בתא
				w = w.substring(0, max); // חיתוך המילה לגבול המקסימלי
				double yc = max_y - (y + 1 - y_height); // חישוב קואורדינטת ה-y עבור כל תא
				StdDrawEx2.text(xc, yc, w); // ציור הטקסט בתא
			}
		}
	}

	/** קולט תוכן לתוך תא (xx,yy) אם הוא נמצא בטווח של גיליון זה.
	 *
	 * @param xx קואורדינטת x של התא הנדרש.
	 * @param yy קואורדינטת y של התא הנדרש.
	 */
	private static void inputCell(int xx, int yy) {
		if (table.isIn(xx, yy)) { // בודק אם התא נמצא בטווח של הגיליו
			Cell cc = table.get(xx, yy); // מקבל את התא שנמצא בקואורדינטות (xx, yy
			cord = new Index2D_CellEntry(Ex2Utils.ABC[xx] + yy);

			String ww = cord + ": " + cc.toString() + " : "; // יוצר מחרוזת שמציגה את פרטי התא הנוכחי אני הוספתי!!

			StdDrawEx2.text(Ex2Utils.GUI_X_START, Ex2Utils.MAX_X - 1, ww); // מציג את פרטי התא בחלון הגרפי
			StdDrawEx2.show(); // מעדכן את החלון הגרפי
			if (Ex2Utils.Debug) { System.out.println(ww); } // במצב Debug, מציג את המידע על התא בקונסול
			String c = StdDrawEx2.getCell(cord, cc.getData()); // פותח חלון קלט למשתמש, לקבלת תוכן חדש לתא
			String s1 = table.get(xx, yy).getData(); // שומר את התוכן הקיים בתא
			if (c == null) { table.set(xx, yy, s1); } // אם המשתמש לא הזין תוכן חדש, נשארים עם התוכן הקיים
			else {
				table.set(xx, yy, c); // מעדכנים את התא בתוכן החדש
				int[][] calc_d = table.depth(); // מחשבים את העומק של התא (לזיהוי לולאות בנוסחאות)
				if (calc_d[xx][yy] == Ex2Utils.ERR) { table.get(xx, yy).setType(Ex2Utils.ERR_CYCLE_FORM); } // אם נמצאה שגיאה, מעדכנים את סוג התא לשגיאה מתאימה
			}
			table.eval(); // מחשב מחדש את כל הגיליון
			StdDrawEx2.resetXY(); // מאפס את ערכי הקואורדינטות של הלחיצה ב-GUI
		}
	}
}
