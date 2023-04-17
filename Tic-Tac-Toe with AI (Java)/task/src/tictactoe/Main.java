package tictactoe;
 import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] gameGrid = new char[3][3]; // create empty game grid 3 x 3

        /*
         * function fillGameGrid fills the gameGrid according to user input on start,
         * extrapolates from initial gameGrid whose turn it is,
         * returns current player character.
         */
        char currentPlayer = fillGameGrid(scanner, gameGrid);

        showGameGrid(gameGrid);

        String gameState; // declare gameState variable for while condition.

        do {
            makeMove(scanner, gameGrid, currentPlayer);

            showGameGrid(gameGrid);

            // check winner function
            gameState = checkWinner(gameGrid, currentPlayer);

            currentPlayer = changeTurn(currentPlayer);

            switch (gameState) {
                case "RUNNING":
                    System.out.println("Game not finished");
                    break;
                case "DRAW":
                    System.out.println("Draw");
                    break;
                case "XWINS":
                    System.out.println("X wins");
                    break;
                case "OWINS":
                    System.out.println("O wins");
                    break;
            }

        } while (gameState.equals("running"));
        // function for making moves

    }

    private static char changeTurn(char currentPlayer) {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';}
        else if (currentPlayer == 'O') {
            currentPlayer = 'X';}
        return currentPlayer;
    }

    private static String checkWinner(char[][] gameGrid, char currentPlayer) {

        // horizontal wins
        for (int i = 0; i < 3; i++) {
            char cell1 = gameGrid[i][0];
            char cell2 = gameGrid[i][1];
            char cell3 = gameGrid[i][2];
            if (cell1 == cell2 && cell1 == cell3  && cell1 == currentPlayer) {
                System.out.println(currentPlayer + " wins");
                return currentPlayer + "wins";
            }
        }

        // vertical wins
        for (int i = 0; i < 3; i++) {
            char cell1 = gameGrid[0][i];
            char cell2 = gameGrid[1][i];
            char cell3 = gameGrid[2][i];
            if (cell1 == cell2 && cell1 == cell3 && cell1 == currentPlayer) {
                System.out.println(currentPlayer + " wins");
                return currentPlayer + "wins";
            }
        }

        // diagonal up left to right win
        char cell1 = gameGrid[0][0];
        char cell2 = gameGrid[1][1];
        char cell3 = gameGrid[2][2];
        if (cell1 == cell2 && cell1 == cell3 && cell1 == currentPlayer) {
            System.out.println(currentPlayer + " wins");
            return currentPlayer + "wins";
        }

        // diagonal up right to left win
        char cell4 = gameGrid[2][0];
        char cell5 = gameGrid[1][1];
        char cell6 = gameGrid[0][2];
        if (cell4 == cell5 && cell4 == cell6 && cell4 == currentPlayer) {
            System.out.println(currentPlayer + " wins");
            return currentPlayer + "wins";
        }

        return "RUNNING";
    }



    private static void makeMove(Scanner scanner, char[][] gameGrid, char currentPlayer) {
        boolean loop = true;
        do {
            System.out.print("Enter the coordinates: ");
            String input = scanner.nextLine();

            int row, col;
            try {
                String[] parts = input.split(" ");
                row = Integer.parseInt(parts[0]) - 1;
                col = Integer.parseInt(parts[1]) - 1;


                // Validate that input contains only numeric characters
                if (!input.matches("[0-9]+ [0-9]+")) {
                    System.out.println("You should enter numbers!");
                }
                else if (row < 0 || row > 2 || col < 0 || col > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                }
                else if (gameGrid[row][col] == 'X' || gameGrid[row][col] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                }
                else if (gameGrid[row][col] == ' ') {
                    gameGrid[row][col] = currentPlayer;
                    loop = false;
                } else {
                    System.out.println("Something went wrong!");
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        } while (loop);
    }

    private static void showGameGrid(char[][] gameGrid) {
        System.out.println("---------");
        for (int row = 0; row < 3; row++) {
            System.out.print("| ");
            for (int col = 0; col < 3; col++) {
                System.out.print(gameGrid[row][col] + " ");
//                System.out.println(col);
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

        // if no. empty cells is odd, it's X's turn
        // if no. cells is even, it's O's turn
        char currentPlayer;
        if (countEmptyCells % 2 != 0) {
            currentPlayer = 'X';}
        else {
            currentPlayer = 'O';}
        return currentPlayer;
    }
}
