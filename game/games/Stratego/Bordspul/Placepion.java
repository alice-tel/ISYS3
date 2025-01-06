package game.games.Stratego.Bordspul;

import game.games.Stratego.Pion.spelerPionnen;
import game.games.Stratego.StrategoGame;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Placepion {

    private int row;
    private int col;

    public Placepion(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void place(int playerId, String[][] playerBoard, List<String> playerPionnen, List<String> geselecteerdePion, int aantalOver, JButton huidigeKnop) {
        if (geselecteerdePion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Geen pion geselecteerd! Kies eerst een pion.");
            return;
        }

        String selectedPion = geselecteerdePion.get(0);

        if (aantalOver <= 0 || !playerPionnen.contains(selectedPion)) {
            JOptionPane.showMessageDialog(null, "Geen beschikbare " + selectedPion + " om te plaatsen.");
            return;
        }

        // Check if the selected grid cell is valid for placing a piece
        if (!playerBoard[row][col].equals("-")) {
            JOptionPane.showMessageDialog(null, "Deze positie is al bezet!");
            return;
        }

        // Place the piece on the board
        playerBoard[row][col] = selectedPion;
        StrategoGame.Setgridbutton(row, col, Color.BLUE);
        StrategoGame.setbuttonstext(row, col, selectedPion);

        // Update the number of available pieces
        aantalOver--;

        // Remove the piece from the player's list
        playerPionnen.remove(selectedPion);

        // Reset the selection
        geselecteerdePion.clear();
        if (huidigeKnop != null) {
            huidigeKnop.setBackground(Color.WHITE);
        }

        // Check if all pieces have been placed for the current player
        if (playerPionnen.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Alle pionnen van speler " + playerId + " zijn geplaatst. Wissel van speler.");
            // Logic to switch to the next player can be implemented here
        }
    }
}
