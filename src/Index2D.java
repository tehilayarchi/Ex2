/**
 * ArielU. Intro2CS, Ex2: https://docs.google.com/document/d/1-18T-dj00apE4k1qmpXGOaqttxLn-Kwi/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * אין לשנות את הממשק הזה!!
 * ממשק זה מייצג אינדקס פשוט של תא דו-ממדי (כמו בגיליון אלקטרוני).
 * האינדקס "c12" מייצג את התא [2][12].
 */
public interface Index2D {
    /**
     *
     * @return ייצוג האינדקס של התא בפורמט של גיליון אלקטרוני (לדוגמה, "B3").
     *
     */
    public String toString();

    /**
     * בודק אם הייצוג המחרוזתי של אינדקס זה תקף "XY",
     * כאשר X הוא אות "A-Z" (או "a-z"), ו-Y הוא מספר שלם בטווח [0-99].
     * @return true אם ורק אם זהו אינדקס דו-ממדי תקף.
     */
    public boolean isValid();

    /**
     *
     * @return the x value (integer) of this
     * @return ערך ה-x (מספר שלם) של אינדקס זה.
     */
    public int getX();
    /**
     * @return ערך ה-y (מספר שלם) של אינדקס זה.
     */
    public int getY();
}
