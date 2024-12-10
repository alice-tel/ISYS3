package game.games.Stratego;

import game.framework.GameFramework;

public class StrategoGame extends GameFramework{
    public StrategoGame(int size){
        super(size,size, 1600, 900,"hoi");
        setVisible(true);
    }

    @Override
    protected String getGameName() {
        return "Stratego";
    }

    @Override
    protected void onGridButtonClicked(int row, int col) {
        // TODO Auto-generated method stub
        return;
    }
}
