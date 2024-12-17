package game.games.Stratego;

import java.awt.*;
import javax.swing.*;
import game.framework.GameFramework;
import game.games.Stratego.Bordspul.Placepion;
import game.games.Stratego.Pion.*;

public class StrategoGame extends GameFramework {
    private JPanel shipPanel;
    private JButton readyButton;
    private spelerPionnen pionnenSpeler1;
    private spelerPionnen pionnenSpeler2;

    public StrategoGame(int size) {
        super(size, size, 1600, 900, "Hier moeten de spelregels van Stratego komen");
        statusLabel.setText("hello");
        setVisible(true); // Show the frame
        
        new Game(size);

        JPanel pionPanel = spelerPionnen.getPionPanel();

        add(pionPanel,BorderLayout.EAST);
                
    }

    @Override
    protected String getGameName() {
        return "Stratego";
    }

    @Override
    protected void onGridButtonClicked(int row, int col) {
        new Placepion(row,col);
            }
    
    public static void Setgridbutton(int row, int col, Color color){

    }
        
}
