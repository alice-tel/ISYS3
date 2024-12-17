package game.games.Stratego.Bordspul;

import java.awt.Color;
import game.games.Stratego.StrategoGame;

public class Updatebord {

    private char[][] bord; // Changed to a String[][] array

    public Updatebord(char[][] bord) {
        this.bord = bord;
    }

    public void updateBoardUI() {
        for (int row = 0; row < bord.length; row++) { // Iterate over rows
            for (int col = 0; col < bord[row].length; col++) { // Iterate over columns
                char temp = bord[row][col];
                String cell = String.valueOf(temp); // Current cell value
                
                if (cell.contains("S")) {
                    StrategoGame.Setgridbutton(row, col, Color.GRAY);
                } 
                if (cell.contains("R")) {
                    StrategoGame.Setgridbutton(row, col, Color.RED);
                } 
                if (cell.contains("B")) {
                    StrategoGame.Setgridbutton(row, col, Color.BLUE);
                } 
                if (cell.contains("-")) {
                    StrategoGame.Setgridbutton(row, col, Color.GRAY);
                }
            }
        }
    }
}
