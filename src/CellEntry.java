/**
 * The CellEntry class represents an index for a cell in a spreadsheet-like system.
 * It implements the Index2D interface and provides functionality to validate,
 * retrieve, and manipulate the row and column indices for a cell.
 */
public class CellEntry implements Index2D {
    private final String index; // The string representing the index

    /**
     * Constructor to initialize the CellEntry with the provided index.
     *
     * @param index The index of the cell in "XY" format.
     */
    public CellEntry(String index) {
        this.index = index;
    }

    /**
     * Returns the string representing the index.
     *
     * @return The index in "XY" format.
     */
    @Override
    public String toString() {
        return index;
    }

    /**
     * Validates the index format:
     * X is a letter (A-Z or a-z),
     * Y is a number in the range [0-99].
     *
     * @return true if the index is valid, false otherwise.
     */
    @Override
    public boolean isValid() {
        if (index == null || index.length() < 2) {   // If the index is empty or less than 2 characters
            return false;
        }

        // Check the first letter
        String letter = index.substring(0, 1).toUpperCase();
        boolean isLetterValid = java.util.Arrays.asList(Ex2Utils.ABC).contains(letter);
        if (!isLetterValid) {
            return false;
        }

        // Check the number following the letter
        try {
            int number = Integer.parseInt(index.substring(1));
            return number >= 0 && number <= 99;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the X value based on the first letter (position in the Ex2Utils.ABC list).
     *
     * @return The X value (an integer).
     */
    @Override
    public int getX() {                                               // The letter part
        if (!isValid()) {                                              // Check if the format is valid
            throw new IllegalStateException("Invalid index format");
        }
        String letter = index.substring(0, 1).toUpperCase();
        return java.util.Arrays.asList(Ex2Utils.ABC).indexOf(letter);
    }

    /**
     * Returns the Y value based on the number following the letter.
     *
     * @return The Y value (an integer).
     */
    @Override
    public int getY() {
        if (!isValid()) {
            throw new IllegalStateException("ERR");
        }
        return Integer.parseInt(index.substring(1));
    }

    /**
     * Validates the format of a cell reference.
     * The valid format is one or more letters (uppercase or lowercase) followed by a number.
     *
     * @param ref The reference string to be validated.
     * @return true if the reference is valid, false otherwise.
     */
    public static boolean isValidCellReference(String ref) {
        // The valid format: letters (uppercase or lowercase) followed by a number
        return ref != null && ref.matches("[A-Za-z]+\\d+");
    }
}
