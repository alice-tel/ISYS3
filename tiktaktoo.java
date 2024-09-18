import java.util.Scanner;
import java.util.Dictionary;
import java.util.Hashtable;

public class tiktaktoo {

    public static Dictionary<String, String> makeBoard() {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        Scanner myObj = new Scanner(System.in);
        System.out.println("Hoe groot moet het bord? (Max 10)");
        int size = myObj.nextInt();
        Dictionary<String, String> board = new Hashtable<>();
        for(int i = 0; i < size ; i++) {
            for(int x = 0; x < size ; x++) {
                String input = letters[i]+x;
                board.put(input, "");
            }
        }
        return board;
    }

    public static Dictionary<String, String> fillBoard(Dictionary<String, String> board) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welke speler is (1 of 2)?");
        String stringPlayer = scanner.nextLine();
        int player = stringPlayer.charAt(0);
        System.out.println("Wat speel je (Bijv. A0)?");
        String input = scanner.nextLine();
        if (player == 1) {
            board.put(input, "X");
        }
        if (player == 2) {
            board.put(input, "O");
        }
        return board;
    }

    public static String isGameOver(Dictionary<String, String> board) {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int boardsize = (int) Math.sqrt(board.size());
        int inARow = boardsize;
        if(inARow >= 4) {
            inARow = 4;
        }
        String xWins = "X".repeat(inARow);
        String oWins = "O".repeat(inARow);
        String diagonalinput = "";
        for(int i = 0; i < boardsize; i++) {
            String letter = letters[i];
            String input = "";
            String inputnumber = "";
            String letterdiagonal = letters[i];
            String inputdiagonal = "";
            String coordsdiagonal = letterdiagonal + i;
            inputdiagonal += board.get(coordsdiagonal);
            String diagonalcoords = letters[boardsize-i];
            String diagonalcoordsnumber = diagonalcoords+i;
            diagonalinput += board.get(diagonalcoordsnumber);
            for(int x = 0; x < boardsize; x++) {
                String coords = letter + x;
                input += board.get(coords);
                String letternumber = letters[x];
                String coordsnumber = letternumber + i;
                inputnumber += board.get(coordsnumber);
            }
            if(input.indexOf(xWins) != -1 || inputnumber.indexOf(xWins) != -1 || inputdiagonal.indexOf(xWins) != -1 || diagonalinput.indexOf(xWins) != -1) {
                return "Speler 1 wint";
            }
            if(input.indexOf(oWins) != -1 || inputnumber.indexOf(oWins) != -1 || inputdiagonal.indexOf(oWins) != -1 || diagonalinput.indexOf(oWins) != -1) {
                return "Speler 2 wint";
            }
        }
        // No winner
        return "No winner";
    }

    public static void main(String[] args){
        Dictionary<String, String> board = makeBoard();
        System.out.println(board);
        System.out.println(isGameOver(board));
        while (isGameOver(board).equals("No winner")) {
            fillBoard(board);
            System.out.println(board);
        }
}

}