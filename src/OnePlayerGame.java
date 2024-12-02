import java.util.Random;

public class OnePlayerGame {
    static int [][] board;
    static char[][] displayedBoard;

    public static void play(char touched, char sunken, char water, int size) {
        board = new int[size][size];
        displayedBoard = new char[size][size];
        Main.initializeBoard(size, board, displayedBoard, water);
        Main.placeShips(board);
    }
}
