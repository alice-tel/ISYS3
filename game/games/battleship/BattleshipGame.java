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

    /**
     * Constructor
     * Initializes an 8x8 grid for the game and sets up the ship panel at the bottom.
     */
    public BattleshipGame() {
        super(GRID_COLS, GRID_ROWS);
        
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

        // Establish connection to the server
        // connectToServer();
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
        placedShips.clear();  // Reset the placed ships for the new player
        readyButton.setEnabled(false);  // Disable ready button until ships are placed
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
     * Initialize the bottom panel to display ships.
     * The player can drag these ships and drop them onto the grid.
     */
    private void initializeShipPanel() {
        shipPanel = new JPanel();
        shipPanel.setLayout(new FlowLayout());
        shipPanel.setBackground(Color.LIGHT_GRAY);

        // Example ships of different sizes
        createShip("Carrier", 5);
        createShip("Battleship", 4);
        createShip("Cruiser", 3);
        createShip("Submarine", 3);
        createShip("Destroyer", 2);

        // Ready-knop
        readyButton = new JButton("Ready");
        readyButton.setEnabled(false); // Disabled until all ships are placed
        readyButton.addActionListener(e -> goToShootingPhase());

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
     * Handles grid button clicks to place a ship or send a move to the server.
     *
     * @param row int
     * @param col int
     */
    @Override
    protected void onGridButtonClicked(int row, int col) {
        if (draggedShip != null) {
            // Logic to place the ship on the defend grid
            int shipSize = getShipSize(draggedShip.getText());
            String shipName = draggedShip.getText().split(" ")[0];

            // Check if the ship has already been placed
            if (placedShips.contains(shipName)) {
                JOptionPane.showMessageDialog(this, "This ship has already been placed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ensure ship fits within the grid and doesn't overlap with another ship
            if (canPlaceShip(row, col, shipSize)) {
                placeShip(row, col, shipSize);  // Place the ship on the defend grid
                placedShips.add(shipName);  // Mark this ship as placed
                draggedShip = null;  // Reset the dragged ship after placing

                // Controleer of alle schepen zijn geplaatst
                if (placedShips.size() == 5) { // Assuming 5 ships in total
                    readyButton.setEnabled(true); // Activeer de Ready-knop
                }

                // Send placement to the server
                sendShipPlacement(row * GRID_COLS + col, isHorizontal ? (row * GRID_COLS + col + shipSize - 1) : ((row + shipSize - 1) * GRID_COLS + col));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ship placement. Ships cannot overlap or go out of bounds.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Send move to the server (use the attack grid)
            sendMove(row * GRID_COLS + col);
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
     * @param row       Starting row
     * @param col       Starting column
     * @param shipSize  The size of the ship
     */
    private void placeShip(int row, int col, int shipSize) {
        for (int i = 0; i < shipSize; i++) {
            if (isHorizontal) {
                currentPlayerDefendGrid[row][col + i] = 'S';  // 'S' for ship
                gridButtons[row][col + i].setBackground(Color.GRAY);
            } else {
                currentPlayerDefendGrid[row + i][col] = 'S';
                gridButtons[row + i][col].setBackground(Color.GRAY);
            }
        }
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
            if (col + shipSize > GRID_COLS) return false;  // Ship goes out of bounds
            for (int i = 0; i < shipSize; i++) {
                if (currentPlayerDefendGrid[row][col + i] == 'S') return false;  // Ship overlaps
            }
        } else {
            if (row + shipSize > GRID_ROWS) return false;  // Ship goes out of bounds
            for (int i = 0; i < shipSize; i++) {
                if (currentPlayerDefendGrid[row + i][col] == 'S') return false;  // Ship overlaps
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
    }

    /**
     * Reset the grid, optionally preparing it for the next phase.
     */
    private void resetGrid() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                gridButtons[row][col].setText("");
                gridButtons[row][col].setBackground(Color.BLUE);
                currentPlayerAttackGrid[row][col] = '-';  // Reset attack grid to empty water
            }
        }
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
    
}
