package game.games.Stratego.Pion;

import game.games.Stratego.Strategys.IAttackStrategy;

public class Attack implements IAttackStrategy {
    private Pion attacker;
    private Pion defender;



    @Override
    public Pion attack(Pion attacker,Pion defender) {
        if (attacker.getWaarde() > defender.getWaarde()) {
            System.out.println(attacker.getNaam() + " wins against " + defender.getNaam());
            return attacker; // Attacker wins
        } else if (attacker.getWaarde() < defender.getWaarde()) {
            System.out.println(defender.getNaam() + " wins against " + attacker.getNaam());
            return defender; // Defender wins
        } else {
            System.out.println("Both " + attacker.getNaam() + " and " + defender.getNaam() + " are removed.");
            return null; // Both lose in a tie scenario
        }
    }
}
