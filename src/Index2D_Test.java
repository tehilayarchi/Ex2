//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class Index2D_Test {
//
//    @Test
//    void testValidIndex() {
//        Index2D_CellEntry index = new Index2D_CellEntry("A1");
//        assertTrue(index.isValid(), "Index 'A1' should be valid");
//    }
//
//    @Test
//    void testInvalidIndex_Null() {
//        Index2D_CellEntry index = new Index2D_CellEntry(null);
//        assertFalse(index.isValid(), "Null index should be invalid");
//    }
//
//    @Test
//    void testInvalidIndex_ShortString() {
//        Index2D_CellEntry index = new Index2D_CellEntry("A");
//        assertFalse(index.isValid(), "Index 'A' should be invalid");
//    }
//
//    @Test
//    void testInvalidIndex_InvalidLetter() {
//        Index2D_CellEntry index = new Index2D_CellEntry("11");
//        assertFalse(index.isValid(), "Index '11' should be invalid");
//    }
//
//    @Test
//    void testInvalidIndex_OutOfRangeNumber() {
//        Index2D_CellEntry index = new Index2D_CellEntry("A100");
//        assertFalse(index.isValid(), "Index 'A100' should be invalid");
//    }
//
//    @Test
//    void testGetX_ValidIndex() {
//        Index2D_CellEntry index = new Index2D_CellEntry("C7");
//        assertEquals(2, index.getX(), "Index 'C7' should return X=2");
//    }
//
//    @Test
//    void testGetY_ValidIndex() {
//        Index2D_CellEntry index = new Index2D_CellEntry("C7");
//        assertEquals(7, index.getY(), "Index 'C7' should return Y=7");
//    }
//
//    @Test
//    void testGetX_InvalidIndex() {
//        Index2D_CellEntry index = new Index2D_CellEntry("C100");
//        IllegalStateException exception = assertThrows(IllegalStateException.class, index::getX);
//        assertEquals("Invalid index format", exception.getMessage());
//    }
//
//    @Test
//    void testGetY_InvalidIndex() {
//        Index2D_CellEntry index = new Index2D_CellEntry("C100");
//        IllegalStateException exception = assertThrows(IllegalStateException.class, index::getY);
//        assertEquals("ERR", exception.getMessage());
//    }
//
//    @Test
//    void testToString() {
//        Index2D_CellEntry index = new Index2D_CellEntry("B5");
//        assertEquals("B5", index.toString(), "toString() should return 'B5'");
//    }
//}
