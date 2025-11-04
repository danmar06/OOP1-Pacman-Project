import java.util.Random;

public class Board {
    private final char[][] board; // 2D array representing the board layout
    private Location pacman; // current location of pacman
    private final int size; // size of the board (assuming square board)
    private int score; // player's score
    private int level;
    private final Ghost[] ghosts;
    private boolean gameOver;

    // Constructor to initialize the board with a given size and pacman's starting location
    public Board(int size, Location pacmanLocation) {
        this(size, pacmanLocation, (new Random()).nextLong());
    }

    public Board(int size, Location pacmanLocation, long seed) {
        this(size, pacmanLocation, seed, 1);
    }

    // Constructor: creates a new board with a random seed
    public Board(int size, Location pacmanLocation, long seed, int level) {
        // creates a new board using a seed to determine the locations of walls and pellets to allow for reproducible maps
        Random random = new Random(seed); // Random generator for reproducible board layout
        score = 0;
        gameOver = false;
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
        ghosts = new Ghost[level];
        for (int i = 0; i<level; i++) {
            Location ghostLocation = Location.RandomLocation(random, size);
            if (ghostLocation.equals(pacmanLocation) || !this.isEmpty(ghostLocation)) {
                i--;
                continue;
            }
            ghosts[i] = new Ghost(ghostLocation, this);
        }
        this.pacman = pacmanLocation;
        this.board[pacman.getY()][pacman.getX()] = 'P';
    }
    // Method to render the current state of the board as a string
    public String drawBoard() {
        // generate the boards ascii representation
        StringBuilder sb = new StringBuilder();
        char[][] tempBoard = board.clone();
        Location currentLocation = new Location(0, 0);
        for (char[] chars : tempBoard) {
            for (char aChar : chars) {
                boolean end = false;
                for (Ghost ghost : ghosts) {
                    if (currentLocation.equals(ghost.getGhostLocation())) {
                        sb.append("\uD83D\uDC7B");
                        end = true;
                    }
                }
                if (end) {

                    currentLocation = currentLocation.right();
                    continue;
                }
                if (aChar != 'P') {
                    sb.append(aChar); // Append regular characters (walls, pellets, spaces)
                } else {
                    sb.append("🙃"); // I can't store the emoji in the char array since this is technically a string
                }
                currentLocation = currentLocation.right();
            }
            currentLocation = new Location(0, currentLocation.getY() + 1);
            sb.append('\n'); // New line after each row
        }
        return sb.toString(); // Return the complete board as a string
    }

    public Location getPacman() {
        return pacman;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isEmpty(Location location) {
        if (!location.withinBounds(size)) {
            return false;
        }
        return board[location.getY()][location.getX()] != '#';
    }
    // Attempt to move Pacman in the specified direction
    public boolean move(Direction direction) {
        // tries to move pacman in the given direction returns true if successful false if the movement resulted in a collision
        // if moving was successful then ghosts are also moved
        Location newLocation = this.pacman.move(direction);
        // perform some bounds checking and return early if we're out of bounds
        if (!newLocation.withinBounds(size) || board[newLocation.getY()][newLocation.getX()] == '#') return false;
        
        // If new location has a pellet, increase score
        if  (board[newLocation.getY()][newLocation.getX()] == '.') {
            score += 1;
        }
        board[pacman.getY()][pacman.getX()] = ' '; // Clear old pacman position
        pacman = newLocation; // Update pacman's location
        board[pacman.getY()][pacman.getX()] = 'P';
        for (Ghost ghost : ghosts) { // calculate the ghost's moves
            ghost.move();
            if (ghost.getGhostLocation().equals(pacman)) {
                gameOver = true;
            }
        }
        return true;
    }

    public int getScore() { // method to get the current score
        return score;
    }
}
