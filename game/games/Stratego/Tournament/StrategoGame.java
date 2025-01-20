package game.games.Stratego.Tournament;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import game.framework.GameFramework;
import game.games.Stratego.Pion.*;
import game.games.Stratego.Bordspul.*;

public class StrategoGame extends GameFramework {
    private JPanel shipPanel;
    private JButton readyButton;
    private spelerPionnen pionnenSpeler1;
    private spelerPionnen pionnenSpeler2;
    private String[][] speler1;
    private String[][] speler2;
    private String[][] allePionnen;
    private int size;
    private String currentSelectedPiece;
    private Game currentGame;   
    private int currentPlayer;
    private JPanel pionPanel1;
    private JPanel pionPanel2;
    private boolean Battlephase = false;


    public StrategoGame(int size) {
        super(size, size, 1600, 900, "Here should be the rules of Stratego");


        this.size = size;
        speler1 = new String[size][size];
        speler2 = new String[size][size];
        allePionnen = new String[size][size];
        
        currentPlayer = 1;
        

        initializGrids();
        statusLabel.setText("Stratego");
        setVisible(true); // Show the frame

        currentGame = new Game(size); // Create an instance of the game which we can use.
        
        // System.out.println(currentSelectedPiece);

        System.out.println("PLAYER: " + currentGame.getPionnenspeler1());
        pionPanel1 = currentGame.getPionnenspeler1().getPionPanel(); // JPanel is currently set to only help player1, this needs changing.
        pionPanel2 = currentGame.getPionnenspeler2().getPionPanel(); // JPanel is currently set to only help player1, this needs changing.
        add(pionPanel1, BorderLayout.EAST);
        new Updatebord(speler1);
        readyButton();
    }

    private void switchPlayer() {
        // Switch the player
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        
        new Updatebord(currentPlayer == 1 ? speler1 : speler2);

        // Update the UI and status
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    
        // Update the panel with pieces
        if (!Battlephase) {
            pionPanel1.setVisible(false);
            add(pionPanel2, BorderLayout.EAST);
        }
        // Repaint and revalidate the GUI
        revalidate();
        repaint();
    }

