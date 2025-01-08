public class Index2D_CellEntry implements Index2D {
    private final String index; // המחרוזת שמייצגת את האינדקס

    public Index2D_CellEntry(String index) {
        this.index = index;
    }

    /**
     * מחזיר את המחרוזת שמייצגת את האינדקס.
     *
     * @return ייצוג האינדקס בפורמט "XY".
     */
    @Override
    public String toString() {
        return index;
    }

    /**
     * בודק אם המחרוזת בפורמט "XY", כאשר:
     * X היא אות "A-Z" או "a-z",
     * Y הוא מספר שלם בטווח [0-99].
     *
     * @return true אם האינדקס תקף, אחרת false.
     */
    @Override
    public boolean isValid() {
        if (index == null || index.length() < 2) {   // אם האינדקס ריק, או אם האורך שלו קטן 2(1)
            return false;
        }

        // בדיקת האות הראשונה
        String letter = index.substring(0, 1).toUpperCase();    //
        boolean isLetterValid = java.util.Arrays.asList(Ex2Utils.ABC).contains(letter);
        if (!isLetterValid) {
            return false;
        }

        // בדיקת המספר שאחרי האות
        try {
            int number = Integer.parseInt(index.substring(1));
            return number >= 0 && number <= 99;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * מחזיר את ערך ה-X לפי האות הראשונה (מיקום ברשימה Ex2Utils.ABC).
     *
     * @return ערך ה-X (מספר שלם).
     */
    @Override
    public int getX() {                                               // האות שלנו
        if (!isValid()) {                                              //בודקים אם זה בפורמט הנכון של התאים
            throw new IllegalStateException("Invalid index format");
        }
        String letter = index.substring(0, 1).toUpperCase();
        return java.util.Arrays.asList(Ex2Utils.ABC).indexOf(letter);
    }

    /**
     * מחזיר את ערך ה-Y לפי המספר שאחרי האות.
     *
     * @return ערך ה-Y (מספר שלם).
     */
    @Override
    public int getY() {
        if (!isValid()) {
            throw new IllegalStateException("ERR");
        }
        return Integer.parseInt(index.substring(1));
    }
}
