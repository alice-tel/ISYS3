package game.games.tictactoe;
import game.framework.GameFramework;

import javax.swing.*;
import java.awt.*;

public class TicTacToeCOMGame extends GameFramework {
    private char currentPlayer;
    private boolean gameActive;

    /**
     * Constructor
     * initializes a 3x3 grid
     * base starting player set to X
     */
    public TicTacToeCOMGame() {
        super(3,3,600,650,""); // generate a 3x3 grid from the parent framework
        currentPlayer = 'X'; // X as the starting player
        gameActive = true; // if this point is reached, the game should be active
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic-Tac-Toe vs COM");
        setVisible(true);
    }

    /**
     * set the current game name
     * @return String (game name)
     */
    @Override
    protected String getGameName() { return "Tic-Tac-Toe"; }

    /**
     * add action events to all buttons to make game input either X or O depending on current player turn
     *
     * @param row int
     * @param col int
     */
    @Override
    protected void onGridButtonClicked(int row, int col) {
        if (!gameActive || !gridButtons[row][col].getText().isEmpty()) {
            return; // ignore if game is over or the cell is already filled
        }

        gridButtons[row][col].setText(String.valueOf(currentPlayer));
        int winningCondition = checkForWin(currentPlayer);

        if (winningCondition != -1) {
            //JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            highlightWinningCondition(winningCondition, row, col);
            statusLabel.setText("Player " + currentPlayer + " wins!");
            gameActive = false; // winner, game over
        } else if (isGridFull()) {
            //JOptionPane.showMessageDialog(this, "It's a tie!");
            statusLabel.setText("It's a tie!");
            gameActive = false; // draw, game over
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // switch players
            statusLabel.setText("Current player: " +  currentPlayer);
            if (currentPlayer == 'O') {
                computerMove(); // trigger computer move
            }
        }
    }



