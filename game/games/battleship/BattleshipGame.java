package game.games.battleship;

import game.framework.GameFramework;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


/**
 * BattleshipGame class extending GameFramework.
 */
public class BattleshipGame extends GameFramework {
    private static final int GRID_ROWS = 8;
    private static final int GRID_COLS = 8;


    private char[][] currentPlayerDefendGrid; // Defend grid for the current player
    private char[][] currentPlayerAttackGrid; // Attack grid for the current player

    private char[][] player1DefendGrid; // Defend grid for player 1
    private char[][] player2DefendGrid; // Defend grid for player 2
    private char[][] player1AttackGrid; // Attack grid for player 1
    private char[][] player2AttackGrid; // Attack grid for player 2
    private int count;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JPanel shipPanel;
    private JLabel draggedShip;
    private Point initialClick;
    private String currentPlayer = "player1"; // Player name (could be set from login)
    private Set<String> placedShips = new HashSet<>();  // To track placed ships
    private boolean isHorizontal = true;  // Default ship placement orientation
    private JButton readyButton; // Ready-knop
    char[][] targetDefendGrid = currentPlayer.equals("player1") ? player2DefendGrid : player1DefendGrid;
    /**
     * Constructor
     * Initializes an 8x8 grid for the game and sets up the ship panel at the bottom.
     */
    public BattleshipGame() {
        super(GRID_COLS, GRID_ROWS);
        statusLabel.setText("Current player: " + currentPlayer);

        // Initialize the defense and attack grids for both players
        player1DefendGrid = new char[GRID_ROWS][GRID_COLS];
        player2DefendGrid = new char[GRID_ROWS][GRID_COLS];
        player1AttackGrid = new char[GRID_ROWS][GRID_COLS];
        player2AttackGrid = new char[GRID_ROWS][GRID_COLS];

        initializeGrids();
        
        // Initialize the grid references based on the current player
        updateCurrentPlayerGrids();

        initializeShipPanel();

        setVisible(true);
        updateBoardUI();
        // Establish connection to the server
        // connectToServer();
    }

    private void InfoTab() {

        JTabbedPane tabbedPane = new JTabbedPane();
    
        // Add the rules tab
        JPanel rulesPanel = new JPanel(new BorderLayout());
    
        JTextArea rulesText = new JTextArea();
        rulesText.setText(
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
        "• Beide spelers moeten eerst al hun schepen correct hebben geplaatst.\n" +
        "• Het spel kan op elk moment worden gereset als beide spelers daarmee instemmen.\n"
        );
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
    
        // Add the JTextArea with scroll pane to the rules panel
        JScrollPane scrollPane = new JScrollPane(rulesText);
        rulesPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Add the rules tab to the tabbed pane
        tabbedPane.addTab("Spelregels", rulesPanel);
    
        // Add the tabbed pane to the frame
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    



    /**
     * Update the grid references to point to the current player's grids.
     */
    private void updateCurrentPlayerGrids() {
        if (currentPlayer.equals("player1")) {
            currentPlayerDefendGrid = player1DefendGrid;
            currentPlayerAttackGrid = player1AttackGrid;
        } else {
            currentPlayerDefendGrid = player2DefendGrid;
            currentPlayerAttackGrid = player2AttackGrid;
        }
    }

    /**
     * Switch between players.
     */
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("player1") ? "player2" : "player1";
        updateCurrentPlayerGrids();
        // placedShips.clear();  // Reset the placed ships for the new player
        printCurrentPlayerDefendGrid();
        //readyButton.setEnabled(false);  // Disable ready button until ships are placed
        updateBoardUI();
    }

    //start of experimentalcode:
    private void handleReadyButton() {
        if (currentPlayer.equals("player1")) {
            
            // Switch to Player 2 for ship placement
            switchPlayer();  // Switch to player 2
            resetBoardForNextPlayer();  // Reset board for Player 2 to place ships
            readyButton.setEnabled(false);  // Disable button until Player 2 places all ships
            updateBoardUI();  // Update the board for Player 2
            statusLabel.setText("Current player: " + currentPlayer);
        } else {
            // Both players have placed their ships, now start the shooting phase
            goToShootingPhase();  // Move to the actual game phase
        }
    }

