import java.util.Scanner; // Import the Scanner class for user input

public class Main {
    public static void main(String[] args) {
        Board board = new Board(10, new Location(0, 0));

        System.out.println(board.drawBoard());
        final Scanner scanner = new Scanner(System.in); // Initialize scanner to read user input
        while (true) { //creates an infinite loop for continuous user input
            System.out.print("WASD to move or anything else to quit > ");
            String line = scanner.nextLine(); // reads the users input
            //convert the user input to a direction
            Direction direction = switch (line.toUpperCase()) {
                case "W" -> Direction.UP;
                case  "S" -> Direction.DOWN;
                case "A" -> Direction.LEFT;
                case "D" -> Direction.RIGHT;
                default -> null;

            };
            //if the user input is not valid, exit the game
            if (direction == null) {
                System.out.println("Bye!");
                break;
            }
            if (board.move(direction)) {
                // If the move is successful, redraw the board and show the score
                System.out.println(board.drawBoard());
                System.out.println("Your score is : " + board.getScore());
            } else {
                // If the move is invalid, notify the user
                System.out.println("You cannot move there.");
            }
            if (board.isGameOver()) {
                System.out.println("Game over.");
                break;
            }
        }
    }
}