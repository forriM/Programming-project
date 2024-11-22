import java.util.Scanner;

public class Main {
    static char touched = '#';
    static char sunken = 'X';
    static char water = '0';
    static int size = 5;
    static int players = 1;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String mode = "";
        do {
            System.out.println("Which mode do you want to play?(basic/advanced)");
            mode = scanner.nextLine();
            if(mode.equals("advanced")) {
                System.out.println("What size of board?");
                size = scanner.nextInt();
                while(size>11 || size<4) {
                    System.out.println("What size of board?");
                    size = scanner.nextInt();
                }
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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}