package game.games.Stratego.Strategys;

public interface PionStrategy {
    public int getMoveDistance();
    public int getHealth();
    public MoveStrategy getMoveStrategy();
    public void setMoveStrategy(MoveStrategy moveStrategy);
}
