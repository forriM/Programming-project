import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int [][] board = new int[0][];
        char [][] displayedBoard = new char[0][];
        char touched = '#';
        char sunken = 'X';
        char water = '0';
        int size = 5;


        Scanner scanner = new Scanner(System.in);
        System.out.println("Which mode do you want to play?(basic/advanced)");
        String mode = scanner.nextLine();
        while (mode.equals("basic") || mode.equals("advanced")) {
            System.out.println("Which mode do you want to play?(basic/advanced)");
            mode = scanner.nextLine();
            if(mode.equals("advanced")) {
                System.out.println("What size of board?");
                size = scanner.nextInt();
                while(size<11 && size>4) {
                    System.out.println("What size of board?");
                    size = scanner.nextInt();
                }
                System.out.println("What should be the symbol for untouched fields");
                touched = scanner.next().charAt(0);
                System.out.println("What should be the symbol for sunken ships fields");
                sunken = scanner.next().charAt(0);
                System.out.println("What should be the symbol for water fields");
                water = scanner.next().charAt(0);
                initializeBoards(size, board, displayedBoard, water);

            }
        }
        initializeBoards(size, board, displayedBoard, water);

    }

    public static void initializeBoards(
            int size,
            int [][] board,
            char[][] displayedBoard,
            char waterSimbol)
    {

    }
}