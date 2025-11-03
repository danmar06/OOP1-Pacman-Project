public class Location {
    int x; // x-coordinate of the location
    int y; // y-coordinate of the location

    //initializes the location with x and y values
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //method for getting the x-coordinate
    public int getX() {
        return x;
    }
    //method for getting the y-coordinate
    public int getY() {
        return y;
    }

    //method to check if the location is within the bounds of the board
    public boolean withinBounds(int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}
