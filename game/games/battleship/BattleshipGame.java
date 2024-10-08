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
    private char[][] player1Grid;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JPanel shipPanel;
    private JLabel draggedShip;
    private Point initialClick;
    private String currentPlayer = "player1"; // Player name (could be set from login)
    private Set<String> placedShips = new HashSet<>();  // To track placed ships
    private boolean isHorizontal = true;  // Default ship placement orientation

    /**
     * Constructor
     * Initializes an 8x8 grid for the game and sets up the ship panel at the bottom.
     */
    public BattleshipGame() {
        super(GRID_COLS, GRID_ROWS); // Generate 8x8 grid
        player1Grid = new char[GRID_ROWS][GRID_COLS];
        initializeGrids();
        initializeShipPanel();

        setVisible(true);

        // Establish connection to the server
        connectToServer();
    }

    /**
     * Initialize the 8x8 game grid with water ('-').
     * This method uses the grid from GameFramework.
     */
    private void initializeGrids() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                player1Grid[row][col] = '-'; // Empty water
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
            // Logic to place the ship on the grid
            int shipSize = getShipSize(draggedShip.getText());
            String shipName = draggedShip.getText().split(" ")[0];

            // Check if the ship has already been placed
            if (placedShips.contains(shipName)) {
                JOptionPane.showMessageDialog(this, "This ship has already been placed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ensure ship fits within the grid and doesn't overlap with another ship
            if (canPlaceShip(row, col, shipSize)) {
                placeShip(row, col, shipSize);  // Place the ship on the grid
                placedShips.add(shipName);  // Mark this ship as placed
                draggedShip = null;  // Reset the dragged ship after placing

                // Send placement to the server
                sendShipPlacement(row * GRID_COLS + col, isHorizontal ? (row * GRID_COLS + col + shipSize - 1) : ((row + shipSize - 1) * GRID_COLS + col));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ship placement. Ships cannot overlap or go out of bounds.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Send move to the server
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
        // Extract the size from the label, e.g., "Carrier (5)" -> 5
        String sizeStr = shipLabel.substring(shipLabel.indexOf('(') + 1, shipLabel.indexOf(')'));
        return Integer.parseInt(sizeStr);
    }

    /**
     * Check if a ship can be placed at the given row and column.
     *
     * @param row      The starting row for the ship
     * @param col      The starting column for the ship
     * @param shipSize The size of the ship
     * @return True if the ship can be placed, false otherwise
     */
    private boolean canPlaceShip(int row, int col, int shipSize) {
        if (isHorizontal) {
            // Check if ship fits within the grid horizontally and doesn't overlap
            if (col + shipSize > GRID_COLS) {
                return false;
            }
            for (int i = 0; i < shipSize; i++) {
                if (player1Grid[row][col + i] == 'S') {
                    return false;  // Cannot place ship here because it's already occupied
                }
            }
        } else {
            // Check if ship fits within the grid vertically and doesn't overlap
            if (row + shipSize > GRID_ROWS) {
                return false;
            }
            for (int i = 0; i < shipSize; i++) {
                if (player1Grid[row + i][col] == 'S') {
                    return false;  // Cannot place ship here because it's already occupied
                }
            }
        }
        return true;
    }

    /**
     * Place a ship on the grid either horizontally or vertically.
     *
     * @param row      The starting row for the ship
     * @param col      The starting column for the ship
     * @param shipSize The size of the ship
     */
    private void placeShip(int row, int col, int shipSize) {
        if (isHorizontal) {
            for (int i = 0; i < shipSize; i++) {
                gridButtons[row][col + i].setText("S");
                gridButtons[row][col + i].setBackground(Color.GRAY);
                player1Grid[row][col + i] = 'S';  // Mark grid with ship
            }
        } else {
            for (int i = 0; i < shipSize; i++) {
                gridButtons[row + i][col].setText("S");
                gridButtons[row + i][col].setBackground(Color.GRAY);
                player1Grid[row + i][col] = 'S';  // Mark grid with ship
            }
        }
    }

    /**
     * Establish a connection to the server and set up input/output streams.
     */
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345); // Replace with server address and port
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send login command
            out.println("login " + currentPlayer);
            String response = in.readLine();
            if (response.startsWith("OK")) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Login failed: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send ship placement to the server.
     *
     * @param beginIndex Start index of the ship
     * @param endIndex End index of the ship
     */
    private void sendShipPlacement(int beginIndex, int endIndex) {
        out.println("place [" + beginIndex + ", " + endIndex + "]");
        try {
            String response = in.readLine();
            if (response.startsWith("OK")) {
                System.out.println("Ship placed successfully.");
            } else {
                System.out.println("Ship placement failed: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a move (attack) to the server.
     *
     * @param index The index of the cell being attacked
     */
    private void sendMove(int index) {
        out.println("move " + index);
        try {
            String response = in.readLine();
            if (response.startsWith("OK")) {
                System.out.println("Move successful.");
            } else {
                System.out.println("Move failed: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
