import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	//Symbols for different board elements and game configuration parameters
    static char touched = '#';
    static char sunken = 'X';
    static char water = '0';
    static int size = 5;
    static int players = 1;
    public static void main(String[] args) {
    	
    	//Input handling for game configuration
        Scanner scanner = new Scanner(System.in);
        String mode;
        
        //Ask the user to choose a game mode
        do {
            System.out.println("Which mode do you want to play?(basic/advanced)");
            mode = scanner.nextLine();
            if(mode.equals("advanced")) {
            	
            	//Advanced mode allows customization of board, size and symbols
                do {
                    System.out.println("What size of board?");
                    size = scanner.nextInt();
                } while (size > 11 || size < 4);
                System.out.println("What should be the symbol for touched fields");
                touched = scanner.next().charAt(0);
                System.out.println("What should be the symbol for sunken ships fields");
                sunken = scanner.next().charAt(0);
                System.out.println("What should be the symbol for water fields");
                water = scanner.next().charAt(0);
                do {
                    System.out.println("2 player or 1 player mode(1/2)");
                    players = scanner.nextInt();
                } while (players>2 || players<1);
            }
        } while (!(mode.equals("basic") || mode.equals("advanced")));
        
        //Start the game based on the number of players
        if(players==1){
            OnePlayerGame.play(touched, sunken, water, size);
        } else if(players==2){
            TwoPlayerGame.play(touched, sunken, water, size);
        } else {
            System.out.println("player number invalid");
        }

    }
    
    //Method to initialize the board with water
    public static void initializeBoard(
            int size,
            int [][] board,
            char [][] displayedBoard,
            char water)
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
                displayedBoard[i][j] = water;
            }
        }
    }

    //Method to display the board to the console
    public static void displayBoard(char[][] board) {
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }

    //Place all ships on the board
    public static void placeShips(int[][] board){
        placeShip(board, 3, false);
        placeShip(board, 3, true);
        placeShip(board, 2, false);
        placeShip(board, 2, true);
//        for (int[] row : board) {
//            for (int field : row) {
//                System.out.print(field + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    private static void placeShip(int[][] board, int shipSize, boolean isVertical) {
        ArrayList<Point> validCoordinates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if( checkShipPosition(i, j, board, shipSize, isVertical)) {
                    validCoordinates.add(new Point(i, j));
                }
            }
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(validCoordinates.size());
        Point shipCoordinate = validCoordinates.get(randomIndex);
        for (int i = 0; i < shipSize; i++) {
            int placeX = isVertical ? shipCoordinate.x + i : shipCoordinate.x;
            int placeY = isVertical ? shipCoordinate.y : shipCoordinate.y + i;

            // Mark the cell as occupied (using a value like 1 to represent the ship)
            board[placeX][placeY] = 1;
        }

    }

    private static boolean checkShipPosition(int x, int y, int[][] board, int shipSize, boolean isVertical) {

        int size = board.length;

        if (isVertical) {
            if (x + shipSize > size) {
                return false;
            }
        } else {
            if (y + shipSize > size) {
                return false;
            }
        }

        for (int i = 0; i < shipSize; i++) {
            int checkX = isVertical ? x + i : x;
            int checkY = isVertical ? y : y + i;

            if (board[checkX][checkY] != 0) {
                return false;
            }
        }

        return true;
    }
 // Start the game
    int[][] board1 = new int[size][size];
    int[][] board2 = new int[size][size]; 
    char[][] displayedBoard1 = new char[size][size];
    char[][] displayedBoard2 = new char[size][size];
    initializeBoard(size, board1, displayedBoard1, water);
    initializeBoard(size, board2, displayedBoard2, water);
    playGame(scanner, board1, board2, displayedBoard1, displayedBoard2, players);
}

// Game loop for playing the game
public static void playGame(Scanner scanner, int[][] board1, int[][] board2, char[][] displayedBoard1, char[][] displayedBoard2, int players) {
    boolean gameOn = true; 
    boolean player1Turn = true; 

    while (gameOn) {
        // Determine which player is playing
        char[][] currentDisplay = player1Turn ? displayedBoard2 : displayedBoard1;
        int[][] currentBoard = player1Turn ? board2 : board1;
        System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn:");

        // Display the board
        displayBoard(currentDisplay);

        // Get the player's shot
        System.out.println("Enter coordinates to attack (row and column):");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        // Validate input
        if (row < 0 || row >= size || col < 0 || col >= size) {
            System.out.println("Invalid coordinates. Try again.");
            continue;
        }

        // Process the attack
        if (currentBoard[row][col] == 1) {
            
            System.out.println("Hit!");
            currentDisplay[row][col] = touched;
            currentBoard[row][col] = 0; 
        } else {
            
            System.out.println("Miss!");
            currentDisplay[row][col] = water;
        }

        // Check for victory
        if (isBoardCleared(currentBoard)) {
            System.out.println("Player " + (player1Turn ? "1" : "2") + " wins!");
            gameOn = false; 
        }

        // Switch turns
        player1Turn = !player1Turn;

        // If single-player mode, skip second board Logic
        if (players == 1) {
            gameOn = false;//End game in one-player mode
        }
    }
}

private static boolean isBoardCleared(int[][] board) {
    for (int[] row : board) {
        for (int cell : row) {
            if (cell == 1) return false;

            }
        }
    
    return true;
}
private static void displayBoard(char[][] board) {
    for (char[] row : board) {
        for (char cell : row) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }
}
}

   