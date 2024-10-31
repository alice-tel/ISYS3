package game.games.pesten;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void drawCard(Deck deck) {
        hand.add(deck.draw());
    }

    public boolean playCard(Card card, Card topCard) {
        if (card.getSuit().equals(topCard.getSuit()) || card.getValue().equals(topCard.getValue())) {
            hand.remove(card);
            return true;
        }
        return false;
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }

    @Override
    public String toString() {
        return name + " has " + hand.size() + " cards.";
    }
}
