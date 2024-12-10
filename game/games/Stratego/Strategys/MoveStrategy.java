package game.games.Stratego.Strategys;

public interface MoveStrategy {
    boolean calculateMove(PionStrategy attacker, PionStrategy defender);
}
