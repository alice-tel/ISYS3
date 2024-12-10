package game.games.Stratego.Pion;


import game.games.Stratego.Strategys.MoveStrategy;
import game.games.Stratego.Strategys.PionStrategy;

public class Pion implements PionStrategy {
    private int moveDistance;
    private int health;
    private MoveStrategy moveStrategy;

    public Pion(int moveDistance, int health, MoveStrategy moveStrategy) {
        this.moveDistance = moveDistance;
        this.health = health;
        this.moveStrategy = moveStrategy;
    }

    @Override
    public int getMoveDistance() {
        return moveDistance;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    @Override
    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
}

