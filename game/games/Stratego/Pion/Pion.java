package game.games.Stratego.Pion;

import game.games.Stratego.Strategys.IAttackStrategy;
import game.games.Stratego.Strategys.IMoveStrategy;

public class Pion {
    private String naam;
    private int waarde;
    private IMoveStrategy moveStrategy;
    private IAttackStrategy attackStrategy;
    private int playerId;// 1 voor speler 1, 2 voor speler 2 (CHANGE)

    public Pion(String naam, int waarde, IMoveStrategy moveStrategy, IAttackStrategy attackStrategy){//Deze constructor is voor de buttons
        this.naam = naam;
        this.waarde = waarde;
        this.moveStrategy = moveStrategy;
        this.attackStrategy = attackStrategy;
    }
    public Pion(String naam, int waarde, IMoveStrategy moveStrategy, IAttackStrategy attackStrategy, int playerId) {//Deze constructor is voor de players zelf
        this.naam = naam;
        this.waarde = waarde;
        this.moveStrategy = moveStrategy;
        this.attackStrategy = attackStrategy;
        this.playerId = playerId;
    }

    // Getters and setters for playerId (if needed)
    

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public void getMove(){
        moveStrategy.move();
    }
    public int getPlayerId() { //gekke id getter
        return playerId;
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