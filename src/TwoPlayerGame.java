public class TwoPlayerGame {
    static int [][] board1;
    static int [][] board2;
    static char[][] displayedBoard1;
    static char[][] displayedBoard2;

    public static void play(char touched, char sunken, char water, int size) {
        board1 = new int[size][size];
        board2 = new int[size][size];
        displayedBoard1 = new char[size][size];
        displayedBoard2 = new char[size][size];
        Main.initializeBoard(size, board1, displayedBoard1, water);
        Main.initializeBoard(size, board2, displayedBoard2, water);
        Main.displayBoard(displayedBoard1);
    }
}
