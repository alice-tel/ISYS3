package game.games.Stratego;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StrategoClient implements Runnable {

    private String hostName = "127.0.0.1"; // Local server
//    private String hostName = "172.201.112.199"; // Tournament server
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
        game.games.tictactoe.TicTacToeClient client = new game.games.tictactoe.TicTacToeClient();
        client.run();
    }

    public StrategoClient() {
        this.playerName = playerName;
        board = new char[10];
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
            out.println("subscribe othello"); // Updated game name

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
            //shutdown();
        } else if (message.startsWith("SVR GAME DRAW")) {
            System.out.println("The game is a draw!");
            printBoard();
            //shutdown();
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

    private void makeComputerMove() {}

    private void printBoard() {}

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

}
