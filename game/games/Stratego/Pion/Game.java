package game.games.Stratego.Pion;
import java.util.Scanner;

public class Game {
    private int currentPlayer;
    private Scanner scanner;
    private spelerPionnen pionnenSpeler1;
    private spelerPionnen pionnenSpeler2;


    public Game(){
        startRegularGame();
    }

    public void startRegularGame() {
        pionnenSpeler1 = new spelerPionnen();
        pionnenSpeler2 = new spelerPionnen();
    }
}
