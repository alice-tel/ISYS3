package game.games.Stratego.Pion;
import java.util.ArrayList;

public class Player {
    private String name;
    ArrayList<Pion> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>(); //hand = je pionnen
    }
    public boolean hasLost() {
        //Flag on board
        return false;
    }
    public String toString() {
        return name + " has " + hand;
    }

}