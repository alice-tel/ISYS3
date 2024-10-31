package game.framework;

import javax.swing.*;
import java.awt.*;

/**
 * renders a base grid by using extend
 * call this in the corresponding class by using super() giving the x by x size desired
 * !!! be sure to add overrides for the getGameName and onGridButtonClicked methods when extending !!!
 */
public abstract class GameFramework extends JFrame {
    protected JButton[][] gridButtons;
    protected int columns;
    protected int rows;
    protected int width;
    protected int height;
    protected JLabel statusLabel;

    /**
     * initializes a grid of buttons of x by x size set in the param
     *
     * @param columns int
     * @param rows int
     */
    public GameFramework(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        gridButtons = new JButton[rows][columns];
        setupUI();
    }

    /**
     * UI setup method
     * add a label at the top and a grid of buttons set in the constructor
     * currently size is preset, this can be changed if desired
     *
     * @TODO currently players are set to x and o, this might need to be changed for future games
     */
    private void setupUI() {
        setTitle(getGameName());
        setLayout(new BorderLayout());
        setSize(this.width, this.height);
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        statusLabel = new JLabel("Current player: X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH); // set the current player status label at the top

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, columns)); // layout for the buttons on the grid

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridButtons[i][j] = new JButton("");
                gridButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                int finalI = i;
                int finalJ = j;
                gridButtons[i][j].addActionListener(e -> onGridButtonClicked(finalI, finalJ));
                gridPanel.add(gridButtons[i][j]);
            }
        }
        add(gridPanel, BorderLayout.CENTER); // add the button grid to the middle of the grid
    }

    /**
     * abstract method for game name init.
     * @return String
     */
    protected abstract String getGameName();

    /**
     * add event listeners to all buttons in the grid
     * be sure to override this when creating a new game
     *
     * @param row int
     * @param col int
     */
    protected abstract void onGridButtonClicked(int row, int col);

//    protected abstract void setWindowSize(int width, int height);

    protected void resetGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setBackground(null);
            }
        }
        statusLabel.setText("Current Player: X");
    }
}
