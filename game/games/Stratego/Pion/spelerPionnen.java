package game.games.Stratego.Pion;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

import game.games.Stratego.Strategys.IAttackStrategy;
import game.games.Stratego.Strategys.IMoveStrategy;

public class spelerPionnen {
    private static JPanel pionpanel;
    private ArrayList<Pion> pionnenPlayer1;
    private ArrayList<Pion> pionnenPlayer2;
    private HashMap<String, Integer> pionTelling;

    public spelerPionnen() {
        pionnenPlayer1 = new ArrayList<>();
        pionnenPlayer2 = new ArrayList<>();
        pionTelling = new HashMap<>();
        pionpanel = new JPanel();
        initializePionnen();
    }

    private void initializePionnen() {
        voegPionnenToe("Bomb", 11, 6, new noMove(), new noAttack());
        voegPionnenToe("Marshal", 10, 1, new Move(), new Attack());
        voegPionnenToe("General", 9, 1, new Move(), new Attack());
        voegPionnenToe("Colonel", 8, 2, new Move(), new Attack());
        voegPionnenToe("Major", 7, 3, new Move(), new Attack());
        voegPionnenToe("Captain", 6, 4, new Move(), new Attack());
        voegPionnenToe("Lieutenant", 5, 4, new Move(), new Attack());
        voegPionnenToe("Sergeant", 4, 4, new Move(), new Attack());
        voegPionnenToe("Miner", 3, 5, new Move(), new MinerAttack());
        voegPionnenToe("Scout", 2, 8, new ScoutMove(), new Attack());
        voegPionnenToe("Spy", 1, 1, new Move(), new SpyAttack());
        voegPionnenToe("Flag", 0, 1, new noMove(), new noAttack());
    }

    private void voegPionnenToe(String naam, int waarde, int aantal, IMoveStrategy moveStrategy, IAttackStrategy attackStrategy) {
        for (int i = 0; i < aantal; i++) {
            pionnenPlayer1.add(new Pion(naam, waarde, moveStrategy, attackStrategy, 1));
            pionnenPlayer2.add(new Pion(naam, waarde, moveStrategy, attackStrategy, 2));
        }

        pionTelling.put(naam, pionTelling.getOrDefault(naam, 0) + (2 * aantal)); // Count for both players

        if (pionTelling.get(naam) == 2 * aantal) {
            Showpionnen(naam, waarde, aantal);
        }
    }

    public void Showpionnen(String naam, int waarde, int aantal) {
        pionpanel.setLayout(new FlowLayout());
        pionpanel.setBackground(Color.LIGHT_GRAY);
        pionpanel.setPreferredSize(new Dimension(300, 600));

        JButton button = new JButton(naam + " " + waarde + " (" + aantal + " each)");
        button.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Player 1 and Player 2 each have " + aantal + " of the " + naam));
        pionpanel.add(button);
    }

    public static JPanel getPionPanel() {
        return pionpanel;
    }

    public ArrayList<Pion> getPionnenPlayer1() {
        return pionnenPlayer1;
    }

    public ArrayList<Pion> getPionnenPlayer2() {
        return pionnenPlayer2;
    }
}
