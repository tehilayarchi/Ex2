import java.io.*;
// Importing classes for file input/output operations: FileReader, FileWriter, BufferedReader, BufferedWriter, etc.

/**
 * The Ex2Sheet class implements the Sheet interface.
 * This class represents a spreadsheet where each cell can store text, numbers, or formulas.
 * The sheet supports functions for getting, setting, and evaluating cell values, as well as saving and loading the sheet from files.
 */
public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // A 2D array of Cell objects, representing the sheet as a table.

    // Constructor that creates a sheet with a custom size
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        // Initializes the table as a 2D array of SCell objects with dimensions x by y.
        for (int i = 0; i < x; i++) {
            // Loop to iterate over all rows.
            for (int j = 0; j < y; j++) {
                // Loop to iterate over all columns in the current row.
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
                // Creates an empty cell and places it in the correct position in the table.
            }
        }
    }

    public Ex2Sheet() {     // Default constructor: creates a sheet with predefined width and height
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);             // Calls the main constructor with fixed dimensions from Ex2Utils.
    }

    @Override
    public String value(int x, int y) {     // Returns the value of a specific cell in the sheet

        if (isIn(x, y)) {                 // Checks if the coordinates (x, y) are within the sheet boundaries.
            return get(x, y).toString();            // Returns the value of the cell as a String.
        }
        return Ex2Utils.EMPTY_CELL;        // If out of bounds, returns an empty value (EMPTY_CELL).
    }

    @Override
    public Cell get(int x, int y) {       // Returns a Cell object by its coordinates (x, y)

        if (isIn(x, y)) {                             // Checks if the coordinates (x, y) are within the bounds of the table.
            return table[x][y];                       // Returns the cell at the specified position.
        }
        return null;                                        // If out of bounds, returns null.
    }

    // Function that returns a cell by a string of coordinates (e.g. "A1")
    @Override
    public Cell get(String cords) {
        CellEntry index = new CellEntry(cords);
        // Converts the coordinates in text format to numeric indexes.
        if (index.isValid()) {
            // Checks if the coordinates are valid.
            int x = index.getX();
            // Gets the row index.
            int y = index.getY();
            // Gets the column index.
            return get(x, y);
            // Returns the cell at the specified position.
        }
        return null;
        // If the coordinates are invalid, returns null.
    }

    // Function that returns the width (number of rows) of the table
    @Override
    public int width() {
        return table.length;
        // Returns the length of the array (number of rows in the table).
    }

    // Function that returns the height (number of columns) of the table
    @Override
    public int height() {
        return table[0].length;
        // Returns the number of columns in the first row.
    }

    // Function that sets a new value for a specific cell in the sheet
    @Override
    public void set(int x, int y, String s) {
        if (isIn(x, y)) {
            // Checks if the coordinates (x, y) are within the bounds of the table.
            table[x][y] = new SCell(s);
            // Creates a new cell with the given value and stores it in the table.
            table[x][y].setData(s, this);
            // Updates the data of the cell and passes a reference to the sheet in case there are dependencies on other cells.
        }
    }

    // Re-evaluates all cells in the sheet
    @Override
    public void eval() {
        for (int i = 0; i < width(); i++) {
            // Loop through all rows.
            for (int j = 0; j < height(); j++) {
                // Loop through all columns in the current row.
                Cell cell = get(i, j);
                // Gets the current cell.
                if (cell != null) {
                    // If the cell is not empty.
                    cell.setData(cell.getData(), this);
                    // Updates the cell's data.
                }
            }
        }
    }

    // Checks if a set of coordinates is within the sheet's bounds
    @Override
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && xx < width() && yy >= 0 && yy < height();
        // Returns true if the coordinates are within the valid range of the sheet.
    }

    @Override
    public int[][] depth() {                                       // Returns a "depth" table for each cell in the sheet
        int[][] ans = new int[width()][height()];                  // Creates a new table with the size of the sheet.
        for (int i = 0; i < width(); i++) {                        // Loop through all rows.
            for (int j = 0; j < height(); j++) {                    // Loop through all columns in the current row.
                Cell cell = get(i, j);                              // Gets the current cell.
                if (cell != null) {                                 // If the cell is not empty.

                    ans[i][j] = cell.getOrder();                    // Computes the order of processing for the cell and stores it.

                }
            }
        }
        return ans;                                                 // Returns the depth table.
    }

    @Override
    public void load(String fileName) throws IOException {                                 // Loads values into the sheet from a text file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {            // Opens the file for reading.
            for (int i = 0; i < width(); i++) {                                             // Loop through all rows.
                for (int j = 0; j < height(); j++) {                                        // Loop through all columns in the current row.
                    String line = br.readLine();                                            // Reads a line from the file.
                    if (line != null) {                                                    // If there is a line to read.
                        set(i, j, line);                                                    // Updates the value in the cell.
                    }
                }
            }
        }
    }

    // Saves all sheet values to a text file
    @Override
    public void save(String fileName) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Opens the file for writing.
            for (int i = 0; i < width(); i++) {
                // Loop through all rows.
                for (int j = 0; j < height(); j++) {
                    // Loop through all columns in the current row.
                    bw.write(value(i, j));
                    // Writes the value of the cell to the file.
                    bw.newLine();
                    // Adds a new line in the file.
                }
            }
        }
    }

    // Re-evaluates and returns the value of a specific cell
    @Override
    public String eval(int x, int y) {
        if (isIn(x, y)) {
            // If the position is within the sheet bounds.
            return value(x, y);
            // Returns the value.
        }
        return Ex2Utils.EMPTY_CELL;
        // Returns an empty value if out of bounds.
    }
}
