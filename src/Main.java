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
            if (mode.equals("advanced")) {

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
                } while (players > 2 || players < 1);
            }
        } while (!(mode.equals("basic") || mode.equals("advanced")));


        int[][] board1 = new int[size][size];
        int[][] board2 = new int[size][size];
        char[][] displayedBoard1 = new char[size][size];
        char[][] displayedBoard2 = new char[size][size];
        initializeBoard(size, board1, displayedBoard1);
        initializeBoard(size, board2, displayedBoard2);
        placeShips(board1);
        placeShips(board2);
        playGame(scanner, board1, board2, displayedBoard1, displayedBoard2, players);

    }

    //Method to initialize the board with water
    public static void initializeBoard(
            int size,
            int[][] board,
            char[][] displayedBoard) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
                displayedBoard[i][j] = ' ';
            }
        }
    }

    //Method to display the board to the console
    public static void displayBoard(char[][] board, int sunkenShips) {
        int shotCount = 0;
        // Print the column headers (A, B, C, ...)
        System.out.print("   "); // Padding for row numbers
        for (int col = 0; col < board[0].length; col++) {
            System.out.print((char) ('A' + col) + " ");
        }
        System.out.println();

        // Print the board with row numbers
        for (int row = 0; row < board.length; row++) {
            System.out.print(row + " "); // Row number
            for (int col = 0; col < board[row].length; col++) {
                System.out.print("|" + board[row][col]);
                if(board[row][col] != ' ') shotCount++;
            }
            System.out.println("|"); // Close the row
        }
        System.out.println("Shots fired: " + shotCount);
        System.out.println("Ships sunk: " + sunkenShips);
    }

    //Place all ships on the board
    public static void placeShips(int[][] board) {
        placeShip(board, 3, false, 2);
        placeShip(board, 3, true, 3);
        placeShip(board, 2, false, 4);
        placeShip(board, 2, true, 5);
        for (int[] row : board) {
            for (int field : row) {
                System.out.print(field + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void placeShip(int[][] board, int shipSize, boolean isVertical, int shipId) {
        ArrayList<Point> validCoordinates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (checkShipPosition(i, j, board, shipSize, isVertical)) {
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

            // Assign a unique value to the ship
            board[placeX][placeY] = shipId;
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


    // Game loop for playing the game
    public static void playGame(Scanner scanner, int[][] board1, int[][] board2, char[][] displayedBoard1, char[][] displayedBoard2, int players) {
        boolean gameOn = true;
        boolean player1Turn = true;
        int sunkShips1 = 0;
        int sunkShips2 = 0;

        while (gameOn) {
            // Determine which player is playing
            char[][] currentDisplay = player1Turn ? displayedBoard2 : displayedBoard1;
            int[][] currentBoard = player1Turn ? board2 : board1;

            System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn:");

            // Display the board
            displayBoard(currentDisplay, player1Turn ? sunkShips1 : sunkShips2);

            // Get the player's shot
            System.out.println("Insert the coordinates:");
            String coordinates = scanner.nextLine();
            coordinates = coordinates.replace("(", "").replace(")", "");
            String[] parts = coordinates.split(",");
            int row;
            int col;
            if (parts.length == 2) {
                try {
                    row = Integer.parseInt(parts[0].trim());
                    col = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("The coordinates are not valid.");
                    continue;
                }
            } else {
                System.out.println("The coordinates are not valid");
                continue;
            }


            // Validate input
            if (row < 0 || row >= size || col < 0 || col >= size) {
                System.out.println("Invalid coordinates. Try again.");
                continue;
            }

            // Process the attack
            if (currentBoard[row][col] > 1) {
                int shipId = currentBoard[row][col]; // Get the ship's unique ID
                currentDisplay[row][col] = touched; // Mark as touched


                // Check if the ship is sunk
                if (isShipSunk(currentBoard, currentDisplay, shipId, touched, sunken)) {
                    if(player1Turn) sunkShips1++;
                    else sunkShips2++;
                }
            } else {
                currentDisplay[row][col] = water;
            }


            // Check for victory
            if (sunkShips1 == 4 || sunkShips2 == 4) {
                displayBoard(currentDisplay, player1Turn ? sunkShips1 : sunkShips2);
                System.out.println("The game is over! ");
                gameOn = false;
            }

            // Switch turns
            player1Turn = players <= 1 || !player1Turn;


        }
    }

    private static boolean isShipSunk(int[][] board, char[][] displayBoard, int shipId, char touchedSymbol, char sunkenSymbol) {
        boolean isSunk = true;

        // First, check if all parts of the ship with `shipId` are hit
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == shipId && displayBoard[i][j] != touchedSymbol ) {
                    isSunk = false;

                }
            }
        }

        // If the ship is sunk, update all its cells on the display board to the `sunkenSymbol`
        if (isSunk) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (displayBoard[i][j] == touchedSymbol && board[i][j] == shipId) {
                        displayBoard[i][j] = sunkenSymbol; // Update the sunk ship's cells
                    }
                }
            }
        }

        return isSunk;
    }

}

   