package game.games.Stratego;

<<<<<<< Updated upstream
=======
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
>>>>>>> Stashed changes
import game.framework.GameFramework;

<<<<<<< Updated upstream
public class StrategoGame extends GameFramework{
    public StrategoGame(int size){
        super(size,size, 1600, 900,"hoi");
        setVisible(true);
=======
public class StrategoGame extends GameFramework {
    private JPanel shipPanel;
    private JButton readyButton;
    private spelerPionnen pionnenSpeler1;
    private spelerPionnen pionnenSpeler2;

    public StrategoGame(int size) {
        super(size, size, 1600, 900, "Hier moeten de spelregels van Stratego komen");
        statusLabel.setText("hello");
        setVisible(true); // Show the frame
        
        new Game();

        JPanel pionPanel = spelerPionnen.getPionPanel();

        add(pionPanel,BorderLayout.EAST);
        printAllPions();
        
    }
    public Pion attack(){
        return null;
>>>>>>> Stashed changes
    }

    @Override
    protected String getGameName() {
        return "Stratego";
    }

    @Override
    protected void onGridButtonClicked(int row, int col) {
        // TODO Auto-generated method stub
        return;
    }
    private void printAllPions() {
        spelerPionnen spelerPionnenInstance = new spelerPionnen();

        // Get all pions for Player 1 and Player 2
        ArrayList<Pion> player1Pions = spelerPionnenInstance.getPionnenPlayer1();
        ArrayList<Pion> player2Pions = spelerPionnenInstance.getPionnenPlayer2();

        System.out.println("=== Player 1 Pions ===");
        for (Pion pion : player1Pions) {
            System.out.println("Name: " + pion.getNaam() +
                    ", Value: " + pion.getWaarde() +
                    ", Player: " + pion.getPlayer());
        }

        System.out.println("=== Player 2 Pions ===");
        for (Pion pion : player2Pions) {
            System.out.println("Name: " + pion.getNaam() +
                    ", Value: " + pion.getWaarde() +
                    ", Player: " + pion.getPlayer());
        }
    }

}
