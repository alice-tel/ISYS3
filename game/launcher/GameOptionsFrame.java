package game.launcher;

import game.games.battleship.BattleshipGame;
import game.games.tictactoe.TicTacToeCOMgame;
import game.games.tictactoe.TicTacToeCvCGame;
import game.games.tictactoe.TicTacToeGame;
import game.games.tictactoe.TicTacToeClient;

import javax.swing.*;

public class GameOptionsFrame extends JFrame {
    public GameOptionsFrame(String gameName) {
        setTitle("Game Options - " + gameName);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton playerVsPlayerButton = new JButton("Player Vs Player");
        JButton playerVsComputerButton = new JButton("Player Vs Computer");
        JButton computerVsComputerButton = new JButton("Computer Vs Computer");
        JButton tournamentButton = new JButton("Tournament");

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

        tournamentButton.addActionListener(e -> {
            startTournamentGame();
            dispose();
        });

        panel.add(playerVsPlayerButton);
        panel.add(playerVsComputerButton);
        panel.add(computerVsComputerButton);
        panel.add(tournamentButton);
        add(panel);
        setVisible(true);
    }

    private void startTournamentGame() {
        // Prompt for player name in a pop-up dialog
        String playerName = JOptionPane.showInputDialog(this, "Enter your player name (alphanumeric, max 16 characters, can't start with a number):");

        if (playerName != null && isValidName(playerName)) {
            // Start the TicTacToeClient with the valid player name
            TicTacToeClient client = new TicTacToeClient(playerName);
            new Thread(client).start(); // Start the client in a new thread
        } else {
            JOptionPane.showMessageDialog(this, "Invalid name! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidName(String name) {
        // Check name constraints: alphanumeric, not starting with a number, max 16 characters
        return name != null && name.matches("^[a-zA-Z][a-zA-Z0-9]{0,15}$");
    }

    private void startGame(String gameName, String mode) {
        if (gameName.equals("Tic-Tac-Toe") && mode.equals("Player vs Player")) {
            new TicTacToeGame(); // Start PvP Tic Tac Toe game
        } else if (gameName.equals("Tic-Tac-Toe") && mode.equals("Player vs Computer")) {
            new TicTacToeCOMgame(); // Start PvC TicTacToe game
        } else if (gameName.equals("Tic-Tac-Toe") && mode.equals("Computer vs Computer")) {
            new TicTacToeCvCGame(); // Start CvC TicTacToe game
        } else if (gameName.equals("Battleships") && mode.equals("Player vs Player")) {
            new BattleshipGame(); // Start Battleship player vs player
        }

        System.out.println("Starting " + gameName + " in " + mode + " mode.");
    }
}