    /**
     * Updates the UI for both the defend and attack grids for the current player.
     */
    private void updateBoardUI() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (currentPlayer.equals("player1")){
                if (currentPlayerDefendGrid[row][col] == 'S'){
                    gridButtons[row][col].setBackground(Color.GRAY);
                } 
                if (currentPlayerDefendGrid[row][col] == 'X') {
                    gridButtons[row][col].setBackground(Color.BLACK);
                }
                if (currentPlayerDefendGrid[row][col] == 'H') {
                    gridButtons[row][col].setBackground(Color.RED);
                }
                if (currentPlayerDefendGrid[row][col] == '-'){
                    gridButtons[row][col].setBackground(Color.BLUE);
                }
                if (currentPlayerAttackGrid[row][col] == '-'){
                    gridButtonsPlayerTwo[row][col].setBackground(Color.BLUE);
                }
                if (currentPlayerAttackGrid[row][col] == 'H'){
                    gridButtonsPlayerTwo[row][col].setBackground(Color.RED);
                } 
                if (currentPlayerAttackGrid[row][col] == 'X') {
                    gridButtonsPlayerTwo[row][col].setBackground(Color.BLACK);
                }
            }
            else{
                if (currentPlayerDefendGrid[row][col] == 'S'){
                    gridButtonsPlayerTwo[row][col].setBackground(Color.GRAY);
                } 
                if (currentPlayerDefendGrid[row][col] == 'X') {
                    gridButtonsPlayerTwo[row][col].setBackground(Color.BLACK);
                }
                if (currentPlayerDefendGrid[row][col] == 'H') {
                    gridButtonsPlayerTwo[row][col].setBackground(Color.RED);
                }
                if (currentPlayerDefendGrid[row][col] == '-'){
                    gridButtonsPlayerTwo[row][col].setBackground(Color.BLUE);
                }
                if (currentPlayerAttackGrid[row][col] == '-'){
                    gridButtons[row][col].setBackground(Color.BLUE);
                }
                if (currentPlayerAttackGrid[row][col] == 'H'){
                    gridButtons[row][col].setBackground(Color.RED);
                } 
                if (currentPlayerAttackGrid[row][col] == 'X') {
                    gridButtons[row][col].setBackground(Color.BLACK);
                }
            }

        }}
    }
    


    private void resetBoardForNextPlayer() {
        // Reset grid buttons for Player 2's ship placement
         placedShips.clear();  // Clear placed ships for Player 2
    }


    //Stop of experimental code:

    /**
     * Initialize the 8x8 game grid with water ('-').
     */
    private void initializeGrids() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                player1DefendGrid[row][col] = '-'; // Empty water for player 1's defense grid
                player2DefendGrid[row][col] = '-'; // Empty water for player 2's defense grid
                player1AttackGrid[row][col] = '-'; // Empty water for player 1's attack grid
                player2AttackGrid[row][col] = '-'; // Empty water for player 2's attack grid

            }
        }
    }

    /**
     * Initialize the bottom panel to display ships.
     * The player can drag these ships and drop them onto the grid.
     */
    private void initializeShipPanel() {
        shipPanel = new JPanel();
        shipPanel.setLayout(new FlowLayout());
        shipPanel.setBackground(Color.LIGHT_GRAY);

        // Example ships of different sizes
        createShip("Carrier", 6);
        createShip("Battleship", 4);
        createShip("Cruiser", 3);
        createShip("Destroyer", 2);

        // Ready-knop
        readyButton = new JButton("Ready");
        readyButton.setEnabled(false); // Disabled until all ships are placed
        //readyButton.addActionListener(e -> goToShootingPhase());                    //dit is de ready knop naast setup
        readyButton.addActionListener(e -> handleReadyButton());
        shipPanel.add(readyButton);
        add(shipPanel, BorderLayout.SOUTH);
    }

    /**
     * Create a visual representation of a ship, which can be dragged.
     *
     * @param shipName The name of the ship
     * @param size     The size of the ship (number of grid cells it occupies)
     */
    private void createShip(String shipName, int size) {
        count ++;
        JLabel shipLabel = new JLabel(shipName + " (" + size + ")");
        shipLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        shipLabel.setOpaque(true);
        shipLabel.setBackground(Color.ORANGE);

        // Mouse events for drag-and-drop functionality
        shipLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Prevent ship from being dragged again if already placed
                if (!placedShips.contains(shipName)) {
                    draggedShip = shipLabel;
                    initialClick = e.getPoint();
                    shipLabel.setBackground(Color.RED);  // Change color to indicate dragging
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedShip != null) {
                    draggedShip.setBackground(Color.ORANGE);  // Reset after drop
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Right-click to rotate the ship
                if (SwingUtilities.isRightMouseButton(e)) {
                    isHorizontal = !isHorizontal;
                    JOptionPane.showMessageDialog(null, "Ship rotated to " + (isHorizontal ? "horizontal" : "vertical"));
                }
            }
        });
        
        shipPanel.add(shipLabel);
    }

    /**
     * Set the game name.
     *
     * @return String (game name)
     */
    @Override
    protected String getGameName() {
        return "Battleships";
    }

    /**
     * Handles grid button clicks for placing ships or shooting, based on game phase.
     *
     * @param row int - row of the clicked cell
     * @param col int - column of the clicked cell
     */
    @Override
    protected void onGridButtonClicked(int row, int col) {
        if (isShootingPhase) {
                handleShot(row, col);
            
        } else {
            // Ship placement logic (setup phase)
            if (draggedShip != null) {
                int shipSize = getShipSize(draggedShip.getText());
                String shipName = draggedShip.getText().split(" ")[0];

                if (placedShips.contains(shipName)) {
                    JOptionPane.showMessageDialog(this, "This ship has already been placed!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Place ship on the current player's defend grid
                if (canPlaceShip(row, col, shipSize)) {
                    placeShip(row, col, shipSize);
                    placedShips.add(shipName);

                    if (placedShips.size() == count) {  // All ships placed
                        readyButton.setEnabled(true);  // Enable ready button
                    }

                    draggedShip = null;  // Reset dragged ship
                } else {
                     JOptionPane.showMessageDialog(this, "Invalid ship placement!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Helper method to get the size of a ship from its label.
     *
     * @param shipLabel The label text of the ship
     * @return The size of the ship
     */
    private int getShipSize(String shipLabel) {
        String[] parts = shipLabel.split(" ");
        return Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
    }

    /**
     * Place a ship on the grid for the current player.
     *
     * @param row      Starting row
     * @param col      Starting column
     * @param shipSize The size of the ship
     */
    private void placeShip(int row, int col, int shipSize) {
        for (int i = 0; i < shipSize; i++) {
            if (isHorizontal) {
                currentPlayerDefendGrid[row][col + i] = 'S';  // 'S' for ship
            } else {
                currentPlayerDefendGrid[row + i][col] = 'S';
            }
        }
        updateBoardUI();
        printCurrentPlayerDefendGrid();
    }

    /**
     * Determine if a ship can be placed at the given coordinates.
     *
     * @param row      Starting row
     * @param col      Starting column
     * @param shipSize The size of the ship
     * @return true if the ship can be placed, false otherwise
     */
    private boolean canPlaceShip(int row, int col, int shipSize) {
        if (isHorizontal) {
            // Controleer of het schip binnen de grenzen blijft
            if (col + shipSize > GRID_COLS) return false;
    
            // Controleer elke positie van het schip
            for (int i = 0; i < shipSize; i++) {
                // Controleer of het schip overlapt met een ander schip
                if (currentPlayerDefendGrid[row][col + i] == 'S') return false;
    
                // Controleer de omliggende cellen voor elke positie van het schip
                if (!isBufferZoneClear(row, col + i)) return false;
            }
        } else {
            // Controleer of het schip binnen de grenzen blijft
            if (row + shipSize > GRID_ROWS) return false;
    
            // Controleer elke positie van het schip
            for (int i = 0; i < shipSize; i++) {
                // Controleer of het schip overlapt met een ander schip
                if (currentPlayerDefendGrid[row + i][col] == 'S') return false;
    
                // Controleer de omliggende cellen voor elke positie van het schip
                if (!isBufferZoneClear(row + i, col)) return false;
            }
        }
        return true;
    }
    
    /**
     * Controleert of er geen schepen zijn in de omliggende cellen van de gegeven positie.
     *
     * @param row De rijpositie om te controleren.
     * @param col De kolompositie om te controleren.
     * @return true als er geen schepen in de omliggende cellen zijn, anders false.
     */
    private boolean isBufferZoneClear(int row, int col) {
        // Controleer alle omliggende cellen in een 3x3 gebied rond (row, col)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
    
                // Controleer of de cel binnen de grenzen ligt
                if (newRow >= 0 && newRow < GRID_ROWS && newCol >= 0 && newCol < GRID_COLS) {
                    // Als er een schip ('S') in de aangrenzende cel staat, faalt de check
                    if (currentPlayerDefendGrid[newRow][newCol] == 'S') return false;
                }
            }
        }
        return true;
    }

    /**
     * Move to the shooting phase after ships are placed.
     */
    private void goToShootingPhase() {
        shipPanel.setVisible(false);
        statusLabel.setText("Shooting phase! Attack your opponent.");

        // Reset the grid for the shooting phase
        resetGrid();  // Clear the grid for attack moves
        revalidate();
        repaint();
        StartBattlephase();
    }

    // Variable to track if we are in the shooting phase
    private boolean isShootingPhase = false;


    /**
     * Starts the battle phase of the game.
     */
    private void StartBattlephase() {
        isShootingPhase = true;  // Enable shooting phase
        currentPlayer = "player1";  // Set starting player
        updateCurrentPlayerGrids();  // Update grids for the current player
        updateBoardUI();  // Refresh UI

        // Set up a MouseListener to handle the shooting phase on the grid
        

        // Start with player 1
        statusLabel.setText("Player 1's turn to attack.");
    }
    


    /**
     * Handles the player's shot by updating the attack and defend grids.
     *
     * @param row The row of the target cell.
     * @param col The column of the target cell.
     * @return true if the shot was valid, false if the cell was already targeted.
     */
    private boolean handleShot(int row, int col) {

        char[][] opponentDefendGrid = currentPlayer.equals("player1") ? player2DefendGrid : player1DefendGrid;
        char[][] currentPlayerAttackGrid = currentPlayer.equals("player1") ? player1AttackGrid : player2AttackGrid;

        
        // Check if this cell has already been attacked
        if (currentPlayerAttackGrid[row][col] != '-') {
            JOptionPane.showMessageDialog(this, "You've already shot here!", "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        // Check if the shot hits a ship
        if (opponentDefendGrid[row][col] == 'S') {
            opponentDefendGrid[row][col] = 'H';  // Mark as hit on opponent's defend grid
            currentPlayerAttackGrid[row][col] = 'H';  // Mark as hit on current player's attack grid
            updateBoardUI();
            statusLabel.setText(currentPlayer + " hit! you can go again");
            if (checkWinCondition(opponentDefendGrid)) {  // Check if game is won
                isShootingPhase = false;
                displayWinningMessage(); 
                statusLabel.setText(currentPlayer + " won!");
                resetGame();
            }
            if(opponentDefendGrid[row+1][col]!= 'S' && opponentDefendGrid[row-1][col]!= 'S' && opponentDefendGrid[row][col+1]!= 'S' && opponentDefendGrid[row][col-1]!= 'S'){
                statusLabel.setText(currentPlayer + " sunk a ship!");
            }
        } 
        else {
            currentPlayerAttackGrid[row][col] = 'X';  // Mark as miss on current player's attack grid
            opponentDefendGrid[row][col] = 'X';
            statusLabel.setText(currentPlayer + " missed! other players turn");
            switchPlayer();
            updateBoardUI();
        }

        updateBoardUI();
        return true;
        
    }

    /**
     * Checks if the provided defense grid has any remaining ships.
     *
     * @param targetDefendGrid The defense grid to check for remaining ships.
     * @return true if all of the opponent's ships are sunk, false otherwise.
     */
    private boolean checkWinCondition(char[][] targetDefendGrid) {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (targetDefendGrid[row][col] == 'S') {
                    return false;  // Ship parts remain, so no win yet
                }
            }
        }
        return true;  // No ship parts remain, current player wins
    }


    /**
     * Displays a winning message for the current player.
     */
    private void displayWinningMessage() {
        JOptionPane.showMessageDialog(this, currentPlayer + " won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Resets the game for a new round.
     */
    private void resetGame() {
        // Optionally reset all grids and UI elements here for a new game
        // This could include reinitializing the grids, clearing the UI, etc.
        // For example:
        initializeGrids();
        updateBoardUI();
        shipPanel.setVisible(true); // Make the ship panel visible again
        placedShips.clear();
        currentPlayer = "player1";
        updateCurrentPlayerGrids();
        readyButton.setEnabled(false);
        
    }

    /**
     * Checks if the game is in the shooting phase.
     *
     * @return true if both players are done placing ships, false otherwise.
     */
    private boolean isShootingPhase() {
        return placedShips.size() >= count;
    }



    /**
     * Send the ship placement information to the server.
     *
     * @param start  The start position of the ship
     * @param end    The end position of the ship
     */
    private void sendShipPlacement(int start, int end) {
        out.println(currentPlayer + " placed ship from " + start + " to " + end);
    }

    /**
     * Send the player's move to the server.
     *
     * @param position The position where the player is attacking
     */
    private void sendMove(int position) {
        out.println(currentPlayer + " attacked position " + position);
    }

    /**
     * Establishes a connection to the game server.
     */

    /**
     * Prints the current player's defense grid to the terminal.
     */
    private void printCurrentPlayerDefendGrid() {
        System.out.println("player 1 attack Grid:");
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                System.out.print(player1AttackGrid[row][col] + " ");
            }
            System.out.println();  // Move to the next line after each row
        }
        System.out.println();  // Extra line for better readability
    
    System.out.println(" player 1 Defend Grid:");
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                System.out.print(player1DefendGrid[row][col] + " ");
            }
            System.out.println();  // Move to the next line after each row
        }
        System.out.println();  // Extra line for better readability
    
    System.out.println( "player 2 Attack Grid:");
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                System.out.print(player2AttackGrid[row][col] + " ");
            }
            System.out.println();  // Move to the next line after each row
        }
        System.out.println();  // Extra line for better readability

        System.out.println( "player 2 defend Grid:");
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                System.out.print(player2DefendGrid[row][col] + " ");
            }
            System.out.println();  // Move to the next line after each row
        }
        System.out.println();  // Extra line for better readability
    }


}
