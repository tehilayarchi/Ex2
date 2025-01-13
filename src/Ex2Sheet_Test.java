


public class Ex2Sheet_Test {
    public static void main(String[] args) {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);

        // בדיקת קביעת ערכים
        sheet.set(0, 0, "Hello");
        sheet.set(1, 1, "World");
        sheet.set(2, 2, "123");

        // בדיקת קריאת ערכים
        System.out.println(sheet.value(0, 0)); // צריך להדפיס "Hello"
        System.out.println(sheet.value(1, 1)); // צריך להדפיס "World"
        System.out.println(sheet.value(2, 2)); // צריך להדפיס "123"
        System.out.println(sheet.value(4, 4)); // צריך להדפיס תא ריק

        // בדיקת גדלים
        System.out.println("Width: " + sheet.width());  // צריך להדפיס 5
        System.out.println("Height: " + sheet.height()); // צריך להדפיס 5
    }
}


