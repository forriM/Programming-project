import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static char touched = '#';
    static char sunken = 'X';
    static char water = '0';
    static int size = 5;
    static int players = 1;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String mode;
        do {
            System.out.println("Which mode do you want to play?(basic/advanced)");
            mode = scanner.nextLine();
            if(mode.equals("advanced")) {
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

        if(players==1){
            OnePlayerGame.play(touched, sunken, water, size);
        } else if(players==2){
            TwoPlayerGame.play(touched, sunken, water, size);
        } else {
            System.out.println("player number invalid");
        }

    }

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

    public static void displayBoard(char[][] board) {
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }

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



}