package game.launcher;

import javax.swing.*;

/**
 * basic launcher
 * creates a new game selection frame with all existing games :)
 */
public class GameLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameSelectionFrame());
    }
}

