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
        // pionnenSpeler2 = new spelerPionnen(size);
        boolean isBuildingPhase = true;
    }

    public spelerPionnen getPionnenspeler1(){
        System.out.println(pionnenSpeler1.getgeselecteerdepion() + " = speler1geselecteerde");
        return pionnenSpeler1;
    }

    public String getSpeler1geselecteerdewaarde(){
        return getPionnenspeler1().getgeselecteerdepion();
    }

    public spelerPionnen getPionnenspeler2(){
        System.out.println(pionnenSpeler2.getgeselecteerdepion() + " = speler1geselecteerde");
        return pionnenSpeler2;
    }

    public String getSpeler2geselecteerdewaarde(){
        return getPionnenspeler2().getgeselecteerdepion();
    }

    public Pion getSpeler1SelectedPionObject(){
        return getPionnenspeler1().getSelectedPionObject();
    }
    public Pion getSpeler2SelectedPionObject(){
        return getPionnenspeler2().getSelectedPionObject();
    }


    //todo: huilen
    //-
}

