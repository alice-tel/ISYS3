package game.games.Stratego.Bordspul;

import java.awt.Color;
import game.games.Stratego.StrategoGame;

public class Updatebord {

    private String[][] bord; // Changed to a String[][] array

    public Updatebord(String[][] bord) {
        this.bord = bord;
        updateBoardUI();
    }

    public void updateBoardUI() {
        for (int row = 0; row < bord.length; row++) { // Iterate over rows
            for (int col = 0; col < bord[row].length; col++) { // Iterate over columns
                // char temp = bord[row][col];
                String cell = bord[row][col]; // Current cell value
                
                if (cell.equals("X")) {
                    StrategoGame.Setgridbutton(row, col, new Color(0, 191, 255));
                } 
                if (cell.contains("R")) {
                    StrategoGame.setBordercolor(row, col, Color.RED);
                } 
                if (cell.contains("B")) {
                    StrategoGame.setBordercolor(row, col, Color.BLUE);
                } 
                if (cell.equals("-")) {
                    StrategoGame.Setgridbutton(row, col, new Color(0, 128, 0));
                }
            }
        }
    }
}
