//
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellEntry_Test {

    @Test
    void testValidIndex() {
        CellEntry index = new CellEntry("A1");
        assertTrue(index.isValid(), "Index 'A1' should be valid");
    }

    @Test
    void testInvalidIndex_Null() {
        CellEntry index = new CellEntry(null);
        assertFalse(index.isValid(), "Null index should be invalid");
    }

    @Test
    void testInvalidIndex_ShortString() {
        CellEntry index = new CellEntry("A");
        assertFalse(index.isValid(), "Index 'A' should be invalid");
    }

    @Test
    void testInvalidIndex_InvalidLetter() {
        CellEntry index = new CellEntry("11");
        assertFalse(index.isValid(), "Index '11' should be invalid");
    }

    @Test
    void testInvalidIndex_OutOfRangeNumber() {
        CellEntry index = new CellEntry("A100");
        assertFalse(index.isValid(), "Index 'A100' should be invalid");
    }

    @Test
    void testGetX_ValidIndex() {
        CellEntry index = new CellEntry("C7");
        assertEquals(2, index.getX(), "Index 'C7' should return X=2");
    }

    @Test
    void testGetY_ValidIndex() {
        CellEntry index = new CellEntry("C7");
        assertEquals(7, index.getY(), "Index 'C7' should return Y=7");
    }

    @Test
    void testGetX_InvalidIndex() {
        CellEntry index = new CellEntry("C100");
        IllegalStateException exception = assertThrows(IllegalStateException.class, index::getX);
        assertEquals("Invalid index format", exception.getMessage());
    }

    @Test
    void testGetY_InvalidIndex() {
        CellEntry index = new CellEntry("C100");
        IllegalStateException exception = assertThrows(IllegalStateException.class, index::getY);
        assertEquals("ERR", exception.getMessage());
    }

    @Test
    void testToString() {
        CellEntry index = new CellEntry("B5");
        assertEquals("B5", index.toString(), "toString() should return 'B5'");
    }
}
