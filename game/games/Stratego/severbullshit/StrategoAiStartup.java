package game.games.Stratego.severbullshit;
import game.games.Stratego.Pion.Pion;
import game.games.Stratego.Pion.spelerPionnen;
import java.util.ArrayList;
import java.util.Scanner;

public class StrategoAiStartup {
    //this method will be called in StrategoClient.
   private ArrayList<Pion> aiPionnenlijst;
   private int setupChoice;
   private String[][] aiBoard;

        //make piece
        public StrategoAiStartup(int size) {
            this.aiBoard = new String[size][size];
            spelerPionnen spelerPionneLijst = new spelerPionnen(size);
            this.aiPionnenlijst = spelerPionneLijst.getPionnenlijst(); //Make u able to get all pions
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    aiBoard[row][col] = "-";
                }
            }

//            for (Pion pion : this.aiPionnenlijst){                                                        //test if pionnen are in the list
//               System.out.println(pion); // Loop to test if able to see pieces (we are stupid)
//           }
            Scanner scanner = new Scanner(System.in);
            System.out.println("What way would you like to fight? (1, 2 or 3)");
            setupChoice = scanner.nextInt();
            //Place Pions with method
            clearBoard();
            PlacePions(setupChoice, aiPionnenlijst);
        }

        // Switch cases for choosing 
        private void PlacePions(int setupChoice, ArrayList<Pion> aiPionnenlijst) {
            switch(setupChoice) {
                case 1: // Aggressive Setup
                    System.out.println("setupChoice is aggressive");
                    setupAgggressiveBoard();
                    break;
                case 2: // Defensive Setup
                    System.out.println("setupChoice is defensive");
                    setupDefensiveBoard();
                    break;
                case 3: // All-round setup
                    System.out.println("setupChoice is all-rounder");
                    setupAllRounderBoard();
                    break;
                default:
                    System.out.print("error invalid choice");
                    break;
            }
    }


    // Create all setups and place them on board
    private void setupAllRounderBoard() {
        clearBoard();
            // clear board method
            for(Pion pion : aiPionnenlijst){
                String pionnenNaam = pion.getNaam();

                switch (pionnenNaam) {
                    case "General":
                        aiBoard[6][1] = pionnenNaam;
                        break;
                    case "Miner":
                        aiBoard[6][4] = pionnenNaam;
                        aiBoard[6][5] = pionnenNaam;
                        break;
                    case "Marshal":
                        aiBoard[6][0] = pionnenNaam;
                        break;
                    case "Scout":
                        aiBoard[6][2] = pionnenNaam;
                        aiBoard[6][3] = pionnenNaam;
                        break;
                    case "Spy":
                        aiBoard[6][6] = pionnenNaam;
                        break;
                    case "Bomb":
                        aiBoard[6][7] = pionnenNaam;
                        aiBoard[7][0] = pionnenNaam;
                        break;
                    case "Flag":
                        aiBoard[7][7] = pionnenNaam;
                        break;
                }
            }
    }

    private void setupDefensiveBoard() {
        clearBoard();
        for(Pion pion : aiPionnenlijst){
            String pionnenNaam = pion.getNaam();

            switch (pionnenNaam) {
                case "General":
                    aiBoard[6][6] = pionnenNaam;
                    break;
                case "Miner":
                    aiBoard[6][3] = pionnenNaam;
                    aiBoard[6][4] = pionnenNaam;
                    break;
                case "Marshal":
                    aiBoard[6][5] = pionnenNaam;
                    break;
                case "Scout":
                    aiBoard[6][7] = pionnenNaam;
                    aiBoard[7][7] = pionnenNaam;
                    break;
                case "Spy":
                    aiBoard[6][2] = pionnenNaam;
//                    aiBoard[7][5] = "boardClearCheck";
                    break;
                case "Bomb":
                    aiBoard[6][0] = pionnenNaam;
                    aiBoard[7][0] = pionnenNaam;
                    break;
                case "Flag":
                    aiBoard[7][1] = pionnenNaam;
                    break;
            }
        }
    }

    private void setupAgggressiveBoard() {
            clearBoard();
        for(Pion pion : aiPionnenlijst){
            String pionnenNaam = pion.getNaam();

            switch (pionnenNaam) {
                case "General":
                    aiBoard[6][1] = pionnenNaam;
                    break;
                case "Miner":
                    aiBoard[6][4] = pionnenNaam;
                    aiBoard[6][5] = pionnenNaam;
                    break;
                case "Marshal":
                    aiBoard[6][0] = pionnenNaam;
                    break;
                case "Scout":
                    aiBoard[6][2] = pionnenNaam;
                    aiBoard[6][3] = pionnenNaam;
                    break;
                case "Spy":
                    aiBoard[6][6] = pionnenNaam;
                    break;
                case "Bomb":
                    aiBoard[6][7] = pionnenNaam;
                    aiBoard[7][0] = pionnenNaam;
                    break;
                case "Flag":
                    aiBoard[7][7] = pionnenNaam;
                    break;
            }
        }
    }

    private void clearBoard() {
        System.out.println("Stratego Board:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                aiBoard[i][j] = "-";
            }
            System.out.println();
        }
    }

    private void printBoard() {
        System.out.println("Stratego Board:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print((" " + aiBoard[i][j] + " "));
            }
            System.out.println();
        }
    }
    //make board
        //put pions on the board
        // return

    public static void main(String[] args){
           StrategoAiStartup ai = new StrategoAiStartup(8);
           ai.printBoard();
    }
}
