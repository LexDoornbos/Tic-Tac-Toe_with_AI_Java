package tictactoe;
 import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] gameGrid = new char[3][3]; // create empty game grid 3 x 3

        char currentPlayer = fillGameGrid(scanner, gameGrid);

        System.out.print("Enter the coordinates: ");

        String[] input = scanner.nextLine().split(" ");
        int rowIndex = Integer.parseInt(input[0]) - 1;
        int colIndex = Integer.parseInt(input[1]) - 1;

        if (rowIndex < 0 || rowIndex > 2 || colIndex < 0 || colIndex > 2) {
            System.out.println("Coordinates should be from 1 to 3!");
        }
        else if (gameGrid[rowIndex][colIndex] == 'X' || gameGrid[rowIndex][colIndex] == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
        }
        else if (gameGrid[rowIndex][colIndex] == ' ') {
            gameGrid[rowIndex][colIndex] = currentPlayer;
        } else {
            System.out.println("Something went wrong!");
        }

        showGameGrid(gameGrid);
    }

    private static void showGameGrid(char[][] gameGrid) {
        System.out.println("---------");
        for (int row = 0; row < 3; row++) {
            System.out.print("| ");
            for (int col = 0; col < 3; col++) {
                System.out.print(gameGrid[row][col] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static char fillGameGrid(Scanner scanner, char[][] gameGrid) {
        System.out.print("Enter the cells: ");
        String cells = scanner.nextLine();

        int index = 0;
        int countEmptyCells = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameGrid[row][col] = cells.charAt(index++);
                if (gameGrid[row][col] == '_') {
                    gameGrid[row][col] = ' ';
                    countEmptyCells += 1;
                }
            }
        }

        char currentPlayer;
        if (countEmptyCells % 2 != 0) {
            currentPlayer = 'X';}
        else {
            currentPlayer = 'O';}

        showGameGrid(gameGrid);
        return currentPlayer;
    }
}
