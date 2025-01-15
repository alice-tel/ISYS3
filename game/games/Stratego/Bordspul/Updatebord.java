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
                if (cell.contains("Q")) {
                    StrategoGame.Setgridbutton(row, col, Color.BLUE);
        
                    // Haal de tekst na "Q" op
                    String textWithoutQ = cell.substring(cell.indexOf("Q") + 1).trim();
        
                    // Zet de tekst op de knop
                    StrategoGame.setbuttonstext(row, col, textWithoutQ);
                }
                if (cell.contains("Z")) {
                    StrategoGame.Setgridbutton(row, col, Color.RED);
                    // Haal de tekst na "Q" op
                    String textWithoutQ = cell.substring(cell.indexOf("Z") + 1).trim();
        
                    // Zet de tekst op de knop
                    StrategoGame.setbuttonstext(row, col, textWithoutQ);
                } 
                if (cell.equals("-")) {
                    StrategoGame.Setgridbutton(row, col, new Color(0, 128, 0));
                    StrategoGame.setbuttonstext(row, col, "");
                }
            }
        }
    }
}
