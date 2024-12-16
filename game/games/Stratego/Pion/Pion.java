package game.games.Stratego.Pion;


<<<<<<< Updated upstream
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

=======
public class Pion {
    private String naam;
    private int waarde;
    private IMoveStrategy moveStrategy;
    private IAttackStrategy attackStrategy;
    private int player; // 1 for Player 1, 2 for Player 2

    public Pion(String naam, int waarde, IMoveStrategy moveStrategy, IAttackStrategy attackStrategy, int player) {
        this.naam = naam;
        this.waarde = waarde;
        this.moveStrategy = moveStrategy;
        this.attackStrategy = attackStrategy;
        this.player = player;
    }

    public void getMove() {
        moveStrategy.move();
    }

    // Updated method to perform an attack
    public Pion getAttack(Pion defender) {
        return attackStrategy.attack(this, defender);
    }

    public void setMoveStrategy(IMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void setAttackStrategy(IAttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    public int getWaarde() {
        return waarde;
    }

    public String getNaam() {
        return naam;
    }

    public int getPlayer() {
        return player;
    }
}
>>>>>>> Stashed changes
