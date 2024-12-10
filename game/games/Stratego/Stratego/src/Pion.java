public class Pion {
    private String naam;
    private int waarde;
    private IMoveStrategy moveStrategy;
    private IAttackStrategy attackStrategy;

    public Pion(String naam, int waarde, IMoveStrategy moveStrategy, IAttackStrategy attackStrategy){
        this.naam = naam;
        this.waarde = waarde;
        this.moveStrategy = moveStrategy;
        this.attackStrategy = attackStrategy;
    }
    public void getMove(){
        moveStrategy.move();
    }
    public void getAttack(){
        attackStrategy.attack();
    }
    public void setMoveStrategy(IMoveStrategy moveStrategy){
        this.moveStrategy = moveStrategy;
    }
    public void setAttackStrategy(IAttackStrategy attackStrategy){
        this.attackStrategy = attackStrategy;
    }
}
