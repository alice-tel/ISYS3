package game.games.Stratego.Pion;
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
    }
}
