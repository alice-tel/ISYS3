package game.games.battleship;
import game.framework.GameFramework;

import javax.swing.*;
import java.awt.*;

/**
 * because this is an abstract class, don't forget to implement the parent
 * getGameName and onGridButtonClicked with overrides
 * getGameName is used in the GameOptionsFrame to add a button that opens the corresponding game
 * onGridButtonClicked adds action event listeners to the grid buttons
 **/
public class BattleshipGame extends GameFramework {
    private char currentPlayer;
    private boolean gameActive;

    /**
     * Constructor
     * initializes a 3x3 grid
     * base starting player set to X
     */

    public BattleshipGame() {
        super(16, 8); // generate a 3x3 grid from the parent framework
        currentPlayer = 'X'; // X as the starting player
        gameActive = true; // if this point is reached, game should be active
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Battleships");
        setVisible(true);
    }


    /**
     * set the current game name
     *
     * @return String (game name)
     */
    @Override
    protected String getGameName() {
        return "Battleships";
    }

    /**
     * add action events to all buttons to make game input either X or O depending on current player turn
     *
     * @param row int
     * @param col int
     */

    @Override
    protected void onGridButtonClicked(int row, int col) {
        if (!gameActive) {
            return; // ignore if the game is over or the cell is already filled
        }

        //int winningCondition = checkForWin(currentPlayer); // check for win and get the right line
    }

    private int checkForWin(int playernumber){
    return 0;
    }
}