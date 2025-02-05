package game.launcher;

import javax.swing.*;

import game.games.Stratego.StrategoGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SelectGridSize extends JFrame {

    boolean isAI;
    boolean isAI2;

    SelectGridSize(boolean isAI,boolean isAI2) {

        this.isAI = isAI;
        this.isAI2 = isAI2;
        setTitle("SetGridSize");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton TenbyTenStratego = new JButton("10x10");
        JButton EightbyEightStratego = new JButton("8x8");

        TenbyTenStratego.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame("Stratego10x10");
            }
        });
        EightbyEightStratego.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame("Stratego8x8");
            }
        });
        panel.add(TenbyTenStratego);
        panel.add(EightbyEightStratego);
        add(panel);
        setVisible(true);
    }
    private void startGame(String mode) {
        if ( mode.equals("Stratego10x10")) {
            new StrategoGame(10,isAI,isAI2); // Start PvP Tic Tac Toe game
        }
        if ( mode.equals("Stratego8x8")) {
            new StrategoGame(8,isAI,isAI2); // Start PvP Tic Tac Toe game
        }
    }
}
    

