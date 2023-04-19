package tictactoe;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        char[][] gameGrid = new char[3][3]; // create empty game grid 3 x 3.
        char playerSymbol = 'X'; // declare playerSymbol variable and initialize it to 'X'.

        boolean menuLoop = true;

        String gameState = "RUNNING"; // declare gameState variable and initialize it to "RUNNING".

        Scanner scanner = new Scanner(System.in); // Create a scanner object to read user input.

        String action;
        String playerX = "";
        String playerO = "";

        emptyGameGrid(gameGrid);

        menuLoop = menuLoop(gameGrid, playerSymbol, menuLoop, gameState, scanner, playerX, playerO);
    }

    private static boolean menuLoop(char[][] gameGrid, char playerSymbol, boolean menuLoop, String gameState, Scanner scanner, String playerX, String playerO) {
        String action;
        // Main game loop.
        while (menuLoop) {
            // Show menu
            String[] input = showMenu().split(" ");

            if (input.length == 1) {
                action = input[0];
            } else if (input.length == 3) {
                action = input[0];
                playerX = input[1];
                playerO = input[2];
            } else {
                System.out.println("Bad parameters!");
                continue;
            }

            if (action.equals("exit")) {
                return false;
//                System.exit(0);
            } else if (action.equals("start")) {
                if ((playerX.equals("user") || playerX.equals("easy")) && (playerO.equals("user") || playerO.equals("easy"))) {
                    showGameGrid(gameGrid);
                    if (playerX.equals("user")) {
                        while (gameState.equals("RUNNING")) {
                            userMove(scanner, gameGrid, playerSymbol);
                            gameState = checkGameState(gameGrid);
                            easyAIMove(gameGrid);
                            gameState = checkGameState(gameGrid);
                        }
                    }
                } else {
                    System.out.println("Bad parameters!");
                }

            } else {
                System.out.println("Bad parameters!");
            }
        }
        return true;
    }

/*            // Ask user to make a move.
            System.out.println("Player " + playerSymbol + " turn.");
            userMove(scanner, gameGrid, playerSymbol);

            // Check the game state after the user's move.
            gameState = checkGameState(gameGrid);

            // If the game is not over, change the player turn and continue the loop.
            if (gameState.equals("RUNNING")) {
                playerSymbol = changeTurn(playerSymbol);
            }
        }

        scanner.close(); // Close the scanner.

        // The game has ended. Show the final game state.
        if (gameState.equals("DRAW")) {
            System.out.println("Draw");
        } else {
            System.out.println("Player " + playerSymbol + " wins");*/

    class Player {

        String playerType;
        char playerSymbol;
    }

    /**
     * Function to show the menu for a new game or to exit the program.
     * @return
     */
    private static String showMenu() {
        // get input string from user
        System.out.print("Input command: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    /**
     * Function to change the player turn.
     * @param playerSymbol takes playerSymbol, flips the character,
     * @return and returns that character
     */
    private static char changeTurn(char playerSymbol) {
        if (playerSymbol == 'X') {
            playerSymbol = 'O';
        } else if (playerSymbol == 'O') {
            playerSymbol = 'X';
        }
        return playerSymbol;
    }

    /**
     * Function to check what state game is in: XWiN, OWiN, DRAW or RUNNING.
     * @param gameGrid the gameGrid in its current state
     * @return returns a string representing the resulting gameState
     */
    private static String checkGameState(char[][] gameGrid) {
        /*
         * Define lines in the gameGrid to check for a win. horizontal, vertical and diagonal.
         */
        char[] hline1 = {gameGrid[0][0], gameGrid[0][1], gameGrid[0][2]};
        char[] hline2 = {gameGrid[1][0], gameGrid[1][1], gameGrid[1][2]};
        char[] hline3 = {gameGrid[2][0], gameGrid[2][1], gameGrid[2][2]};

        char[] vline1 = {gameGrid[0][0], gameGrid[1][0], gameGrid[2][0]};
        char[] vline2 = {gameGrid[0][1], gameGrid[1][1], gameGrid[2][1]};
        char[] vline3 = {gameGrid[0][2], gameGrid[1][2], gameGrid[2][2]};

        char[] diag1 = {gameGrid[0][0], gameGrid[1][1], gameGrid[2][2]};
        char[] diag2 = {gameGrid[2][0], gameGrid[1][1], gameGrid[0][2]};

        // collect them in array checkLines
        char[][] checkLines = {hline1, hline2, hline3, vline1, vline2, vline3, diag1, diag2};

        /*
         * Check if the gameGrid has any empty cells left.
         */
        boolean isFilled = true;
        for (char[] row : gameGrid) {
            for (char cell : row) {
                if (cell != 'X' && cell != 'O') {
                    isFilled = false;
                    break; // Break first loop
                }
            }
            if (!isFilled) {
                break; // Break second loop if non-X or non-O cell has already been found. isFilled = false.
            }
        }
        if (isFilled) {
            return "DRAW"; // If isFilled = true -> Return 'DRAW' for draw
        }

        // Check all horizontal, vertical and diagonal lines for winners and return string declaring who won.
        for (char[] line: checkLines) {
            if (line[0] == line[1] && line[1] == line[2] && line[0] != ' ') {
                System.out.println(line[0] + " wins");
                return "gameWON";
            }
        }

        // If both the isFilled loop and gameWON loop do not return anything: return "Running".
        return "RUNNING";
    }

    /**
     * Function for user to make a move.
     * @param scanner to read user's input
     * @param gameGrid get gameGrid and add move
     * @param playerSymbol to know which symbol to insert
     */
    private static void userMove(Scanner scanner, char[][] gameGrid, char playerSymbol) {
        boolean loop = true;
        do {
            System.out.print("Enter the coordinates: ");
            String input = scanner.nextLine();

            // Validate that input contains only numeric characters
            try {
                int x, y;
                String[] parts = input.split(" ");
                x = Integer.parseInt(parts[0]) - 1;
                y = Integer.parseInt(parts[1]) - 1;

                if (x < 0 || x > 2 || y < 0 || y > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (gameGrid[x][y] == 'X' || gameGrid[x][y] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else if (gameGrid[x][y] == ' ') {
                    gameGrid[x][y] = playerSymbol;
                    showGameGrid(gameGrid);
                    loop = false;
                } else {
                    System.out.println("Something went wrong!");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        } while (loop);
    }

    /**
     * Function for AI to make an easy move.
     * @param gameGrid get gameGrid to make move on
     */
    private static void easyAIMove(char[][] gameGrid) {
        System.out.println("Making move level \"easy\"");
        Random random = new Random();
        int x, y;
        boolean loop = true;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
            if (gameGrid[x][y] == ' ') {
                gameGrid[x][y] = 'O';
                showGameGrid(gameGrid);
                loop = false;
            }
        } while (loop);
    }

    /**
     * Function to display gameGrid.
     * @param gameGrid 2 dimensional array, representing grid of 3 x 3 cells
     */
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

    /**
     * Function to create gameGrid filled with empty spaces.
     * @param gameGrid 2 dimensional array, representing grid of 3 x 3 cells
     */
    private static void emptyGameGrid(char[][] gameGrid) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameGrid[row][col] = ' ';
            }
        }
    }
}
