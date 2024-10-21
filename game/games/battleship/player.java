package game.games.battleship;

public class player {
    private char[][] defendGrid;
    private char[][] attackGrid;
    private String playerName;

    public player(String playerName) {
        this.playerName = playerName;
        defendGrid = new char[8][8];
        attackGrid = new char[8][8];

        // Initialize grids with water ('-')
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                defendGrid[i][j] = '-';
                attackGrid[i][j] = '-';
            }
        }
    }

    public char[][] getDefendGrid() {
        return defendGrid;
    }

    public char[][] getAttackGrid() {
        return attackGrid;
    }

    public String getPlayerName() {
        return playerName;
    }
}
