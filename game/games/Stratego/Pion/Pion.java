package game.games.Stratego.Pion;

import game.games.Stratego.Strategys.IAttackStrategy;
import game.games.Stratego.Strategys.IMoveStrategy;

public class Pion {
    private String naam;
    private int waarde;
    private int aantal;
    private IMoveStrategy moveStrategy;
    private IAttackStrategy attackStrategy;

    public Pion(String naam, int waarde,int aantal){
        this.naam = naam;
        this.waarde = waarde;
        this.aantal = aantal;
    }
    public int getWaarde(){
        return waarde;
    }
    public int getAantal(){
        return aantal;
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
    public String getNaam(){
        return naam;
    }
}