    /**
     * Computer makes a move using the Minimax algorithm
     */
    private void computerMove() {
        if (!gameActive) return; // only continue of the game is still active

        // make it feel like the computer isn't actually instant to give a more natural feel
        Timer timer = new Timer(1000, e -> {
            int[] bestMove = findBestMove();
            // check if the best move is valid in the first place
            if (bestMove[0] == -1 && bestMove[1] == -1) {
                statusLabel.setText("No valid moves for computer, game over.");
                return;
            }

            gridButtons[bestMove[0]][bestMove[1]].setText(String.valueOf(currentPlayer));


            int winningCondition = checkForWin(currentPlayer);
            if (winningCondition != -1) {
                highlightWinningCondition(winningCondition, bestMove[0], bestMove[1]);
                statusLabel.setText("Player " + currentPlayer + " wins!");
                gameActive = false;
            } else if (isGridFull()) {
                statusLabel.setText("It's a tie!");
                gameActive = false;
            } else {
                // Switch to human player
                currentPlayer = 'X';
                statusLabel.setText("Current player: " + currentPlayer);
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    /**
     *  finds the best move for the com using Minimax
     *
     * @return int[] (row, col) of the best move
     */
    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        // list of preferred moves, edit as needed
        int[][] preferredMoves = {{1,1},{0,0},{0,2},{2,0},{2,2}};

        // eval all possible moves
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gridButtons[i][j].getText().isEmpty()) { // check if spot is available
                    gridButtons[i][j].setText("O"); // COM move
                    int moveScore = minimax(0, false); // call minimax
                    gridButtons[i][j].setText(""); // undo the move

                    if (moveScore > bestScore || (moveScore == bestScore && isPreferrdMove(i,j,preferredMoves))) {
                        bestScore = moveScore;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    /**
     * method to check if move is in list of preferred moves
     *
     * @param row int row
     * @param col int column
     * @param preferredMoves list of preferred moves
     * @return Boolean T if move is preferred
     */
    private boolean isPreferrdMove(int row, int col, int[][] preferredMoves) {
        for (int[] move : preferredMoves) {
            if (move[0] == row && move[1] == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * minimax algorithm to determine the best move
     *
     * @param depth int (depth of the recursion
     * @param isMaximizingplayer boolean
     * @return int (score of best move)
     */
    private int minimax(int depth, boolean isMaximizingplayer) {
        int winningCondition = checkForWin(isMaximizingplayer ? 'O' : 'X');
        if (winningCondition != -1) {
            return isMaximizingplayer ? 10 - depth : -10 + depth;
        }
        if (isGridFull()) {
            return 0; // tie
        }

        if (isMaximizingplayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (gridButtons[i][j].getText().isEmpty()) {
                        gridButtons[i][j].setText("O");
                        int score = minimax(depth + 1, false);
                        gridButtons[i][j].setText(""); // undo move
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (gridButtons[i][j].getText().isEmpty()) {
                        gridButtons[i][j].setText("X");
                        int score = minimax(depth + 1, true);
                        gridButtons[i][j].setText(""); // undo move
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    /**
     * highlights the corresponding line during a win
     *
     * @param condition int (number for type of winning condition)
     * @param row int
     * @param col int
     */
    private void highlightWinningCondition(int condition, int row, int col) {
        if (condition == 0) { // row win
            for (int j = 0; j < rows; j++) {
                gridButtons[row][j].setForeground(Color.GREEN);
            }
        } else if (condition == 1) { // column win
            for (int i = 0; i < columns; i++) {
                gridButtons[i][col].setForeground(Color.GREEN);
            }
        } else if (condition == 2) { // diagonal win (top left to bottom right)
            for (int i = 0; i < rows; i++) {
                gridButtons[i][i].setForeground(Color.GREEN);
            }
        } else if (condition == 3) {
            for (int i = 0; i < rows; i++) {
                gridButtons[i][columns - 1 - i].setForeground(Color.GREEN);
            }
        }
    }

    /**
     * logic to check for winning condition
     * checks buttons for value to determine the win condition
     * default state of -1 for no win
     *
     * @param player char (current player)
     * @return int (int for type of winning condition)
     */
    private int checkForWin(char player) {
        // Check rows for a win
        for (int i = 0; i < rows; i++) {
            if (!gridButtons[i][0].getText().isEmpty() &&
                    !gridButtons[i][1].getText().isEmpty() &&
                    !gridButtons[i][2].getText().isEmpty() &&
                    gridButtons[i][0].getText().charAt(0) == player &&
                    gridButtons[i][1].getText().charAt(0) == player &&
                    gridButtons[i][2].getText().charAt(0) == player) {
                return 0; // Return the winning row index
            }
        }

        // Check columns for a win
        for (int i = 0; i < columns; i++) {
            if (!gridButtons[0][i].getText().isEmpty() &&
                    !gridButtons[1][i].getText().isEmpty() &&
                    !gridButtons[2][i].getText().isEmpty() &&
                    gridButtons[0][i].getText().charAt(0) == player &&
                    gridButtons[1][i].getText().charAt(0) == player &&
                    gridButtons[2][i].getText().charAt(0) == player) {
                return 1; // Indicate column win
            }
        }

        // Check diagonals for a win
        if (!gridButtons[0][0].getText().isEmpty() &&
                !gridButtons[1][1].getText().isEmpty() &&
                !gridButtons[2][2].getText().isEmpty() &&
                gridButtons[0][0].getText().charAt(0) == player &&
                gridButtons[1][1].getText().charAt(0) == player &&
                gridButtons[2][2].getText().charAt(0) == player) {
            return 2; // Indicate diagonal win from top-left to bottom-right
        }
        if (!gridButtons[0][2].getText().isEmpty() &&
                !gridButtons[1][1].getText().isEmpty() &&
                !gridButtons[2][0].getText().isEmpty() &&
                gridButtons[0][2].getText().charAt(0) == player &&
                gridButtons[1][1].getText().charAt(0) == player &&
                gridButtons[2][0].getText().charAt(0) == player) {
            return 3; // Indicate diagonal win from top-right to bottom-left
        }

        return -1; // No win found

    }

    /**
     * checks if the grid has been fully used
     *
     * @return bool (empty cells present or not)
     */
    private boolean isGridFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gridButtons[i][j].getText().isEmpty()) {
                    return false; // empty cell remaining
                }
            }
        }
        return true; // no more empty cells
    }
}
