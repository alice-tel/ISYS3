package game.games.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BattleshipsClient implements Runnable {

//    private String hostName = "127.0.0.1"; // Local server
     private String hostName = "172.201.112.199"; // Tournament server
    private int portNumber = 7789;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private String playerName;
    private Random random = new Random();

    // Ship lengths
    private int[] shipLengths = {6, 4, 3, 2};
    private boolean[] occupied = new boolean[64]; // 8x8 grid
    private boolean[] shotsFired = new boolean[64]; // Track previous shots
    private Set<Integer> placedShipLengths = new HashSet<>(); // Tracks placed ship lengths
    private boolean isYourTurn = false; // Flag to check if it's the player's turn
    private Set<Integer> shipPositions = new HashSet<>(); // Tracks all ship positions

    public static void main(String[] args) {
        System.out.print("Enter your player name: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String playerName = null;
        try {
            playerName = reader.readLine();
            if (playerName == null || playerName.trim().isEmpty()) {
                System.out.println("Invalid player name.");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        BattleshipsClient client = new BattleshipsClient(playerName);
        client.run();
    }

    public BattleshipsClient(String playerName) {
        this.playerName = playerName.toLowerCase();
    }

    @Override
    public void run() {
        try {
            client = new Socket(hostName, portNumber);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // Log in
            out.println("login " + playerName);
            String response = in.readLine();
            if (response.startsWith("ERR")) {
                System.out.println("Error during login: " + response);
                return;
            }
            System.out.println("Logged in as " + playerName);

            // Subscribe to Battleship game
            out.println("subscribe battleship");

            // Handle server messages
            String message;
            while ((message = in.readLine()) != null) {
                if (done) break;
                handleServerMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
            //shutdown();
        }
    }

    private void resetGameState() {
        occupied = new boolean[64];
        shotsFired = new boolean[64];
        placedShipLengths.clear();
        shipPositions.clear();
        isYourTurn = false;
    }

    private void handleServerMessage(String message) throws IOException {
        if (message.startsWith("SVR GAME MATCH")) {
            System.out.println("Game match found!");
        } else if (message.startsWith("SVR GAME YOURTURN")) {
            isYourTurn = true;
            if (placedShipLengths.isEmpty()) {
                System.out.println("Placing ships...");
                placeShips();  // Place ships if not already done
                return;
            }
            makeMove();  // Proceed to make a move after placing ships
        } else if (message.startsWith("SVR GAME MOVE")) {
            handleMoveResult(message);  // Handle move result
        } else if (message.startsWith("SVR GAME SINK")) {
            handleSinkMessage(message);  // Handle ship sink message
        } else if (message.startsWith("SVR GAME WIN") || message.startsWith("SVR GAME LOSS") || message.startsWith("SVR GAME DRAW")) {
            System.out.println("Game Over: " + message);  // Game result
            resetGameState();
            out.println("subscribe battleship");
            //shutdown();
        } else if (message.startsWith("OK")) {
            // Command accepted
        } else if (message.startsWith("ERR")) {
            System.out.println("Error from server: " + message);
            handleServerError(message);
        } else if (message.startsWith("SVR GAME CHALLENGE")) {
            System.out.println("Challenge received: " + message);
            out.println("challenge accept");  // Automatically accept challenges
        }
    }

    private void placeShips() {
        // Ensure ships are placed only once
        if (placedShipLengths.size() > 0) {
            System.out.println("Ships already placed.");
            return;
        }

        // Example placement of ships using the corrected format
        if (placeShip(6, 24, 29)) {  // Placing a ship of length 6 horizontally
            placedShipLengths.add(6);
        }
        if (placeShip(4, 56, 59)) {  // Placing a ship of length 4 horizontally
            placedShipLengths.add(4);
        }
        if (placeShip(3, 44, 46)) {  // Placing a ship of length 3 horizontally
            placedShipLengths.add(3);
        }
        if (placeShip(2, 0, 1)) {    // Placing a ship of length 2 horizontally
            placedShipLengths.add(2);
        }

        // After placing ships, check if all ships are placed and send a "ready" or "start" command
        if (placedShipLengths.size() == shipLengths.length) {
            //out.println("ready"); // Inform server that the ships are placed and player is ready to start
            System.out.println("All ships placed, waiting for the match to begin...");
        }
    }

    private boolean placeShip(int length, int start, int end) {
        // Ensure that start < end and they are within valid range
        if (start < 0 || start >= 64 || end < 0 || end >= 64 || end - start + 1 != length) {
            return false;
        }

        // Ensure no overlapping ships
        for (int i = start; i <= end; i++) {
            if (occupied[i]) {
                return false;
            }
        }

        // Place the ship
        for (int i = start; i <= end; i++) {
            occupied[i] = true;
            shipPositions.add(i); // Track each position occupied by a ship
        }

        // Format the placement command as "place [startIndex,endIndex]"
        String placeCommand = "place " + start + " " + end;
        System.out.println("Issuing place command: " + placeCommand);  // Debug print
        out.println(placeCommand);  // Send the placement command to the server

        return true;
    }


    private boolean canPlaceShip(int start, int direction, int shipLength) {
        if (start < 0 || start >= 64) return false;

        for (int i = 0; i < shipLength; i++) {
            int index = (direction == 0) ? start + i : start + i * 8;
            if (index < 0 || index >= 64 || occupied[index]) return false;
        }

        return true;
    }

    private void makeMove() {
        if (placedShipLengths.size() < shipLengths.length) {
            return;
        }

        if (!isYourTurn) {
            return;
        }

        // Use minimax to calculate the best move
        int bestMove = minimax(3); // Search depth of 3 (can be adjusted)
        shotsFired[bestMove] = true;

        // Send the move command to the server
        out.println("move " + bestMove);

        isYourTurn = false;
    }

    private int minimax(int depth) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 64; i++) {
            if (!shotsFired[i]) {
                // Try this move
                shotsFired[i] = true;
                int score = minimaxEvaluate(depth - 1, false);
                shotsFired[i] = false;

                // Update the best score and best move
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }

    private int minimaxEvaluate(int depth, boolean maximizingPlayer) {
        // Base case: if we've reached the maximum depth, evaluate the board
        if (depth == 0) {
            return evaluateBoard();
        }

        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Evaluate all possible moves
        for (int i = 0; i < 64; i++) {
            if (!shotsFired[i]) {
                shotsFired[i] = true;
                int score = minimaxEvaluate(depth - 1, !maximizingPlayer);
                shotsFired[i] = false;

                if (maximizingPlayer) {
                    bestScore = Math.max(bestScore, score);
                } else {
                    bestScore = Math.min(bestScore, score);
                }
            }
        }

        return bestScore;
    }

    private int evaluateBoard() {
        // Basic heuristic evaluation: prioritize hitting areas where ships are most likely to be
        // For simplicity, prioritize areas around previous hits or known ship locations
        int score = 0;

        // Example scoring logic (can be adjusted):
        // - +10 for shooting near previously hit spots
        // - -10 for shooting near missed spots
        // - +5 for shooting at empty but strategic locations (e.g., near board edges)
        for (int i = 0; i < 64; i++) {
            if (shotsFired[i]) {
                // Reward hits
                if (shotsFired[i] && isHit(i)) {
                    score += 10;
                }
                // Penalize misses
                else if (shotsFired[i] && !isHit(i)) {
                    score -= 5;
                }
            }
        }

        return score;
    }

    private boolean isHit(int index) {
        return false; // Returns true if the position is a hit
    }

    private void handleMoveResult(String message) {

        String player = extractValue(message, "PLAYER");
        String move = extractValue(message, "MOVE");
        String result = extractValue(message, "RESULT");

        System.out.println("Move by player " + player + " at position " + move + " resulted in: " + result);
        if ("BOEM".equals(result)) {
            System.out.println("It's a hit!");
        } else if ("PLONS".equals(result)) {
            System.out.println("It's a miss!");
        }
    }

    private void handleSinkMessage(String message) {
        String shipOwner = extractValue(message, "SHIP_OWNER");
        String length = extractValue(message, "LENGTH");

        System.out.println("Ship owned by " + shipOwner + " of length " + length + " has been sunk!");
    }

    private String extractValue(String message, String key) {
        int start = message.indexOf(key + ": \"") + key.length() + 3;
        int end = message.indexOf("\"", start);
        return message.substring(start, end);
    }

    private void handleServerError(String message) {
                if (message.contains("Invalid ship placement")) {
            System.out.println("Retrying ship placement...");
            placeShips();
        } else {
            System.out.println("Unrecoverable error. Disconnecting...");
//            out.println("subscribe battleship");
            //shutdown();
        }
    }

    private void shutdown() {
        done = true;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (client != null && !client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
