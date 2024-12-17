package game.framework;

import javax.swing.*;
import java.awt.*;

/**
 * renders a base grid by using extend
 * call this in the corresponding class by using super() giving the x by x size desired
 * !!! be sure to add overrides for the getGameName and onGridButtonClicked methods when extending !!!
 */
public abstract class GameFramework extends JFrame {
    protected static JButton[][] gridButtons;
    protected JButton[][] gridButtonsPlayerTwo;
    protected int columns;
    protected int rows;
    protected int width;
    protected int height;
    protected String rules;
    protected JLabel statusLabel;
    private JTabbedPane tabbedPaneL;

    /**
     * initializes a grid of buttons of x by x size set in the param
     *
     * @param columns int
     * @param rows int
     */
    public GameFramework(int columns, int rows, int width, int height,String rules) {
        this.columns = columns;
        this.rows = rows;
        this.width = width;
        this.height = height;
        this.rules = rules;
        gridButtons = new JButton[rows][columns];
        gridButtonsPlayerTwo = new JButton[rows][columns];
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
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPaneL = new JTabbedPane();

        JPanel gamePanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Current player: X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gamePanel.add(statusLabel, BorderLayout.NORTH);

        JPanel containerPanel = new JPanel(new GridLayout(1, 2));
        JPanel gridPanel1 = new JPanel(new GridLayout(rows, columns));
        JPanel gridPanel2 = new JPanel(new GridLayout(rows, columns));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridButtons[i][j] = new JButton("");
                gridButtons[i][j].setOpaque(true);
                gridButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 30));
                int finalI = i;
                int finalJ = j;
                gridButtons[i][j].addActionListener(e -> onGridButtonClicked(finalI, finalJ));
                gridPanel1.add(gridButtons[i][j]);
                
            }
        }
        containerPanel.add(gridPanel1);
    if( getGameName().contains("Battleships"))
        containerPanel.add(gridPanel2);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridButtonsPlayerTwo[i][j] = new JButton("");
                gridButtonsPlayerTwo[i][j].setOpaque(true);
                gridButtonsPlayerTwo[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                int finalI = i;
                int finalJ = j;
                gridButtonsPlayerTwo[i][j].addActionListener(e -> onGridButtonClicked2(finalI, finalJ));
                gridPanel2.add(gridButtonsPlayerTwo[i][j]);
                
            }
        }
        gamePanel.add(containerPanel, BorderLayout.CENTER);
        tabbedPaneL.addTab("Game", gamePanel);

        // Spelregels tabblad
        JPanel rulesPanel = new JPanel(new BorderLayout());
        JTextArea rulesText = new JTextArea(
            rules
        );
        rulesText.setEditable(false);
        rulesText.setFont(new Font("Arial", Font.PLAIN, 14));
        rulesPanel.add(new JScrollPane(rulesText), BorderLayout.CENTER);
        tabbedPaneL.addTab("Spelregels", rulesPanel);

        add(tabbedPaneL);
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
    /**
 * A secondary handler for grid button clicks.
 * Override this for additional behavior.
 *
 * @param row int
 * @param col int
 */
protected void onGridButtonClicked2(int row, int col) {
}

//    protected abstract void setWindowSize(int width, int height);

    protected void resetGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setBackground(null);
                gridButtonsPlayerTwo[i][j].setText("");
                gridButtonsPlayerTwo[i][j].setBackground(null);
            }
        }
        statusLabel.setText("Current player: X");
    }
}
