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
    private JPanel pionpanel;
    private ArrayList<Pion> pionnen;
    private HashMap<String, Integer> pionTelling; // Map voor het bijhouden van pionnenaantallen
    private JButton huidigeKnop; // Houdt de huidige geselecteerde knop bij
    private String geselecteerdePion;// Lijst van geselecteerde pionnen
    private int waardegeselecteerdePion; // Lijst van geselecteerde pionnen
    private int aantalOver;
    private ArrayList<JButton> pionButtons; // List of buttons for each piece
    private String test = "";

    public spelerPionnen(int size) {
        pionnen = new ArrayList<>();
        pionButtons = new ArrayList<>();
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
        voegPionnenToe("Marshal", 10, 1);
        voegPionnenToe("General", 9, 1 );
        voegPionnenToe("Colonel", 8, 2 );
        voegPionnenToe("Major", 7, 3 );
        voegPionnenToe("Captain", 6, 4);
        voegPionnenToe("Lieutenant", 5, 4);
        voegPionnenToe("Sergeant", 4, 4);
        voegPionnenToe("Miner", 3, 5);
        voegPionnenToe("Scout", 2, 8);
        voegPionnenToe("Spy", 1, 1);
        voegPionnenToe("Bomb", 11, 6);
        voegPionnenToe("Flag", 0, 1);
        }
        if(size == 8){
            voegPionnenToe("Marshal", 10, 1);
            voegPionnenToe("General", 9, 1);
            voegPionnenToe("Miner", 3, 2);
            voegPionnenToe("Scout", 2, 2);
            voegPionnenToe("Spy", 1, 1);
            voegPionnenToe("Bomb", 11, 2);
            voegPionnenToe("Flag", 0, 1);
            }
        }

    private void voegPionnenToe(String naam, int waarde, int aantal) {
        for (int i = 0; i < aantal; i++) {
            pionnen.add(new Pion(naam, waarde,aantal));
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
        pionButtons.add(button);

        // Kleur instellen
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PLAYER3: " + this);

                if (huidigeKnop != null && huidigeKnop != button) {
                    // Deselecteer vorige knop
                    huidigeKnop.setBackground(Color.WHITE);
                }

                if (button == huidigeKnop) {
                    // Klik nogmaals: deselecteer knop
                    huidigeKnop.setBackground(Color.WHITE);
                    huidigeKnop = null;
                    System.out.println("Button selected: " + naam);
                    geselecteerdePion = null;
                    waardegeselecteerdePion = 13;
                } else {
                    // Selecteer knop
                    if (aantalOver > 0) {
                        button.setBackground(Color.ORANGE);
                        huidigeKnop = button;
                        
                     
                    // System.out.println("Before assignment, geselecteerdePion: " + geselecteerdePion);
                    setgeselecteerdepion(naam);
                    //geselecteerdepion = naam
                    // System.out.println("Afther assignment, geselecteerdePion: " + geselecteerdePion);
                        System.out.println(getgeselecteerdepion());

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
       
        geselecteerdePion = pion;
    

    }

    public String getgeselecteerdepion() {
        System.out.println("PLAYER2: " + this);
        // System.out.println("test = " + test);
        // if (geselecteerdePion.isEmpty()) {
        //     System.out.println("No pion selected yet.");
        //     return "";
        // }
        // geselecteerdePion = naam; // Or handle appropriately
        // System.out.println("spelerPIONNEN GESELECTEERDE PION TEST = " + geselecteerdePion);
        System.out.println(geselecteerdePion);
        return geselecteerdePion;
    }
    
    public int getPionwaarde(){
        return waardegeselecteerdePion;
    } 
    public ArrayList<Pion> getPionnen(){
        return pionnen;
    } 

    public JPanel getPionPanel() {
        return pionpanel;
    }
    public Pion getSelectedPionObject() {
        for (Pion pion : pionnen) { // for all pion in pionnen
            if (pion.getNaam().equals(geselecteerdePion)) { //if the name saved on "geselecteerdePion is listed in the pionnenlist"
                // System.out.println("PIONNEN ZIJN GELIJK");
                return pion;
            }
            
            
        }
        return null; // Return null if no matching pion is found
    }

    public ArrayList<Pion> getPionnenlijst(){

             return pionnen;
    }
    
    public void decreasePieceCount(String pionNaam) {
        for (JButton button :pionButtons) {
            if (button.getText().contains(pionNaam)) {
                // Extract the count from the button text
                int waarde;
                String[] parts = button.getText().split(" ");
                int count = Integer.parseInt(parts[parts.length - 1].replace("(", "").replace(")", ""));
                if (parts.length > 2) {
                     waarde = Integer.parseInt(parts[1]);
                }
                else{waarde = 13;}
                // Decrease the count
                
                count--;
    
                // Update the button text
                if (count > 0) {
                    button.setText((waarde > 0 && waarde < 11)
                    ? (pionNaam + " " + waarde + " (" + count + ")")
                    : (pionNaam + " (" + count + ")"));
                } else {
                    button.setText((waarde > 0 && waarde < 11)
                    ? (pionNaam + " " + waarde + " (" + count + ")")
                    : (pionNaam + " (" + count + ")"));
                    button.setEnabled(false); // Disable the button if count reaches 0
                    button.setBackground(Color.WHITE);
                    waardegeselecteerdePion = 13;
                    geselecteerdePion = null;
                }
                    int newCount = pionTelling.get(pionNaam) - 1;
                        pionTelling.put(pionNaam, newCount);
                break;
            }
        }
    }
    public void addpionpanal(JButton button){
        pionpanel.add(button);
    }
    public boolean availablepieces() {
        for (int count : pionTelling.values()) {
            if (count > 0) {
                return true; // there are stil available pieces
            }
        }
        return false; // no available pieces
    }
    
}