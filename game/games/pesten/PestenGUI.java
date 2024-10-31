package game.games.pesten;

import game.framework.GameFramework;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PestenGUI extends GameFramework {

    private Game game;
    private ArrayList<Player> players;
    private Card topCard;
    private int currentPlayer;
    private JLabel topCardLabel;

    public PestenGUI(int columns, int rows, ArrayList<Player> players) {
        super(columns, rows - 1);
        this.players = players;
        game = new Game(players);
        currentPlayer = 0;
        setupGame();
    }

    @Override
    protected String getGameName() {
        return "Pesten";
    }


    private void setupGame() {
        // Initialize players with hands and draw the starting card
        game.startGame();
        topCard = game.getTopCard();
        setSize(2000,650);

        // Initialize the top card label and add it to the GUI
        if (topCardLabel == null) {
            topCardLabel = new JLabel("", SwingConstants.CENTER);
            topCardLabel.setFont(new Font("Arial", Font.BOLD, 18));
            add(topCardLabel, BorderLayout.NORTH);
        }
        updateTopLabel();
        setupDrawButton();

        for (int i = 0; i< players.size(); i++) {
            updatePlayerHandDisplay(i); // Update player hand display
        }
    }

    @Override
    protected void onGridButtonClicked(int row, int col) {
        Player player = players.get(currentPlayer);

        // Check if the player clicked on a card in their hand
        if (row == currentPlayer && col < player.getHand().size()) {
            Card selectedCard = player.getHand().get(col);

            if (player.playCard(selectedCard, topCard)) {
                topCard = selectedCard;
                updateTopLabel();
                updatePlayerHandDisplay(currentPlayer);

                if (player.hasWon()) {
                    JOptionPane.showMessageDialog(this, player + " has won!");
                    resetGame();
                } else {
                    nextTurn();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid card choice! You can't play that card!");
            }
        } else if (row == players.size()) { // Last row reserved for the deck
            player.drawCard(game.getDeck());
            updatePlayerHandDisplay(currentPlayer);
            nextTurn();
        }
    }

    private void updateTopLabel() {
        Player player = players.get(currentPlayer);
        topCardLabel.setText("Top card: " + topCard.toString() + " | " + player);
    }



    private void updatePlayerHandDisplay(int playerIndex) {
        Player player = players.get(playerIndex);
        boolean isCurrentPlayer = (playerIndex == currentPlayer);

        for (int j = 0; j < gridButtons[playerIndex].length; j++) {
            if (j < player.getHand().size()) {
                if (isCurrentPlayer) {
                    // Set the card's suit and value on the button text
                    gridButtons[playerIndex][j].setText(player.getHand().get(j).toString());
                } else {
                    gridButtons[playerIndex][j].setText("---");
                }
            } else {
                gridButtons[playerIndex][j].setText("");
            }
            gridButtons[playerIndex][j].setFont(new Font("Arial", Font.PLAIN, 16));
        }
    }

    private void setupDrawButton() {
        JButton drawButton = new JButton("Draw Card");
        drawButton.setFont(new Font("Arial", Font.BOLD, 20));
        drawButton.addActionListener(e -> onDrawButtonClicked());

        JPanel drawPanel = new JPanel();
        drawPanel.add(drawButton);
        add(drawPanel, BorderLayout.SOUTH);
    }

    private void onDrawButtonClicked() {
        Player player = players.get(currentPlayer);
        player.drawCard(game.getDeck());
        updatePlayerHandDisplay(currentPlayer);
        nextTurn();
    }

    private void nextTurn() {
        currentPlayer = (currentPlayer + 1) % players.size();
        statusLabel.setText("" + players.get(currentPlayer));

        // Refresh display for all players to show/hide cards
        for (int i = 0; i < players.size(); i++) {
            updatePlayerHandDisplay(i);
        }

        updateTopLabel();
    }

    private void resetGame() {
        game = new Game(players); // Reset game instance
        currentPlayer = 0;
        setupGame();
        updateTopLabel();// Reset GUI
    }
}
