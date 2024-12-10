package game.games.Stratego;

import game.framework.GameFramework;

public class StrategoGame extends GameFramework{
    public StrategoGame(int size){
        super(size,size, 1600, 900,"Hier moeten de spelregels vaan Stratego komen");
        setVisible(true);
    }

    @Override
    protected String getGameName() {
        return "Stratego";
    }

    @Override
    protected void onGridButtonClicked(int row, int col) {
        return;
    }
}
