// Add your documentation below:


public class Index2D_CellEntry implements Index2D {
    private int x, y;
    private int width, height; // נוסיף לשם בדיקת תקינות

    public Index2D_CellEntry(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isValid() {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }
}


