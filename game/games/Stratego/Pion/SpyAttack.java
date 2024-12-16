package game.games.Stratego.Pion;

import game.games.Stratego.Strategys.IAttackStrategy;

public class SpyAttack implements IAttackStrategy{
    private Pion attacker;
    private Pion defender;



    @Override
    public Pion attack(Pion attacker, Pion defender) {
        if (defender.getNaam().equals("Marshal")) {
            return attacker; // Spy wins against Marshal
        }
        return defender; // Spy loses to other pieces
    }
}

