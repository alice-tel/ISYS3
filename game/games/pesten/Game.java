package game.games.pesten;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private ArrayList<Player> players;
    private Card topCard;
    private int currentPlayer;
    private Scanner scanner;

    public Game(ArrayList<Player> players) {
        deck = new Deck();
        this.players = players;
        currentPlayer = 0;
        scanner = new Scanner(System.in);
        startGame();
    }

    public void startGame() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCard(deck);
            }
        }
        topCard = deck.draw();
        System.out.println("Starting card: " + topCard);
    }

    public Deck getDeck() {
        return deck;
    }

    public Card getTopCard() {
        return topCard;
    }

    private void displayHand(Player player) {
        System.out.println(player + "'s hand:");
        for (int i = 0; i < player.getHand().size(); i++) {
            System.out.println((i + 1) + ": " + player.getHand().get(i));
        }
    }

    private void nextTurn() {
        Player player = players.get(currentPlayer);
        System.out.println("\n" + player + "'s turn.");
        System.out.println("Top card: " + topCard);

        displayHand(player);

        System.out.println("Choose a card to play (1-" + player.getHand().size() + ") or type 0 to draw a card:");
        int choice = scanner.nextInt();

        if (choice == 0) {
            player.drawCard(deck);
            System.out.println(player + " drew a card.");
        } else if (choice > 0 && choice <= player.getHand().size()) {
            Card chosenCard = player.getHand().get(choice - 1);
            if (player.playCard(chosenCard, topCard)) {
                topCard = chosenCard;
                System.out.println(player + " played " + chosenCard);
            } else {
                System.out.println("Invalid card choice! You can't play that card.");
                player.drawCard(deck);
                System.out.println(player + " drew a card.");
            }
        } else {
            System.out.println("Invalid choice! Drawing a card instead.");
            player.drawCard(deck);
        }

        if (player.hasWon()) {
            System.out.println(player + " has won the game!");
            System.exit(0);
        }

        currentPlayer = (currentPlayer + 1) % players.size();
    }

    public void playGame() {
        while (true) {
            nextTurn();
        }
    }
}
