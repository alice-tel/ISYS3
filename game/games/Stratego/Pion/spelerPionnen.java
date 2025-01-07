package game.games.Stratego.Pion;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import game.games.Stratego.Strategys.IAttackStrategy;
import game.games.Stratego.Strategys.IMoveStrategy;



public class spelerPionnen {
    private static JPanel pionpanel;
    private ArrayList<Pion> pionnen;
    private HashMap<String, Integer> pionTelling; // Map voor het bijhouden van pionnenaantallen
    private JButton huidigeKnop; // Houdt de huidige geselecteerde knop bij
    private String geselecteerdePion = ""; // Lijst van geselecteerde pionnen
    private int waardegeselecteerdePion; // Lijst van geselecteerde pionnen
    private int aantalOver;
    private String test = "";

    public spelerPionnen(int size) {
        pionnen = new ArrayList<>();
        // geselecteerdePion = new String();
        pionTelling = new HashMap<>(); // Initialiseer de HashMap
        pionpanel = new JPanel();
        pionpanel.setLayout(new BoxLayout(pionpanel, BoxLayout.Y_AXIS)); // Layout verticaal
        pionpanel.setBackground(Color.LIGHT_GRAY);
        pionpanel.setPreferredSize(new Dimension(150, 600));
        initializePionnen(size);
    }

    private void initializePionnen(int size) {
        if (size == 10){
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
        voegPionnenToe("Bomb", 11, 6, new noMove(), new noAttack());
        voegPionnenToe("Flag", 0, 1, new noMove(), new noAttack());}
        if(size == 8){
            voegPionnenToe("Marshal", 10, 1, new Move(), new Attack());
            voegPionnenToe("General", 9, 1, new Move(), new Attack());
            voegPionnenToe("Miner", 3, 2, new Move(), new MinerAttack());
            voegPionnenToe("Scout", 2, 2, new ScoutMove(), new Attack());
            voegPionnenToe("Spy", 1, 1, new Move(), new SpyAttack());
            voegPionnenToe("Bomb", 11, 2, new noMove(), new noAttack());
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
        JButton button = new JButton((waarde > 0 && waarde < 11)
                ? (naam + " " + waarde + " (" + aantal + ")")
                : (naam + " (" + aantal + ")"));

        aantalOver = aantal;
        

        // Kleur instellen
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (huidigeKnop != null && huidigeKnop != button) {
                    // Deselecteer vorige knop
                    huidigeKnop.setBackground(Color.WHITE);
                }

                if (button == huidigeKnop) {
                    // Klik nogmaals: deselecteer knop
                    huidigeKnop.setBackground(Color.WHITE);
                    huidigeKnop = null;
                    // geselecteerdePion = naam;
                    waardegeselecteerdePion = 13;
                } else {
                    // Selecteer knop
                    if (aantalOver > 0) {
                        button.setBackground(Color.ORANGE);
                        huidigeKnop = button;
                        
                     
                    System.out.println("Before assignment, geselecteerdePion: " + geselecteerdePion);
                    setgeselecteerdepion(naam);
                    //geselecteerdepion = naam
                    System.out.println("Afther assignment, geselecteerdePion: " + geselecteerdePion);


                        waardegeselecteerdePion = waarde;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Geen pionnen meer beschikbaar voor " + naam);
                    }
                }
                
                
            }
        });
        pionpanel.add(button);
        pionpanel.revalidate(); // Layout updaten
    }

    public void setgeselecteerdepion(String pion){
        System.out.println("setter" + geselecteerdePion);
        geselecteerdePion = pion;
        test = geselecteerdePion;
        System.out.println("setter" + geselecteerdePion);

    }

    public String getgeselecteerdepion() {
        System.out.println("test = " + test);
        // if (geselecteerdePion.isEmpty()) {
        //     System.out.println("No pion selected yet.");
        //     return "";
        // }
        // geselecteerdePion = naam; // Or handle appropriately
        System.out.println("spelerPIONNEN GESELECTEERDE PION TEST = " + geselecteerdePion);
        return geselecteerdePion;
    }
    
    
    public ArrayList<Pion> getPionnen(){
        return pionnen;
    } 

    public static JPanel getPionPanel() {
        return pionpanel;
    }

    // public static void main(String[] args){
    //     spelerPionnen sp = new spelerPionnen(10);
    //     System.out.println(" begin");
    //     sp.setgeselecteerdepion("kaas");
    //     System.out.println(sp.geselecteerdePion);
    // }
}
