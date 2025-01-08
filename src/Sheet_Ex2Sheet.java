import java.io.IOException;
// Add your documentation below:
public class Sheet_Ex2Sheet {

private Cell[][] table;

    public Sheet_Ex2Sheet(int x, int y) {
         table = new Cell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                table[i][j] = new Cell() {
                    /**
                     * Return the input text (aka String) this cell was init by (without any computation).
                     *
                     * @return
                     */
                    @Override
                    public String getData() {
                        return "";
                    }

                    /**
                     * Changes the underline string of this cell
                     *
                     * @param s
                     */
                    @Override
                    public void setData(String s) {

                    }

                    /**
                     * Returns the type of this cell {TEXT,NUMBER, FORM, ERR_CYCLE_FORM, ERR_WRONG_FORM}
                     *
                     * @return an int value (as defined in Ex2Utils)
                     */
                    @Override
                    public int getType() {
                        return 0;
                    }

                    /**
                     * Changes the type of this Cell {TEXT,NUMBER, FORM, ERR_CYCLE_FORM, ERR_WRONG_FORM}
                     *
                     * @param t an int type value as defines in Ex2Utils.
                     */
                    @Override
                    public void setType(int t) {

                    }

                    /**
                     * Computes the natural order of this entry (cell) in case of a number or a String =0, else 1+ the max of all dependent cells.
                     *
                     * @return an integer representing the "number of rounds" needed to compute this cell (using an iterative approach)..
                     */
                    @Override
                    public int getOrder() {
                        return 0;
                    }

                    /**
                     * Changes the order of this Cell
                     *
                     * @param t
                     */
                    @Override
                    public void setOrder(int t) {

                    }
                }; // תא ריק
            }
        }
    }

    public Cell get(int x, int y) {
        return table[x][y];
    }

    public void set(int x, int y, Cell c) {
        table[x][y] = c;
    }

    public int width() {
        return table.length;
    }

    public int height() {
        return table[0].length;
    }

    public int xCell(String c) {
        // המרת עמודה באותיות למספר
        return -1; // החזירי ערך דיפולטי זמני
    }

    public int yCell(String c) {
        // המרת שורה ממחרוזת למספר
        return -1; // החזירי ערך דיפולטי זמני
    }

    public String eval(int x, int y) {
        return table[x][y].getData(); // שימוש ב-getData במקום getContent
    }

    public String[][] evalAll() {
        String[][] result = new String[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                result[i][j] = eval(i, j);
            }
        }
        return result;
    }

    public int[][] depth() {
        int[][] result = new int[width()][height()];
        // חישוב עומק עבור כל תא
        return result;
    }
}
