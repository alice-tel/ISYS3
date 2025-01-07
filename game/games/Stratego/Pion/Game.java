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
        pionnenSpeler2 = new spelerPionnen(size);
        boolean isBuildingPhase = true;
    }

    public spelerPionnen getPionnenspeler1(){
        System.out.println("SPELER1 GETTER INTERACTIE");
        System.out.println(pionnenSpeler1.getgeselecteerdepion() + "speler1geselecteerde");
        return pionnenSpeler1;
    }

    public String getSpeler1geselecteerdewaarde(){
        return getPionnenspeler1().getgeselecteerdepion();
    }

    //todo: huilen
    //-
}

