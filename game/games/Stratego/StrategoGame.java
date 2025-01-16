package game.games.Stratego;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    private String selectedpiece = null;
    private int originalplacey = 0;
    private int originalplacex = 0;
    private String Red = "Red";
    private String Blue = "Blue";
    private String Unoccupied = "-";
    private String Water = "Water";
    private boolean player1HasMovablePieces = true;
    private boolean player2HasMovablePieces = true;


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
        pionPanel1 = currentGame.getPionnenspeler1().getPionPanel(); 
        pionPanel2 = currentGame.getPionnenspeler2().getPionPanel(); 
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
                speler1[row][col] = Unoccupied;
                speler2[row][col] = Unoccupied;
                allePionnen[row][col] = Unoccupied;
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
            allePionnen[row][col] = Water; // Mark water with 'X'
            speler1[row][col] = Water;
            speler2[row][col] = Water;
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

                    if (speler1[row][col] != Unoccupied) {
                        return;
                    }
                    // Place the piece on the grid
                    speler1[row][col] = Blue + " "+ selectedPion1.getNaam(); // Update logical grid
                    speler1[row][col] += " " + pionnen1.getPionwaarde(); // Update logical grid   
                    allePionnen[row][col] = Blue + " " + selectedPion1.getNaam(); // Update logical grid
                    allePionnen[row][col] += " " + pionnen1.getPionwaarde(); // Update logical grid   
                    speler2[size -row -1][col] = Blue; // Update logical grid
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

                    if (speler2[row][col] != Unoccupied) {
                        return;
                    }
                    // Place the piece on the grid
                    speler2[row][col] = Red + " " + selectedPion2.getNaam(); // Update logical grid
                    speler2[row][col] += " " +pionnen2.getPionwaarde(); // Update logical grid
                    allePionnen[size -row -1][col] = Red + " " + selectedPion2.getNaam(); // Update logical grid
                    allePionnen[size -row -1][col] += " " + pionnen2.getPionwaarde(); // Update logical grid   
                    speler1[size -row -1][col] = Red;
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
        if(Battlephase){
            boolean	attacker;
            boolean	defender;
            String[][] CurrentGrid = null;
            String[][] OtherGrid = null;
            String currentpiece = null;
            String Opponentpiece = null;

            if(currentPlayer == 1){
                CurrentGrid = speler1;
                OtherGrid = speler2;
                currentpiece = Blue;
                Opponentpiece = Red;
                if (!player1HasMovablePieces){
                    switchPlayer();
                }
            }
            if(currentPlayer == 2){
                CurrentGrid = speler2; 
                OtherGrid = speler1;
                currentpiece = Red;
                Opponentpiece = Blue;
                if (!player2HasMovablePieces){
                    switchPlayer();
                }
            }
            if (CurrentGrid[row][col] == Water){
                return;
            }
                if(selectedpiece != null && selectedpiece.startsWith(currentpiece)){
                    if(isValidMove(originalplacey, originalplacex, row , col ,selectedpiece)){
                if(CurrentGrid[row][col] == Unoccupied){
                    CurrentGrid[row][col] = selectedpiece;
                    OtherGrid[size -row -1][col] = currentpiece;
                    CurrentGrid[originalplacey][originalplacex] = Unoccupied ;
                    OtherGrid[size -originalplacey - 1][originalplacex] = Unoccupied ;
                    }
                if(CurrentGrid[row][col] == Opponentpiece){
                    System.out.println(OtherGrid[size -row -1][col]);
                    Duel duel = new Duel(selectedpiece,OtherGrid[size -row -1][col]);
                    attacker = duel.attackerwin();
                    defender =  duel.defenderwin();
                    if(!attacker && !defender){
                    OtherGrid[row][col] = Unoccupied;
                    CurrentGrid[size -row -1][col] = Unoccupied;
                    OtherGrid[originalplacey][originalplacex] = Unoccupied ;
                    CurrentGrid[size -originalplacey - 1][originalplacex] = Unoccupied ;
                    if(Checkforwin(currentPlayer)){
                        Battlephase = false;
                        return;
                    }
                }
                    else if(attacker){
                        CurrentGrid[row][col] = Unoccupied;
                        OtherGrid[size -row -1][col] = selectedpiece;
                        CurrentGrid[originalplacey][originalplacex] = Unoccupied ;
                        OtherGrid[size -originalplacey - 1][originalplacex] = selectedpiece;
                        if(Checkforwin(currentPlayer)){
                            Battlephase = false;
                            return;
                        }
                    }
                    else if(defender){
                        OtherGrid[row][col] = selectedpiece;
                        CurrentGrid[size -row -1][col] = Unoccupied;
                        OtherGrid[originalplacey][originalplacex] = selectedpiece ;
                        CurrentGrid[size -originalplacey - 1][originalplacex] = Unoccupied;
                    }
                    
                
                }
                switchPlayer();
            }
            
        }
        selectedpiece = CurrentGrid[row][col];
        originalplacey = row;
        originalplacex = col;
        System.out.println(selectedpiece);
    }
    
        return;
    }
    public boolean Checkforwin(int currentPlayer){
        int flagcount = 0;
        player1HasMovablePieces = false;
        player2HasMovablePieces = false;
        if(currentPlayer == 1){
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if(speler2[row][col].contains("Flag")){
                        flagcount++;
                    }
                    if (!speler1[row][col].contains("Flag") && !speler1[row][col].contains("Bomb") && !speler1[row][col].equals(Unoccupied)) {
                        player1HasMovablePieces = true;
                    }
                    if (!speler2[row][col].contains("Flag") && !speler2[row][col].contains("Bomb") && !speler2[row][col].equals(Unoccupied)) {
                        player2HasMovablePieces = true;
                    }
                }
            }
            if(flagcount == 0){
                statusLabel.setText("Player " + currentPlayer + " has won the game");
                return true;
            }
        }
        if(currentPlayer == 2){
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if(speler1[row][col].contains("Flag")){
                        flagcount++;
                    }
                    if (!speler2[row][col].contains("Flag") && !speler2[row][col].contains("Bomb") && !speler2[row][col].equals(Unoccupied)) {
                        player2HasMovablePieces = true;
                    }
                    if (!speler1[row][col].contains("Flag") && !speler1[row][col].contains("Bomb") && !speler1[row][col].equals(Unoccupied)) {
                        player1HasMovablePieces = true;
                    }
                }
            }
            if(flagcount == 0){
                statusLabel.setText("Player " + currentPlayer + " has won the game");
                return true;
            }
           
        }
        if (!player1HasMovablePieces && !player2HasMovablePieces) {
            statusLabel.setText("The game is a tie!");
            return true;
        }
        return false;
    }

    public boolean isValidMove(int originalPlaceY, int originalPlaceX, int row, int col, String selectedPiece) {
        int move = 1;
    
        // Speciale bewegingen voor bepaalde stukken
        if (selectedPiece.contains("Flag") || selectedPiece.contains("Bomb")) {
            move = 0; // Deze stukken kunnen niet bewegen
        }
        if (selectedPiece.contains("Scout")) {
            move = size; // "Scout" kan meerdere vakken bewegen
        }
        ArrayList<int[]> validMoves = new ArrayList<>();
        for(int i = 0; i < move; i++){
        validMoves.add(new int[] {originalPlaceY + i, originalPlaceX}); // Naar beneden
        validMoves.add(new int[] {originalPlaceY - i, originalPlaceX}); // Naar boven
        validMoves.add(new int[] {originalPlaceY, originalPlaceX + i}); // Naar rechts
        validMoves.add(new int[] {originalPlaceY, originalPlaceX - i}); // Naar links
        }
        // Controleer of de doelpositie in de lijst van geldige zetten zit
        for (int[] validMove : validMoves) {
            if (validMove[0] == row && validMove[1] == col) {
                return true; // Zet is geldig
            }
        }
    
        return false; // Zet is niet geldig
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
