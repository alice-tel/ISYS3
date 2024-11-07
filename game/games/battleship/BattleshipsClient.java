package game.games.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class BattleshipsClient implements Runnable {

    private String hostName = "127.0.0.1";
    private int portNumber = 7789;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private char[] board; // 3x3 board
    private String playerName;
    private char playerSymbol; // 'X' or 'O'
    private char opponentSymbol; // 'X' or 'O'

    public static void main(String[] args) {
        BattleshipsClient client = new BattleshipsClient();
        client.run();
    }

    public BattleshipsClient(String playerName) {
        this.playerName = playerName;
        board = new char[9]; // Initialize empty board
    }

    @Override
    public void run() {
        try {
            client = new Socket(hostName, portNumber);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // Prompt for a valid player name
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your player name (alphanumeric, max 16 characters, can't start with a number): ");
            playerName = consoleReader.readLine();

            // Validate player name
            while (!isValidName(playerName)) {
                System.out.print("Invalid name! Please enter a valid player name: ");
                playerName = consoleReader.readLine();
            }

            // Log in as the player
            out.println("login " + playerName);
            waitForLoginResponse();

            // Subscribe to the game
            out.println("subscribe battleships"); // Updated game name

            String inputMessage;
            while ((inputMessage = in.readLine()) != null) {
                System.out.println(inputMessage);
                handleServerMessage(inputMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            shutdown();
        }
    }

    private boolean isValidName(String name) {
        // Check name constraints: alphanumeric, not starting with a number, max 16 characters
        return name.matches("^[a-zA-Z][a-zA-Z0-9]{0,15}$");
    }

    private void waitForLoginResponse() throws IOException {
        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);
            if (response.startsWith("OK")) {
                System.out.println("Login successful.");
                break; // Login successful
            } else if (response.startsWith("ERR")) {
                System.out.println("Error: " + response);
                System.out.print("Enter a new player name: ");
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                playerName = consoleReader.readLine();
                out.println("login " + playerName); // Try logging in again
            }
        }
    }

    private void handleServerMessage(String message) {

        // @TODO this is where the logic goes for handling the game in server
        
        if (message.startsWith("SVR GAME MATCH")) {
            System.out.println("Game match found! Starting the game...");
            playerSymbol = 'X'; // Assuming player is X
            opponentSymbol = 'O'; // Set the opponent's symbol
            board = new char[9]; // Reset the board for a new game
            printBoard();
        } else if (message.startsWith("SVR GAME YOURTURN")) {
            System.out.println("It's your turn! The computer will now make a move.");
            makeComputerMove(); // Computer makes a move
        } else if (message.startsWith("SVR GAME LOSS")) {
            System.out.println("You lost the game!");
            printBoard();
            shutdown();
        } else if (message.startsWith("SVR GAME DRAW")) {
            System.out.println("The game is a draw!");
            printBoard();
            shutdown();
        } else if (message.startsWith("SVR GAME MOVE")) {
            try {
                // Extract the move using regex
                String movePattern = "MOVE: \"(\\d+)\"";
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(movePattern);
                java.util.regex.Matcher matcher = pattern.matcher(message);

                if (matcher.find()) {
                    int move = Integer.parseInt(matcher.group(1)); // Get the move from the regex matcher

                    // Update the board with the opponent's move if the spot is available
                    if (move >= 0 && move < 9) {
                        if (board[move] == '\0') { // Check if the spot is available
                            board[move] = opponentSymbol; // Update the board with opponent's move
                        } else {
                            System.out.println("Received an invalid move from the server: " + move);
                        }
                    } else {
                        System.out.println("Move out of bounds: " + move);
                    }
                } else {
                    System.out.println("Unexpected message format for MOVE: " + message);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid move format received from the server.");
            }

            printBoard(); // Print the updated board after the move
        }
    }



    private void makeComputerMove() {
        // Find the first available spot on the board
        for (int i = 0; i < board.length; i++) {
            if (board[i] == '\0') {
                board[i] = playerSymbol; // Player symbol for computer
                out.println("move " + i); // Send move to the server
                printBoard(); // Print the board after making a move
                return;
            }
        }
        System.out.println("No valid moves available!");
    }


    private void printBoard() {
        // print board logic here
    }

    public void shutdown() {
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

    public BattleshipsClient() {
        board = new char[9]; // Initialize empty board
    }
}
