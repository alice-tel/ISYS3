package game.games.Stratego.Pion;
import java.lang.reflect.Array;
import java.util.Scanner;

public class Game {
    private int currentPlayer;
    private Scanner scanner;
    private spelerPionnen pionnenSpeler1;
    private spelerPionnen pionnenSpeler2;
    private int size;

    public Game(int size){
        this.size = size;
        startRegularGame();
    }

    public void startRegularGame() {
        pionnenSpeler1 = new spelerPionnen(size);
        // pionnenSpeler2 = new spelerPionnen(size); //this is turned off at the moment because we were testing if player1 worked
        boolean isBuildingPhase = true; //Use this for onbuttongridclicked building and defending phase.
    }

    public spelerPionnen getPionnenspeler1(){
        return pionnenSpeler1; //create a link to all methods assosiated with Speler1
    }

    public String getSpeler1geselecteerdewaarde(){
        return getPionnenspeler1().getgeselecteerdepion(); //From Speler1, getselected pion (the side bar where u can click to select a type of pion) this bar is player dedicated so you have to use this
    }

    public spelerPionnen getPionnenspeler2(){
        // System.out.println(pionnenSpeler2.getgeselecteerdepion() + " = speler1geselecteerde");
        return pionnenSpeler2; //create a link to all methods assosiated with Speler1
    }

    public String getSpeler2geselecteerdewaarde(){
        return getPionnenspeler2().getgeselecteerdepion();
    }

    public Pion getSpeler1SelectedPionObject(){
        return getPionnenspeler1().getSelectedPionObject(); //from player1, get the selected pion
    }
    public Pion getSpeler2SelectedPionObject(){
        return getPionnenspeler2().getSelectedPionObject();//from player2, get the selected pion
    }
}

