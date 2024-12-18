package game.launcher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the main game selection screen
 * be sure to add a new action event listener if a new game gets implemented
 * as well as a new panel (see existing games for examples)
 */
public class GameSelectionFrame extends JFrame {
    public GameSelectionFrame() {
        setTitle("Game Launcher");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton ticTacToeButton = new JButton("Tic-Tac-Toe");
        JButton battleshipButton = new JButton("Battleships");
        JButton pestenButton = new JButton("Pesten");
        JButton StrategoButton = new JButton("Stratego");

        ticTacToeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GameOptionsFrame("Tic-Tac-Toe");
            }
        });
        battleshipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GameOptionsFrame("Battleships");
            }
        });
        StrategoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GameOptionsFrame("Stratego");
            }});
        pestenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { new GameOptionsFrame("Pesten"); }
        });
        
        panel.add(StrategoButton);
        panel.add(ticTacToeButton);
        panel.add(battleshipButton);
        panel.add(pestenButton);
        add(panel);
        setVisible(true);
    }
}
