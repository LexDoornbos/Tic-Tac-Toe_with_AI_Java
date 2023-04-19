package tictactoe;
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

public class Main {

    final static Scanner scanner = new Scanner(System.in);
    static char[][] gameGrid = new char[3][3]; // create empty game grid 3 x 3.
    static char playerSymbol = 'X'; // declare playerSymbol variable and initialize it to 'X'.
    static int countMoves = 0;

    public static void main(String[] args) {

/*        char[][] gameGrid = new char[3][3]; // create empty game grid 3 x 3.
        char playerSymbol = 'X'; // declare playerSymbol variable and initialize it to 'X'.*/

        boolean menuLoop = true;

        String gameState = "RUNNING"; // declare gameState variable and initialize it to "RUNNING".

        emptyGameGrid(gameGrid);

        menuLoop(gameGrid, menuLoop, gameState);
    }

    private static void menuLoop(char[][] gameGrid, boolean menuLoop, String gameState) {
        String action;
        // Main game loop.
        while (menuLoop) {
            // Show menu
            String[] input = showMenu().split(" ");
            String playerX;
            String playerO;

            if (input.length == 1 && input[0].equals("exit")) {
                menuLoop = false;
                System.exit(0);
            }
            if (input.length == 3) {
                action = input[0];
                playerX = input[1];
                playerO = input[2];
            } else {
                System.out.println("Bad parameters!");
                continue;
            }

            if (action.equals("start")) {
                if ((playerX.equals("user") || playerX.equals("easy")) && (playerO.equals("user") || playerO.equals("easy"))) {
                    showGameGrid(gameGrid);
                    String player = playerX;
                    while (gameState.equals("RUNNING")) {
                        makeMove(gameGrid, player, countMoves);
                        countMoves += 1;
                        gameState = checkGameState(gameGrid);
                        player = changeTurn(player, playerX, playerO);
                    }
                    emptyGameGrid(Main.gameGrid);
                    countMoves = 0;
                    gameState = checkGameState(gameGrid);
                } else {
                    System.out.println("Bad parameters!");
                }
            } else {
                System.out.println("Bad parameters!");
            }
        }
        Main.gameGrid = emptyGameGrid(gameGrid);
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


    /**
     * Function to show the menu for a new game or to exit the program.
     * @return the input line
     */
    private static String showMenu() {
        // get input string from user
        System.out.print("Input command: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Function to change the player turn.
     * @param player takes player, flips the turn,
     * @return and returns that character
     */
    private static String changeTurn(String player, String playerX, String playerO) {
        if (Objects.equals(player, playerX)) {
            player = playerO;
        } else {
            player = playerX;
        }
        return player;
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

        // Check all horizontal, vertical and diagonal lines for winners and return string declaring who won.
        for (char[] line: checkLines) {
            if (line[0] == line[1] && line[1] == line[2] && line[0] != ' ') {
                System.out.println(line[0] + " wins");
                return "gameWON"; // if the game has a winner: return "gameWON" as the gameState.
            }
        }

        /*
         * Check if the gameGrid has any empty cells left.
         */
        int emptyCells = 0;
        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                if (gameGrid[i][j] == ' ') {
                    ++emptyCells;
                }
            }
        }
        if (emptyCells == 0){
            System.out.println("Draw");
            return "DRAW";} // if the game has a no winner and no more empty cells: return "DRAW" as the gameState.

        // If both the isFilled loop and gameWON loop do not return anything: return "RUNNING" as the gameState.
        return "RUNNING";
    }

    private static void makeMove(char[][] gameGrid, String player, int countMoves) {
        if (player.equals("user")){userMove(gameGrid, countMoves);}
        if (player.equals("easy")){easyAIMove(gameGrid, countMoves);}
    }

    /**
     * Function for user to make a move.
     * @param gameGrid get gameGrid and add move
     * @param countMoves to determine X or O.
     */
    private static void userMove(char[][] gameGrid, int countMoves) {
        boolean loop = true;
        if (countMoves % 2 == 0) {
            playerSymbol = 'X';
        } else { playerSymbol = 'O';}

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
    private static void easyAIMove(char[][] gameGrid, int countMoves) {
        if (countMoves % 2 == 0) {
            playerSymbol = 'X';
        } else { playerSymbol = 'O';}
        System.out.println("Making move level \"easy\"");
        Random random = new Random();
        int x, y;
        boolean loop = true;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
            if (gameGrid[x][y] == ' ') {
                gameGrid[x][y] = playerSymbol;
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
    private static char[][] emptyGameGrid(char[][] gameGrid) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameGrid[row][col] = ' ';
            }
        }
        return gameGrid;
    }
}
