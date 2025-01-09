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
    private String currentSelectedPiece;
    private Game currentGame;

    public StrategoGame(int size) {
        super(size, size, 1600, 900, "Hier moeten de spelregels van Stratego komen");

        this.size = size;
        speler1 = new String[size][size];
        speler2 = new String[size][size];
        allePionnen = new String[size][size];
        
        initializGrids();
        statusLabel.setText("Stratego");
        setVisible(true); // Show the frame

        currentGame = new Game(size); //create an instance of game wich we can use.
        
        // System.out.println(currentSelectedPiece);

        System.out.println("PLAYER: " + currentGame.getPionnenspeler1());
        JPanel pionPanel = currentGame.getPionnenspeler1().getPionPanel(); //JPanel is currently set to only help player1, this needs changing.

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
       protected void onGridButtonClicked(int row, int col) { //ALL THIS CODE WAS FOR TESTING, REWRITE WHOLE METHOD
           // Fetch the selected Pion object
           Pion selectedPion = currentGame.getSpeler1SelectedPionObject();//this wasnt for testing, use this.
           
           if (selectedPion == null) {
               System.out.println("No pion selected to place!");
               return;
           }
       
           // Check if the grid cell is valid for placing a pion
           if (!speler1[row][col].equals("-")) {
               System.out.println("Cannot place here. Cell already occupied or invalid!");
               return;
           }
       
           // Place the pion on the grid
           speler1[row][col] = selectedPion.getNaam(); // Update logical grid
           Setgridbutton(row, col, Color.BLUE); // Update button color
           setbuttonstext(row, col, selectedPion.getNaam()); // Set button text
       
           System.out.println("Placed " + selectedPion.getNaam() + " at [" + row + ", " + col + "]");
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
