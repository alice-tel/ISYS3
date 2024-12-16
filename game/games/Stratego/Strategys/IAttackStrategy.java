package game.games.Stratego.Strategys;


import game.games.Stratego.Pion.Pion;

public interface IAttackStrategy {
    public Pion attack(Pion attacker, Pion defender);

}
