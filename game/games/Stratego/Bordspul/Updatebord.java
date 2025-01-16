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
                
                if (cell.equals("Water")) {
                    StrategoGame.Setgridbutton(row, col, new Color(0, 191, 255));
                } 
                if (cell.equals("Blue")) {
                    StrategoGame.Setgridbutton(row, col, Color.BLUE);
        
                   
                    String textWithoutBlue = cell.substring("Blue".length()).trim();
                    
                    // Zet de tekst op de knop
                    StrategoGame.setbuttonstext(row, col, textWithoutBlue);
                }
                else if (cell.contains("Blue")) {
                    StrategoGame.Setgridbutton(row, col, Color.BLUE);
        
                   
                    String textWithoutBlue = cell.substring("Blue".length()).trim();
                    String[] parts = cell.split(" ");
                    int waarde = Integer.parseInt(parts[2]);
                    if (waarde > 1 || waarde < 11) {
                        textWithoutBlue = parts[1];
                    }
                    // Zet de tekst op de knop
                    StrategoGame.setbuttonstext(row, col, textWithoutBlue);
                }
                if (cell.equals("Red")) {
                    StrategoGame.Setgridbutton(row, col, Color.RED);
                    String textWithoutRed = cell.substring("Red".length()).trim();
                    StrategoGame.setbuttonstext(row, col, textWithoutRed);
                }
                else if (cell.contains("Red")) {
                    StrategoGame.Setgridbutton(row, col, Color.RED);
                   
                    String textWithoutRed = cell.substring("Red".length()).trim();
                    String[] parts = cell.split(" ");
                    int waarde = Integer.parseInt(parts[2]);
                    if (waarde > 1 || waarde < 11) {
                        textWithoutRed = parts[1];
                    }
                    // Zet de tekst op de knop
                    StrategoGame.setbuttonstext(row, col, textWithoutRed);
                } 
                if (cell.equals("-")) {
                    StrategoGame.Setgridbutton(row, col, new Color(0, 128, 0));
                    StrategoGame.setbuttonstext(row, col, "");
                }
            }
        }
    }
}
