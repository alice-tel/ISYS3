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
    private ArrayList<Pion> player1Pionnen;
    private ArrayList<Pion> player2Pionnen;
    private HashMap<String, Integer> pionTelling; // Map voor het bijhouden van pionnenaantallen
    private JButton huidigeKnop; // Houdt de huidige geselecteerde knop bij
    private List<String> geselecteerdePion; // Lijst van geselecteerde pionnen
    private int waardegeselecteerdePion; // Lijst van geselecteerde pionnen
    private int aantalOver;

    public spelerPionnen(int size) {
        pionnen = new ArrayList<>();
        player1Pionnen = new ArrayList<>();
        player2Pionnen = new ArrayList<>();
        geselecteerdePion = new ArrayList<>();
        pionTelling = new HashMap<>(); // Initialiseer de HashMap
        pionpanel = new JPanel();
        pionpanel.setLayout(new BoxLayout(pionpanel, BoxLayout.Y_AXIS)); // Layout verticaal
        pionpanel.setBackground(Color.LIGHT_GRAY);
        pionpanel.setPreferredSize(new Dimension(150, 600));
        initializePionnen(size);
        GivePlayerPionnen(size);
    }

    private void initializePionnen(int size) {
        if (size == 10) {
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
            voegPionnenToe("Flag", 0, 1, new noMove(), new noAttack());
        }
        if (size == 8) {
            voegPionnenToe("Marshal", 10, 1, new Move(), new Attack());
            voegPionnenToe("General", 9, 1, new Move(), new Attack());
            voegPionnenToe("Miner", 3, 2, new Move(), new MinerAttack());
            voegPionnenToe("Scout", 2, 2, new ScoutMove(), new Attack());
            voegPionnenToe("Spy", 1, 1, new Move(), new SpyAttack());
            voegPionnenToe("Bomb", 11, 2, new noMove(), new noAttack());
            voegPionnenToe("Flag", 0, 1, new noMove(), new noAttack());
        }
    }

    private void GivePlayerPionnen(int size) {
        if (size == 10) {
            assignPionnenToPlayer("Marshal", 10, 1, new Move(), new Attack(), 1);
            assignPionnenToPlayer("General", 9, 1, new Move(), new Attack(), 2);
            assignPionnenToPlayer("Colonel", 8, 2, new Move(), new Attack(), 1);
            assignPionnenToPlayer("Major", 7, 3, new Move(), new Attack(), 2);
            assignPionnenToPlayer("Captain", 6, 4, new Move(), new Attack(), 1);
            assignPionnenToPlayer("Lieutenant", 5, 4, new Move(), new Attack(), 2);
            assignPionnenToPlayer("Sergeant", 4, 4, new Move(), new Attack(), 1);
            assignPionnenToPlayer("Miner", 3, 5, new Move(), new MinerAttack(), 2);
            assignPionnenToPlayer("Scout", 2, 8, new ScoutMove(), new Attack(), 1);
            assignPionnenToPlayer("Spy", 1, 1, new Move(), new SpyAttack(), 2);
            assignPionnenToPlayer("Bomb", 11, 6, new noMove(), new noAttack(), 1);
            assignPionnenToPlayer("Flag", 0, 1, new noMove(), new noAttack(), 2);
        }
        if (size == 8) {
            assignPionnenToPlayer("Marshal", 10, 1, new Move(), new Attack(), 1);
            assignPionnenToPlayer("General", 9, 1, new Move(), new Attack(), 2);
            assignPionnenToPlayer("Miner", 3, 2, new Move(), new MinerAttack(), 1);
            assignPionnenToPlayer("Scout", 2, 2, new ScoutMove(), new Attack(), 2);
            assignPionnenToPlayer("Spy", 1, 1, new Move(), new SpyAttack(), 1);
            assignPionnenToPlayer("Bomb", 11, 2, new noMove(), new noAttack(), 2);
            assignPionnenToPlayer("Flag", 0, 1, new noMove(), new noAttack(), 1);
        }
    }

    private void assignPionnenToPlayer(String naam, int waarde, int aantal, IMoveStrategy moveStrategy, IAttackStrategy attackStrategy, int playerId) {     //job comment:idee hierachter: Ik wil niet de orginele pionnenlijst aanpassen want stel ik leeg die met speler1 dan heeft speler2 niks. Mijn idee was als volgt: Als een speler een pion plaats, (de naam van de pion wordt gepakt met thomas zijn buttons) dan wordt uit die speler zijn array 1 van de namen weggehaald en wordt t button aantal van die pion -1 gedaan. Nadat speler 1 weer klaar is met plaatsen dan moet t aantal weer omhooggegooid voor speler2.
        for (int i = 0; i < aantal; i++) {
            Pion pion = new Pion(naam, waarde, moveStrategy, attackStrategy, playerId);
            if (playerId == 1) {
                player1Pionnen.add(pion);
            } else if (playerId == 2) {
                player2Pionnen.add(pion);
            }
        }
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
    
        // Capture final variables
        final String finalNaam = naam; // Make a copy of naam                 //job comment:dit was nodig anders kreeg een lege array van geselecteerdepion 
        final int finalWaarde = waarde; // Make a copy of waarde
    
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
                    geselecteerdePion.clear();
                    waardegeselecteerdePion = 13;
                } else {
                    // Selecteer knop
                    if (aantalOver > 0) {
                        button.setBackground(Color.ORANGE);
                        huidigeKnop = button;
                        geselecteerdePion.clear();
                        geselecteerdePion.add(finalNaam); // Use finalNaam here
                        waardegeselecteerdePion = finalWaarde; // Use finalWaarde here
                        System.out.println("Geselecteerde pion: " + geselecteerdePion); // Debugging output
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Geen pionnen meer beschikbaar voor " + finalNaam);
                    }
                }
            }
        });
    
        pionpanel.add(button);
        pionpanel.revalidate(); // Layout updaten
    }

    public List<String> getGeselecteerdePion() {
        return geselecteerdePion;
    }
    
    public void decrementPionCount(String naam) {
        if (pionTelling.containsKey(naam)) {
            int remaining = pionTelling.get(naam) - 1;
            pionTelling.put(naam, remaining);
            aantalOver = remaining;
        }
    }
    
    
    

    public static JPanel getPionPanel() {
        return pionpanel;
    }
}
