package game.games.tictactoe;
import game.framework.GameFramework;

import javax.swing.*;
import java.awt.*;

/**
 * because this is an abstract class, don't forget to implement the parent
 * getGameName and onGridButtonClicked with overrides
 * getGameName is used in the GameOptionsFrame to add a button that opens the corresponding game
 * onGridButtonClicked adds action event listeners to the grid buttons
 **/
public class TicTacToeGame extends GameFramework {
    private char currentPlayer;
    private boolean gameActive;

    /**
     * Constructor
     * initializes a 3x3 grid
     * base starting player set to X
     */
    public TicTacToeGame() {
        super(3); // generate a 3x3 grid from the parent framework
        currentPlayer = 'X'; // X as the starting player
        gameActive = true; // if this point is reached, game should be active
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic-Tac-Toe");
        setVisible(true);
    }

    /**
     * set the current game name
     *
     * @return String (game name)
     */
    @Override
    protected String getGameName() {
        return "Tic-Tac-Toe";
    }

    /**
     * add action events to all buttons to make game input either X or O depending on current player turn
     *
     * @param row int
     * @param col int
     */
    @Override
    protected void onGridButtonClicked(int row, int col) {
        if (!gameActive || !gridButtons[row][col].getText().isEmpty()) {
            return; // ignore if the game is over or the cell is already filled
        }

        gridButtons[row][col].setText(String.valueOf(currentPlayer)); // mark grid
        int winningCondition = checkForWin(currentPlayer); // check for win and get the right line
        if (winningCondition != -1) {
            //JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            hightlightWinningCondition(winningCondition, row, col);
            statusLabel.setText("Player " + currentPlayer + " wins!");
            gameActive = false; // winner, game over
        } else if (isGridFull()) {
            //JOptionPane.showMessageDialog(this, "It's a tie!");
            statusLabel.setText("It's a tie!");
            gameActive = false; // draw, game over
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // switch players
            statusLabel.setText("Current player: " + currentPlayer);
        }
    }

    /**
     * hightlights the corresponding line during a win
     *
     * @param condition int (number for type of winning condition)
     * @param row int
     * @param col int
     */
    private void hightlightWinningCondition(int condition, int row, int col) {
        if (condition == 0) { // row win
            for (int j = 0; j < gridSize; j++) {
                gridButtons[row][j].setForeground(Color.GREEN);
            }
        } else if (condition == 1) { // column win
            for (int i = 0; i < gridSize; i++) {
                gridButtons[i][col].setForeground(Color.GREEN);
            }
        } else if (condition == 2) { // diagonal win (top left to bottom right)
            for (int i = 0; i < gridSize; i++) {
                gridButtons[i][i].setForeground(Color.GREEN);
            }
        } else if (condition == 3) {
            for (int i = 0; i < gridSize; i++) {
                gridButtons[i][gridSize - 1 - i].setForeground(Color.GREEN);
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
        for (int i = 0; i < gridSize; i++) {
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
        for (int i = 0; i < gridSize; i++) {
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
                !gridButtons[2][2].getText().isEmpty() &&
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
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gridButtons[i][j].getText().isEmpty()) {
                    return false; // empty cell remaining
                }
            }
        }
        return true; // no more empty cells
    }
}
