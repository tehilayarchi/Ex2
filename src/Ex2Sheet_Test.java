import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class Ex2Sheet_Test {

    @Test
    public void testValue() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3); // Create a 3x3 sheet
        sheet.set(0, 0, "5"); // Set a value in cell A1
        sheet.set(1, 1, "Hello"); // Set a value in cell B2

        // Check if the function returns the correct value
        assertEquals("5", sheet.value(0, 0)); // A1 should return "5"
        assertEquals("Hello", sheet.value(1, 1)); // B2 should return "Hello"
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.value(2, 2)); // C3 is not set, should return an empty value
    }

    @Test
    public void testGet() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3); // Create a 3x3 sheet
        sheet.set(0, 0, "10"); // Set a value in cell A1

        // Check if the function returns the correct cell
        assertNotNull(sheet.get(0, 0)); // A1 should not be null
        assertNull(sheet.get(3, 3)); // Out of bounds, should return null
    }

    @Test
    public void testSet() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3); // Create a 3x3 sheet

        // Set a value in cell A1
        sheet.set(0, 0, "100");

        // Check if the value was updated in cell A1
        assertEquals("100", sheet.value(0, 0)); // A1 should return "100"
    }

    @Test
    public void testEval() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3); // Create a 3x3 sheet
        sheet.set(0, 0, "5"); // Set a value in cell A1
        sheet.set(0, 1, "=A1+10"); // Set a formula in cell B1 (A1 + 10)

        // Before calling eval(), B1 should be a formula, not a value
        assertEquals("=A1+10", sheet.value(0, 1));

        // Call eval() to compute the value of B1
        sheet.eval();

        // Check if the value of B1 has been updated based on the formula
        assertEquals("15", sheet.value(0, 1)); // B1 should return 15 (5 + 10)
    }

    @Test
    public void testLoad() throws IOException {
        Ex2Sheet sheet = new Ex2Sheet(3, 3); // Create a 3x3 sheet
        // Create a file with sample values
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("testLoad.txt"))) {
            writer.write("10\n");
            writer.write("20\n");
            writer.write("30\n");
        }

        // Read the data from the file
        sheet.load("testLoad.txt");

        // Check if the values were read correctly
        assertEquals("10", sheet.value(0, 0)); // A1 should return 10
        assertEquals("20", sheet.value(1, 0)); // A2 should return 20
        assertEquals("30", sheet.value(2, 0)); // A3 should return 30
    }

    @Test
    public void testSave() throws IOException {
        Ex2Sheet sheet = new Ex2Sheet(3, 3); // Create a 3x3 sheet
        sheet.set(0, 0, "100");
        sheet.set(1, 0, "200");

        // Save the sheet to a file
        sheet.save("testSave.txt");

        // Read the file to verify the data was saved
        try (BufferedReader reader = new BufferedReader(new FileReader("testSave.txt"))) {
            assertEquals("100", reader.readLine()); // First line should return 100
            assertEquals("200", reader.readLine()); // Second line should return 200
        }
    }
}
