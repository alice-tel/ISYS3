package game.games.Stratego;

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

    public StrategoGame(int size) {
        super(size, size, 1600, 900, "Hier moeten de spelregels van Stratego komen");

        this.size = size;
        speler1 = new String[size][size];
        speler2 = new String[size][size];
        allePionnen = new String[size][size];
        
        initializGrids();
        statusLabel.setText("Stratego");
        setVisible(true); // Show the frame

        new Game(size);

        JPanel pionPanel = spelerPionnen.getPionPanel();

        add(pionPanel,BorderLayout.EAST);
        new Updatebord(speler1);
        
    }


    public void initializGrids() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Standaardvulling van het bord
                speler1[row][col] = "-";
                speler2[row][col] = "-";
                allePionnen[row][col] = "-";
            }
        }
    
        // Definieer de waterposities en vul ze met 'X'
        setWaterPositions();
    }
    
    private void setWaterPositions() {
        int[][] waterPositions;

        // Check de grootte van het bord en stel de waterposities in
        if (size == 10) { // Voor een 10x10 bord
            waterPositions = new int[][]{
                {4, 2}, {4, 3}, {5, 2}, {5, 3}, // Eerste waterblok
                {4, 6}, {4, 7}, {5, 6}, {5, 7}  // Tweede waterblok
            };
        } else if (size == 8) { // Voor een 8x8 bord
            waterPositions = new int[][]{
                {3, 2}, {4, 2}, // Eerste waterblok
                {3, 5}, {4, 5}  // Tweede waterblok
            };
        }else {
            throw new IllegalArgumentException("Ongeldige bordgrootte!");
        } 
    
        for (int[] position : waterPositions) {
            int row = position[0];
            int col = position[1];
            allePionnen[row][col] = "X"; // Markeer water met 'X'
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
protected void onGridButtonClicked(int row, int col) {
    // Retrieve the selected pion from spelerPionnen
    List<String> selectedPion = spelerPionnen.getGeselecteerdePion();                                   //job comment: Dit is zodat we de buttons aan de zijkant kunne gebruiken
    
    if (selectedPion != null && !selectedPion.isEmpty()) {                                                  
        String pionNaam = selectedPion.get(0); // Get the selected pion's name                              //job comment: pak de waarde van spelerpionnen Selectedpion 
        
        // Determine the current player's board
        String[][] currentPlayerBoard = isPlayer1Turn() ? speler1 : speler2;                                //job comment: dit moet veranderdworden met een switchplayer method
        int playerId = isPlayer1Turn() ? 1 : 2;
        
        // Create a Placepion instance and attempt to place the pion
        Placepion placepion = new Placepion(row, col, currentPlayerBoard, pionNaam, playerId);              //job comment: WAAROM HEBBEN WE PLACE PION EEN CLASS GEMAAKT ;-;
        if (placepion.place()) {
            // Successfully placed the pion
            spelerPionnen.decrementPionCount(pionNaam);                                                     //job comment: Hierdoor kan iemand niet 4 flaggen plaatsen, mijn idee was om dus per pion dat de speler plaats de knoppen aantal -1 te doen, en als speler 1 klaar is met plaatsen dat hij dan het aantal weer vult zodat speler 2 t kan plaatsen
            
            // Update UI and switch players if needed
            Setgridbutton(row, col, Color.BLUE); // Example: Set color for placed pion                        //job comment: (kweenie of blue een good idee is, meer een indicator voor mezelf of t werkte)
            setbuttonstext(row, col, pionNaam);
            
            if (isPlayer1Turn() && spelerPionnen.player1Pionnen.isEmpty()) {                                        //job comment: als speler 1 klaar is dan gaan we naar speler 2
                switchToPlayer2();                                                                              //job comment:deze actie mist nog logic, gwn switchplayer doen gok ik
            } else if (!isPlayer1Turn() && spelerPionnen.player2Pionnen.isEmpty()) {
                endPlacementPhase();                                                                                   //job comment: deze actie mist nog logic, ik denk dat ik hier de gridbuttonclick weer aanpas naar aanvallen zoals bij battleships met een boolean
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid placement. Try again.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Select a pion before placing it.");
    }
}

// Helper method to check if it's Player 1's turn
private boolean isPlayer1Turn() {
    // Implement logic to determine the current player
    return true; // Replace with actual turn logic
}

// Switch to Player 2's placement phase
private void switchToPlayer2() {
    statusLabel.setText("Player 2's turn to place pieces.");
    // Additional logic to handle the transition
}

// End the placement phase
private void endPlacementPhase() {
    statusLabel.setText("All pieces placed. Game ready to start.");
    // Additional logic to start the game
}
    
    public static void Setgridbutton(int row, int col, Color color){
        gridButtons[row][col].setBackground(color);
    }
    public static void setBordercolor(int row, int col, Color color){
        gridButtons[row][col].setBorder(new LineBorder(color, 1));
    }
    public static void setbuttonstext(int row, int col, String text){
        gridButtons[row][col].setText(text);
    }
        
}
