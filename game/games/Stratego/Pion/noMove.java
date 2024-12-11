package game.games.Stratego.Pion;

import game.games.Stratego.Strategys.IMoveStrategy;

public class noMove implements IMoveStrategy{
    public void move() {System.out.println("This piece can't move");}
}
