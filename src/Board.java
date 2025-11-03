import java.util.Random;

public class Board {
    private final char[][] board; // 2D array representing the board layout
    private Location pacman; // current location of pacman
    private final int size; // size of the board (assuming square board)
    private int score; // player's score


    // Constructor to initialize the board with a given size and pacman's starting location
    public Board(int size, Location pacmanLocation) {
        this(size, pacmanLocation, (new Random()).nextLong());
    }

    // Constructor: creates a new board with a random seed
    public Board(int size, Location pacmanLocation, long seed) {
        // creates a new board using a seed to determine the locations of walls and pellets to allow for reproducible maps
        Random random = new Random(seed); // Random generator for reproducible board layout
        score = 0;
        this.size = size;
        this.board = new char[size][size]; // initialise a 2d array of chars to represent our board
        
        // Populate the board with walls, pellets, or empty spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextInt() % 10 == 0)  { // 1/10 chance a square is a wall
                    this.board[i][j] = '#'; // TODO: replace with actual maze generation function
                } else if (random.nextInt() % 12 == 0) { // 1/12 chance a square is a pellet given its not a wall
                    this.board[i][j] = '.';
                } else {
                    this.board[i][j] = ' ';
                }
            }
        }
        // Place pacman on the board at the specified starting location
        this.pacman = pacmanLocation;
        this.board[pacman.getY()][pacman.getX()] = 'P';
    }
    // Method to render the current state of the board as a string
    public String drawBoard() {
        // generate the boards ascii representation
        StringBuilder sb = new StringBuilder();
        for (char[] chars : board) {
            for (char aChar : chars) {
                if (aChar != 'P') {
                    sb.append(aChar); // Append regular characters (walls, pellets, spaces)
                } else {
                    sb.append("🙃"); // I can't store the emoji in the char array since this is technically a string
                }
            }
            sb.append('\n'); // New line after each row
        }
        return sb.toString(); // Return the complete board as a string
    }

    // Attempt to move Pacman in the specified direction
    public boolean move(Direction direction) {
        // tries to move pacman in the given direction returns true if successful false if the movement resulted in a collision
        Location newLocation = switch (direction) {
            case UP -> new Location(pacman.getX(), pacman.getY() - 1);
            case DOWN -> new Location(pacman.getX(), pacman.getY() + 1);
            case LEFT -> new Location(pacman.getX() - 1, pacman.getY());
            case RIGHT -> new Location(pacman.getX() + 1, pacman.getY());
        };
        // perform some bounds checking and return early if we're out of bounds
        if (!newLocation.withinBounds(size) || board[newLocation.getY()][newLocation.getX()] == '#') return false;
        
        // If new location has a pellet, increase score
        if  (board[newLocation.getY()][newLocation.getX()] == '.') {
            score += 1;
        }
        board[pacman.getY()][pacman.getX()] = ' '; // Clear old pacman position
        pacman = newLocation; // Update pacman's location
        board[pacman.getY()][pacman.getX()] = 'P';
        return true;
    }
    public int getScore() { // method to get the current score
        return score;
    }
}
