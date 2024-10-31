package game.framework;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Gatherer.Integrator.Greedy;

/**
 * renders a base grid by using extend
 * call this in the corresponding class by using super() giving the x by x size desired
 * !!! be sure to add overrides for the getGameName and onGridButtonClicked methods when extending !!!
 */
public abstract class GameFramework extends JFrame {
    protected JButton[][] gridButtons;
    protected JButton[][] gridButtonsPlayerTwo;
    protected int columns;
    protected int rows;
    protected int width;
    protected int height;
    protected JLabel statusLabel;
    private JTabbedPane tabbedPaneL;

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
        
        if (rows + columns == 16) {
            gridButtonsPlayerTwo = new JButton[rows][columns];
            initializeBattleships();  // Roep de methode aan die schepen initialiseert
        }
        else setupUI();
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

        statusLabel = new JLabel("player1", SwingConstants.CENTER);
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
    private void initializeBattleships() {
        setTitle(getGameName());
        setSize(1500, 800);
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
                gridButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                int finalI = i;
                int finalJ = j;
                gridButtons[i][j].addActionListener(e -> onGridButtonClicked(finalI, finalJ));
                gridPanel1.add(gridButtons[i][j]);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridButtonsPlayerTwo[i][j] = new JButton("");
                gridButtonsPlayerTwo[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                int finalI = i;
                int finalJ = j;
                gridButtonsPlayerTwo[i][j].addActionListener(e -> onGridButtonClicked(finalI, finalJ));
                gridPanel2.add(gridButtonsPlayerTwo[i][j]);
            }
        }

        containerPanel.add(gridPanel1);
        containerPanel.add(gridPanel2);
        gamePanel.add(containerPanel, BorderLayout.CENTER);
        tabbedPaneL.addTab("Game", gamePanel);

        // Spelregels tabblad
        JPanel rulesPanel = new JPanel(new BorderLayout());
        JTextArea rulesText = new JTextArea(
            "Spelregels Battleship\n\n" +
            "Setup-fase:\n" +
            "• Spelbord: 8x8 grid voor elke speler.\n" +
            "• Schepen: Elke speler plaatst vier schepen op het bord:\n" +
            "   - 1x Lengte 6\n" +
            "   - 1x Lengte 4\n" +
            "   - 1x Lengte 3\n" +
            "   - 1x Lengte 2\n" +
            "• Plaatsingsregels:\n" +
            "   - Schepen mogen elkaar niet raken, maar mogen wel tegen de rand.\n" +
            "   - Schepen kunnen horizontaal of verticaal worden geplaatst.\n" +
            "   - Klik met de rechter muisknop op 1 van de schepen om de schepen te roteren.\n" +
            "• Klaar-knop: Zodra alle schepen geplaatst zijn, druk je op de 'Klaar'-knop om verder te gaan.\n\n" +
            
            "Spelfase:\n" +
            "• Doel: Om beurten aanvallen uitvoeren en proberen de schepen van de tegenstander tot zinken te brengen.\n" +
            "• Aanvallen:\n" +
            "   - Klik op een vakje van het tegenstanderbord om aan te vallen.\n" +
            "   - Rood betekent dat je een schip hebt geraakt ('Hit').\n" +
            "   - Zwart betekent een misser ('Miss').\n" +
            "• Beurtwisseling:\n" +
            "   - Als je raakt, mag je nog een keer aanvallen.\n" +
            "   - Als je mist, is de andere speler aan de beurt.\n\n" +
            
            "Winconditie:\n" +
            "• Het spel eindigt zodra alle delen van de schepen van een speler zijn geraakt.\n" +
            "• De speler die als laatste nog overgebleven schepen heeft, wint het spel.\n\n" +
            
            "Spelvoorwaarden:\n" +
            "• Beide spelers moeten eerst al hun schepen correct hebben geplaatst.\n" 
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
        statusLabel.setText("Current Player: X");
    }
}