    public void initializGrids() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Default filling of the grid
                speler1[row][col] = "-";
                speler2[row][col] = "-";
                allePionnen[row][col] = "-";
            }
        }
    
        // Define water positions and fill them with 'X'
        setWaterPositions();
    }
    
    private void setWaterPositions() {
        int[][] waterPositions;

        // Check the board size and set the water positions
        if (size == 10) { // For a 10x10 board
            waterPositions = new int[][]{
                {4, 2}, {4, 3}, {5, 2}, {5, 3}, // First water block
                {4, 6}, {4, 7}, {5, 6}, {5, 7}  // Second water block
            };
            
        } else if (size == 8) { // For an 8x8 board
            waterPositions = new int[][]{
                {3, 2}, {4, 2}, // First water block
                {3, 5}, {4, 5}  // Second water block
            };
            
        } else {
            throw new IllegalArgumentException("Invalid board size!");
        } 
    
        for (int[] position : waterPositions) {
            int row = position[0];
            int col = position[1];
            allePionnen[row][col] = "X"; // Mark water with 'X'
            speler1[row][col] = "X";
            speler2[row][col] = "X";
            gridButtons[row][col].setBorderPainted(false); 
        }
    }
    
    
    @Override
    protected String getGameName() {
        return "Stratego";
    }

    
       @Override
       protected void onGridButtonClicked(int row, int col) { // ALL THIS CODE WAS FOR TESTING, REWRITE WHOLE METHOD
           // Fetch the selected Pion object
           Pion selectedPion1;
           spelerPionnen pionnen1;
           Pion selectedPion2;
           spelerPionnen pionnen2;
    
            selectedPion1 = currentGame.getSpeler1SelectedPionObject();
            pionnen1 = currentGame.getPionnenspeler1();
        
            selectedPion2 = currentGame.getSpeler2SelectedPionObject();
            pionnen2 = currentGame.getPionnenspeler2();
        
        if (!Battlephase) {
            if (row > size / 2) { // Check if the grid cell is valid for placing a piece
                if (currentPlayer == 1) {
                    if (selectedPion1 == null) {
                        System.out.println("No piece selected to place!");
                        return;
                    }

                    if (speler1[row][col] != "-") {
                        return;
                    }
                    // Place the piece on the grid
                    speler1[row][col] = "Q" + selectedPion1.getNaam(); // Update logical grid
                    speler1[row][col] += " " + pionnen1.getPionwaarde(); // Update logical grid   
                    speler2[row - size / 2 - 1][col] = "Q"; // Update logical grid
                    Setgridbutton(row, col, Color.BLUE);
                    if (selectedPion1.getNaam().equals("Flag") || selectedPion1.getNaam().equals("Bomb")) {
                        // Only show the name without value
                        setbuttonstext(row, col, selectedPion1.getNaam());
                    } else {
                        // Show name and value
                        setbuttonstext(row, col, selectedPion1.getNaam() + " " + pionnen1.getPionwaarde());
                    }
                    pionnen1.decreasePieceCount(selectedPion1.getNaam());
                    System.out.println("Placed " + selectedPion1.getNaam() + " at [" + row + ", " + col + "]");
                    System.out.println(speler1);
                    
                    if (!currentGame.getPionnenspeler1().availablepieces()) {
                        readyButton.setEnabled(true);
                    }
                }
                else {
                    if (selectedPion2 == null) {
                        System.out.println("No piece selected to place!");
                        return;
                    }

                    if (speler2[row][col] != "-") {
                        return;
                    }
                    // Place the piece on the grid
                    speler2[row][col] = "Z" + selectedPion2.getNaam(); // Update logical grid
                    speler2[row][col] += pionnen2.getPionwaarde(); // Update logical grid
                    speler1[row - size / 2 - 1][col] = "Z";
                    Setgridbutton(row, col, Color.RED);
                    if (selectedPion2.getNaam().equals("Flag") || selectedPion2.getNaam().equals("Bomb")) {
                        // Only show the name without value
                        setbuttonstext(row, col, selectedPion2.getNaam());
                    } else {
                        // Show name and value
                        setbuttonstext(row, col, selectedPion2.getNaam() + " " + pionnen2.getPionwaarde());
                    }
                    pionnen2.decreasePieceCount(selectedPion2.getNaam());
                    System.out.println("Placed " + selectedPion2.getNaam() + " at [" + row + ", " + col + "]");
                    System.out.println(speler2);
                    
                    if (!currentGame.getPionnenspeler2().availablepieces()) {
                        readyButton.setEnabled(true);
                    }
                }
            }
        }
        return;
    }

    public void handleReadyButton() {
        if (currentPlayer == 2) {
            startBattlephase();
        }
        readyButton.setEnabled(false);
        switchPlayer();
    }

    public void standardbord() {

    }

    public void readyButton() {
        readyButton = new JButton("Ready");
        readyButton.setEnabled(false); // Disabled until all ships are placed
        readyButton.setPreferredSize(new Dimension(200, 50));
        // readyButton.addActionListener(e -> goToShootingPhase()); // This is the ready button next to setup
        readyButton.addActionListener(e -> handleReadyButton());
        // currentGame.getPionnenspeler1().addpionpanal(readyButton);
        add(readyButton, BorderLayout.SOUTH);
    }

    public void startBattlephase() {
        Battlephase = true;
        remove(pionPanel1);
        remove(pionPanel2);
        remove(readyButton);
    }
    
    public static void Setgridbutton(int row, int col, Color color) {
        gridButtons[row][col].setBackground(color);
    }

    public static void setBordercolor(int row, int col, Color color) {
        gridButtons[row][col].setBorder(new LineBorder(color, 1));
    }

    public static void setbuttonstext(int row, int col, String text) {
        gridButtons[row][col].setText(text);
    }
}
