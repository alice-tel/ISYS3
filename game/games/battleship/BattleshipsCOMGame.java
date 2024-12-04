package game.games.battleship;

import game.framework.GameFramework;
import game.games.pesten.PestenGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class BattleshipsCOMGame extends GameFramework {
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
    public BattleshipsCOMGame() {
        super(GRID_COLS, GRID_ROWS,1500,800,"");
        setTitle("Battleships vs COM");

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
    }

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
     * Update the grid references to point to the current player's grids.
     */
    private void updateCurrentPlayerGrids() {
        if (currentPlayer.equals("player1")) {
            currentPlayerDefendGrid = player1DefendGrid;
            currentPlayerAttackGrid = player1AttackGrid;
        } else { //het is nu computers beurt
            currentPlayerDefendGrid = player2DefendGrid;
            currentPlayerAttackGrid = player2AttackGrid;
        }
    }

    /**
     * Switch between players.
     */
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("player1") ? "computerAi" : "player1";
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
            aiPlaceShips(); // Computer places ships randomly
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
//                if (currentPlayer.equals("player1")) {
                    if (currentPlayerDefendGrid[row][col] == 'S') {
                        gridButtons[row][col].setBackground(Color.GRAY);
                    }
                    if (currentPlayerDefendGrid[row][col] == 'X') {
                        gridButtons[row][col].setBackground(Color.BLACK);
                    }
                    if (currentPlayerDefendGrid[row][col] == 'H') {
                        gridButtons[row][col].setBackground(Color.RED);
                    }
                    if (currentPlayerDefendGrid[row][col] == '-') {
                        gridButtons[row][col].setBackground(Color.BLUE);
                    }
                    if (currentPlayerAttackGrid[row][col] == '-') {
                        gridButtonsPlayerTwo[row][col].setBackground(Color.BLUE);
                    }
                    if (currentPlayerAttackGrid[row][col] == 'H') {
                        gridButtonsPlayerTwo[row][col].setBackground(Color.RED);
                    }
                    if (currentPlayerAttackGrid[row][col] == 'X') {
                        gridButtonsPlayerTwo[row][col].setBackground(Color.BLACK);
                    }
//                } else {
//                    if (currentPlayerDefendGrid[row][col] == 'S') {
//                        gridButtonsPlayerTwo[row][col].setBackground(Color.GRAY);
//                    }
//                    if (currentPlayerDefendGrid[row][col] == 'X') {
//                        gridButtonsPlayerTwo[row][col].setBackground(Color.BLACK);
//                    }
//                    if (currentPlayerDefendGrid[row][col] == 'H') {
//                        gridButtonsPlayerTwo[row][col].setBackground(Color.RED);
//                    }
//                    if (currentPlayerDefendGrid[row][col] == '-') {
//                        gridButtonsPlayerTwo[row][col].setBackground(Color.BLUE);
//                    }
//                    if (currentPlayerAttackGrid[row][col] == '-') {
//                        gridButtons[row][col].setBackground(Color.BLUE);
//                    }
//                    if (currentPlayerAttackGrid[row][col] == 'H') {
//                        gridButtons[row][col].setBackground(Color.RED);
//                    }
//                    if (currentPlayerAttackGrid[row][col] == 'X') {
//                        gridButtons[row][col].setBackground(Color.BLACK);
//                    }
//                }

            }
        }
    }


    private void resetBoardForNextPlayer() {
        // Reset grid buttons for Player 2's ship placement
        placedShips.clear();  // Clear placed ships for Player 2
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

    @Override
    protected void onGridButtonClicked(int row, int col) {
        // Implement the logic for what happens when a grid button is clicked.
        // For example, handle player moves, check for hits, or update the attack grid.
        System.out.println("Grid button clicked at row: " + row + ", col: " + col);
        if (isShootingPhase) {
           return;
        } else {
            // Ship placement logic (setup phase)
            placeShips(row,col);
        }


        // Toggle current player if needed, or add other game logic here.
    }
    @Override
    protected void onGridButtonClicked2(int row, int col) {
         // Implement the logic for what happens when a grid button is clicked.
        // For example, handle player moves, check for hits, or update the attack grid.
        System.out.println("Grid button clicked at row: " + row + ", col: " + col);
        if (isShootingPhase && currentPlayer.equals("player1")) {
            handleShot(row, col);

        } else {
            // Ship placement logic (setup phase)
            return;
            }
        }
    
    public void placeShips(int row, int col){
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
        count++;
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
        if (currentPlayerAttackGrid[row][col] == 'H' || currentPlayerAttackGrid[row][col] == 'X') {
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

            // If the shot was a hit, we don't change the turn (continue same player)
            return true;

        } else {
            currentPlayerAttackGrid[row][col] = 'X';  // Mark as miss on current player's attack grid
            opponentDefendGrid[row][col] = 'X';
            statusLabel.setText(currentPlayer + " missed! other players turn");

            // After a  miss, switch to the opponent
            currentPlayer = currentPlayer.equals("player1") ? "computerAi" : "player1";
            if (currentPlayer.equals("computerAi")) {
                computerMove();
            }
            updateBoardUI();
            return false;
        }
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

        System.out.println("player 2 Attack Grid:");
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                System.out.print(player2AttackGrid[row][col] + " ");
            }
            System.out.println();  // Move to the next line after each row
        }
        System.out.println();  // Extra line for better readability

        System.out.println("player 2 defend Grid:");
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                System.out.print(player2DefendGrid[row][col] + " ");
            }
            System.out.println();  // Move to the next line after each row
        }
        System.out.println();  // Extra line for better readability
    }

    /**
     * Places random computer ships vertically and horizontally
     */
    private void aiPlaceShips() {
        // Define the ships and their sizes
        String[] shipNames = {"Carrier", "Battleship", "Cruiser", "Destroyer"};
        int[] shipSizes = {6, 4, 3, 2};

        // Iterate through each ship type
        for (int i = 0; i < shipNames.length; i++) {
            String shipName = shipNames[i];
            int shipSize = shipSizes[i];
            boolean placed = false;

            // Attempt to place the ship until it is successfully placed
            while (!placed) {
                // Randomly determine starting position and orientation
                int row = (int) (Math.random() * GRID_ROWS);
                int col = (int) (Math.random() * GRID_COLS);
                isHorizontal = Math.random() < 0.5; // Randomly choose between horizontal and vertical

                // Check if the ship can be placed at the selected position
                if (canPlaceShip(row, col, shipSize)) {
                    placeShip(row, col, shipSize); // Place the ship
                    placed = true; // Successfully placed the ship
                }
            }
        }
        // Proceed to the shooting phase after placing all ships
        goToShootingPhase();

        printCurrentPlayerDefendGrid();
    }

    /**
     * Makes the computers' move by using the Minimax function
     */
    private void computerMove() {
        // Check if we're in the shootingphase. if not, return early
        if (!isShootingPhase) {
            return;
        }

        // Set up a timer for a delay
        Timer timer = new Timer(1000, null);

        timer.addActionListener(e -> {
            if (!isShootingPhase) {
                timer.stop();
                return;
            }
                int[] bestMove = minimaxDecision();
                int row = bestMove[0];
                int col = bestMove[1];

                // Perform the shot and determine if it was a hit or a miss
                boolean hit = handleShot(row, col);

                if (!hit) {
                    currentPlayer = "player1";
                    timer.stop();
                } else {
                    updateBoardUI();
                }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private int[] minimaxDecision() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        boolean foundHitAdjacent = false;

        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                // Only consider untargeted cells
                if (player1DefendGrid[row][col] == 'H' || player1DefendGrid[row][col] == 'X') { // Only target cells with ships as test
                    continue; // Skip cells that have been targeted or missed
                }

                if (isAdjacentHit(row, col)) {
                    // Evaluate move based on non-all-knowing logic
                    int score = evaluateMove(row, col);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                        foundHitAdjacent = true;
                    }
                }
            }
        }

        // If no adjacent cells to hits are found, pick a random untargeted cell
        if (!foundHitAdjacent) {
            do {
                bestMove[0] = (int) (Math.random() * GRID_ROWS);
                bestMove[1] = (int) (Math.random() * GRID_COLS);
            } while (player1DefendGrid[bestMove[0]][bestMove[1]] == 'H' || player1DefendGrid[bestMove[0]][bestMove[1]] == 'X');
        }
        return bestMove;
    }

    private int evaluateMove(int row, int col) {
        int score = 0;

        // Increase score if adjacent cells show potential hits
        if (isAdjacentHit(row, col)) {
            score += 10;
        } else {
            score += (Math.random() > 0.7) ? 3 : 0; // Random low-priority scoring
        }

        return score;
    }

    private boolean isAdjacentHit(int row, int col) {
        return (isHit(row - 1, col) || isHit(row + 1, col) ||
                isHit(row, col - 1) || isHit(row, col + 1));
    }

    private boolean isHit(int row, int col) {
        // Ensure within bounds and check if cell was previously a hit
        return row >= 0 && row < GRID_ROWS && col >= 0 && col < GRID_COLS && player1DefendGrid[row][col] == 'H';
    }
}

// NOTE TO SELF!!!! IF I MISS "ALREADY SHOT HERE" FIX THAT GURL

