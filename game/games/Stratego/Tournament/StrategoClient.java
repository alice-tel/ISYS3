package game.games.Stratego.Tournament;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.games.Stratego.StrategoGame;
import game.games.Stratego.AI.*;

public class StrategoClient implements Runnable {

//    private String hostName = "127.0.0.1";
    private String hostName = "172.201.112.199";
    private int portNumber = 7789;

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    private boolean placed = false;
    private boolean active = false;

    private String[][] board = new String[8][8]; // 8x8 board
    private StrategoGame game;

    public StrategoClient() {
        game = new StrategoGame(8, true, false);
        board = game.getSpeler1();
        game.startBattlephase();
    }

    @Override
    public void run() {
        try {
            client = new Socket(hostName, portNumber);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // Get the user's name
            String userName = getUserName();

            out.println("login " + userName);
            out.println("subscribe stratego");

            String inputMessage;
            while ((inputMessage = in.readLine()) != null) {
                if (inputMessage.contains("MATCH")) {
                    placed = false;
                    active = true;
                } else if (inputMessage.contains("LOSS") || inputMessage.contains("WIN")) {
                    active = false;
                    // After the game ends, resubscribe
                    System.out.println("Game ended, resubscribing...");
                    out.println("subscribe stratego");
                } else if (inputMessage.contains("Opponent Placed")) {
                    enemypiece(extractNumber(inputMessage));
                }
                else if (inputMessage.contains("YOURTURN") && active) {
                    if (!placed) {
                        placed = true;
                        placePieces();
                    } else {
                        if (game.getcurrentPlayer() == 2) {
                            game.switchPlayer();}
                        calculateAndMakeMove(game.getMove());
                        }
                    }
                    System.out.println(inputMessage);
                }
            }
            catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int extractNumber(String inputMessage) {
        if (inputMessage == null) {
            return -1; // Foutwaarde als de input null is
        }

        Pattern pattern = Pattern.compile("\\d+"); // Zoek naar een getal
        Matcher matcher = pattern.matcher(inputMessage);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group()); // Converteer naar int
        }

        return -1; // Geen nummer gevonden
    }

    private String getUserName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }


    private void placePieces() {
        List<String> pieces = new ArrayList<>(Arrays.asList(
                "Marshal", "General", "Miner", "Miner", "Scout", "Scout", "Spy", "Bomb", "Bomb", "Flag"
        ));

        Collections.shuffle(pieces);  // Randomize the piece list

        // Now place the pieces at random positions on the bottom three rows
        int[] positions = getRandomPositions(pieces.size());



        for (int i = 0; i < pieces.size(); i++) {
            placePiece(positions[i], pieces.get(i));
            placePiecelocal(positions[i], pieces.get(i));
        }

    }

private void enemypiece(int index) {
    int col = index % 8;
    int row = (index - col) / 8;
    
    game.setspeler1color(row, col);
    board = game.getSpeler1();
    }

    private void placePiecelocal(int index, String piece) {
        
        int col = index % 8;
        int row = (index - col) / 8;
        
        game.setspeler1(row, col, piece);
        board = game.getSpeler1();

    }

    private int[] getRandomPositions(int count) {
        Set<Integer> positions = new HashSet<>();
        Random rand = new Random();
        while (positions.size() < count) {
            int row = rand.nextInt(3) + 5;  // Random row between 5 and 7
            int col = rand.nextInt(8);      // Random column between 0 and 7
            int position = row * 8 + col;   // Convert (row, col) to a 1D position
            positions.add(position);
        }
        return positions.stream().mapToInt(Integer::intValue).toArray();
    }


    private void placePiece(int index, String piece) {



        out.println("place " + index + " " + piece);
    }

    private void calculateAndMakeMove(Move move) {
        // Generate all valid moves for the current board
        int[] start = move.getFrom();
        int[] end = move.getTo();
       
        int from = start[0] * 8 + start[1];
        int to = end[0] * 8 + end[1];

        // Send the move to the server
        out.println("move " + from + " " + to);

    }
 


    public void shutdown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class InputHandler implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inputReader.readLine();
                    if (message.equalsIgnoreCase("/quit")) {
                        inputReader.close();
                        shutdown();
                    } else {
                        out.println(message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        StrategoClient client = new StrategoClient();
        client.run();
    }
}
