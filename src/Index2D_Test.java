import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellEntry_Test {

    @Test
    void testValidIndex() {
        CellEntry index = new CellEntry("A1");
        // Ensure that a valid index (e.g., "A1") is recognized as valid
        assertTrue(index.isValid(), "Index 'A1' should be valid");
    }

    @Test
    void testInvalidIndex_Null() {
        CellEntry index = new CellEntry(null);
        // Ensure that a null index is considered invalid
        assertFalse(index.isValid(), "Null index should be invalid");
    }

    @Test
    void testInvalidIndex_ShortString() {
        CellEntry index = new CellEntry("A");
        // Ensure that a short string index (e.g., "A") is considered invalid
        assertFalse(index.isValid(), "Index 'A' should be invalid");
    }

    @Test
    void testInvalidIndex_InvalidLetter() {
        CellEntry index = new CellEntry("11");
        // Ensure that a string without a letter (e.g., "11") is considered invalid
        assertFalse(index.isValid(), "Index '11' should be invalid");
    }

    @Test
    void testInvalidIndex_OutOfRangeNumber() {
        CellEntry index = new CellEntry("A100");
        // Ensure that an index with a number out of range (e.g., "A100") is considered invalid
        assertFalse(index.isValid(), "Index 'A100' should be invalid");
    }

    @Test
    void testGetX_ValidIndex() {
        CellEntry index = new CellEntry("C7");
        // Ensure that the X coordinate is correctly parsed for a valid index (e.g., "C7")
        assertEquals(2, index.getX(), "Index 'C7' should return X=2");
    }

    @Test
    void testGetY_ValidIndex() {
        CellEntry index = new CellEntry("C7");
        // Ensure that the Y coordinate is correctly parsed for a valid index (e.g., "C7")
        assertEquals(7, index.getY(), "Index 'C7' should return Y=7");
    }

    @Test
    void testGetX_InvalidIndex() {
        CellEntry index = new CellEntry("C100");
        // Ensure that an invalid index (e.g., "C100") throws an exception for X coordinate
        IllegalStateException exception = assertThrows(IllegalStateException.class, index::getX);
        assertEquals("Invalid index format", exception.getMessage());
    }

    @Test
    void testGetY_InvalidIndex() {
        CellEntry index = new CellEntry("C100");
        // Ensure that an invalid index (e.g., "C100") throws an exception for Y coordinate
        IllegalStateException exception = assertThrows(IllegalStateException.class, index::getY);
        assertEquals("ERR", exception.getMessage());
    }

    @Test
    void testToString() {
        CellEntry index = new CellEntry("B5");
        // Ensure that the toString method correctly returns the string representation of the index
        assertEquals("B5", index.toString(), "toString() should return 'B5'");
    }
}
