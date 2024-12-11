package game.games.Stratego.Pion;

import game.games.Stratego.Strategys.IAttackStrategy;

public class MinerAttack implements IAttackStrategy{
    @Override
    public void attack() {
        //Aside from its default attack, the miner is the only piece that can take down the bomb
    }
}