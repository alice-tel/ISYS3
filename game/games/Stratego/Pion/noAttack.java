package game.games.Stratego.Pion;

import game.games.Stratego.Strategys.IAttackStrategy;

public class noAttack implements IAttackStrategy {
    private Pion attacker;
    private Pion defender;



    @Override
    public Pion attack(Pion attacker, Pion defender) {
        return null;
    }
}
