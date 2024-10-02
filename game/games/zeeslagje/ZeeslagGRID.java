package game.games.zeeslagje;

import java.awt.*;
import javax.swing.*;

public class ZeeslagGRID {
    int boardWidth = 1300;
    int boardHeight = 850;

    JFrame frame = new JFrame("Zeeslag");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel boardPanel2 = new JPanel();
    JPanel rulesPanel = new JPanel();

    JButton[][] board = new JButton[8][16];
    JButton[][] board1 = new JButton[8][8];
    JButton[][] board2 = new JButton[8][8];

    ZeeslagGRID() {
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Zeeslag");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        // JTabbedPane voor tabbladen
        JTabbedPane tabbedPane = new JTabbedPane();

        // Speelbord tab
        boardPanel.setLayout(new GridLayout(8, 16));
        boardPanel.setForeground(Color.lightGray);
        boardPanel2.setLayout(new GridLayout(8, 8));
        boardPanel2.setPreferredSize(new Dimension(500,500));
        boardPanel2.setBackground(Color.darkGray);

        // Voeg speelbord toe aan het bord panel
        for (int r = 0; r < 8 ; r++) {
            for (int c = 0; c < 8; c++) {
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setPreferredSize(new Dimension(50,50));
                board2[r][c] = tile;
                boardPanel2.add(tile);
            }
        }
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(500,500));
        boardPanel.setBackground(Color.darkGray);

        for (int r = 0; r < 8 ; r++) {
            for (int c = 0; c < 8; c++) {
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setPreferredSize(new Dimension(50,50));
                board[r][c] = tile;
                boardPanel.add(tile);
            }
        }

        // Voeg het speelbord paneel toe aan het eerste tabblad
        tabbedPane.addTab("Speelbord", boardPanel2);

        // Spelregels tab
        rulesPanel.setLayout(new BorderLayout());
        JLabel rulesLabel = new JLabel("<html><body><h2>Spelregels:</h2>"
                + "<ul>"
                + "<li>Plaats je schepen op het bord.</li>"
                + "<li>Raad de positie van de vijandelijke schepen.</li>"
                + "<li>Als een schot raakt word het veld rood.</li>"
                + "<li>Vernietig alle schepen van je tegenstander om te winnen.</li>"
                + "</ul></body></html>");
        rulesPanel.add(rulesLabel, BorderLayout.CENTER);

        // Voeg de spelregels toe aan het tweede tabblad
        tabbedPane.addTab("Spelregels", rulesPanel);

        // Voeg de tabbedPane toe aan het frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.pack();
    }
}
