package game.launcher;

import game.games.tictactoe.TicTacToeGame;

import javax.swing.*;

/**
 * Here the screen where a player gets to choose how to play the game gets rendered
 * action event listeners are present on the types of modes the player gets to choose from
 * the startGame method loads the corresponding game class
 */
public class GameOptionsFrame extends JFrame {
    public GameOptionsFrame(String gameName) {
        setTitle("Game Options - " + gameName);
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton playerVsPlayerButton = new JButton("Player Vs Player");
        JButton playerVsComputerButton = new JButton("Player Vs Computer");
        JButton computerVsComputerButton = new JButton("Computer Vs Computer");

        playerVsPlayerButton.addActionListener(e -> {
            startGame(gameName, "Player vs Player");
            dispose();
        });

        playerVsComputerButton.addActionListener(e -> {
            // Start the game
            startGame(gameName, "Player vs Computer");
            dispose();
        });

        computerVsComputerButton.addActionListener(e -> {
            // Start the game
            startGame(gameName, "Computer vs Computer");
            dispose();
        });

        panel.add(playerVsPlayerButton);
        panel.add(playerVsComputerButton);
        panel.add(computerVsComputerButton);
        add(panel);
        setVisible(true);
    }

    /**
     * check the game name to start corresponding game
     * @TODO currently checks the string directly, maybe change this later?
     *
     * @param gameName String (name of selected game)
     * @param mode String (selected game more (pve, pvp, eve))
     */
    private void startGame(String gameName, String mode) {
        if (gameName.equals("Tic-Tac-Toe") && mode.equals("Player vs Player")) {
            new TicTacToeGame(); // Start PvP Tic Tac Toe game
        } else if (gameName.equals("Tic-Tac-Toe") && mode.equals("Player vs Computer")) {
            new TicTacToeGame(); // Start Tic Tac Toe game, replace with PvC game when finished
        }
        System.out.println("Starting " + gameName + " in " + mode + " mode.");
    }
}
