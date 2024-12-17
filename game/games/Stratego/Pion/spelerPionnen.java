package game.games.Stratego.Pion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import game.games.Stratego.Strategys.IAttackStrategy;
import game.games.Stratego.Strategys.IMoveStrategy;

import java.awt.*;


public class spelerPionnen {
    private static
    JPanel pionpanel;
    private ArrayList<Pion> pionnen;
    private HashMap<String, Integer> pionTelling; // Map voor het bijhouden van pionnenaantallen

    public spelerPionnen(int size) {
        pionnen = new ArrayList<>();
        pionTelling = new HashMap<>(); // Initialiseer de HashMap
        pionpanel = new JPanel();
        initializePionnen(size);
    }

    private void initializePionnen(int size) {
        if (size == 10){
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
        voegPionnenToe("Flag", 0, 1, new noMove(), new noAttack());}
        if(size == 8){
            voegPionnenToe("Bomb", 11, 2, new noMove(), new noAttack());
            voegPionnenToe("Marshal", 10, 1, new Move(), new Attack());
            voegPionnenToe("General", 9, 1, new Move(), new Attack());
            voegPionnenToe("Miner", 3, 2, new Move(), new MinerAttack());
            voegPionnenToe("Scout", 2, 2, new ScoutMove(), new Attack());
            voegPionnenToe("Spy", 1, 1, new Move(), new SpyAttack());
            voegPionnenToe("Flag", 0, 1, new noMove(), new noAttack());}
        }

    private void voegPionnenToe(String naam, int waarde, int aantal, IMoveStrategy moveStrategy, IAttackStrategy attackStrategy) {
        for (int i = 0; i < aantal; i++) {
            pionnen.add(new Pion(naam, waarde, moveStrategy, attackStrategy));
        }

        pionTelling.put(naam, pionTelling.getOrDefault(naam, 0) + aantal);

        if (pionTelling.get(naam) == aantal) {
            Showpionnen(naam, waarde, aantal);
        }
    }

    public void Showpionnen(String naam, int waarde, int aantal) {
        pionpanel.setLayout(new FlowLayout());
        pionpanel.setBackground(Color.LIGHT_GRAY);
        pionpanel.setPreferredSize(new Dimension(300, 600));

        JButton button = new JButton(waarde < 11 
        ? (naam + " " + waarde + " (" + aantal + ")") 
        : (naam + " (" + aantal + ")"));

        button.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Je hebt " + aantal + " van de " + naam));
        pionpanel.add(button);
    }

    public static JPanel getPionPanel() {
        return pionpanel;
    }
}
