package game.games.Stratego.Pion;


import game.games.Stratego.Strategys.MoveStrategy;
import game.games.Stratego.Strategys.PionStrategy;

public class Scout implements PionStrategy {
    private MoveStrategy moveStrategy; // Field to store the strategy

    @Override
    public int getMoveDistance() {
        return 9; // Scouts can move 9 spaces
    }

    @Override
    public int getHealth() {
        return 2; // Scouts have a health value of 9
    }

    @Override
    public MoveStrategy getMoveStrategy() {
        return moveStrategy; // Return the current strategy
    }

    @Override
    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy; // Assign a new strategy
    }
}

