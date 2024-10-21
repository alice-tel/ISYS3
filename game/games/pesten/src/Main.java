import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        players.add(new Player("Player 3"));

        Game game = new Game(players);
        game.playGame();
    }
}
