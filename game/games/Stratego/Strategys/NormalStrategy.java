package game.games.Stratego.Strategys;


public class NormalStrategy implements MoveStrategy {
    @Override
    public boolean calculateMove(PionStrategy attacker, PionStrategy defender) {
        if (attacker.getHealth() > defender.getHealth()) {
            return true; // Attacker wins
        } else if (attacker.getHealth() < defender.getHealth()) {
            return false; // Defender wins
        } else {
            return false; // Both lose (tie scenario)
        }
    }
}

