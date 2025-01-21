package game.games.Stratego.Tournament;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class StrategoClient implements Runnable {

    private String hostName = "127.0.0.1";
    private int portNumber = 7789;

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    private boolean placed = false;
    private boolean active = false;

    private String[][] board = new String[8][8]; // 8x8 board
    private final int MAX_DEPTH = 3; // Depth for Expectiminimax algorithm

    @Override
    public void run() {
        try {
            client = new Socket(hostName, portNumber);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // Get the user's name
            String userName = getUserName();

            out.println("login " + userName + LocalDateTime.now().getSecond() + LocalDateTime.now().getNano());
            out.println("subscribe stratego");

            String inputMessage;
            while ((inputMessage = in.readLine()) != null) {
                if (inputMessage.contains("MATCH")) {
                    placed = false;
                    active = true;
                    initializeBoard();
                } else if (inputMessage.contains("LOSS") || inputMessage.contains("WIN")) {
                    active = false;
                    // After the game ends, resubscribe
                    System.out.println("Game ended, resubscribing...");
                    out.println("subscribe stratego");
                } else if (inputMessage.contains("YOURTURN") && active) {
                    if (!placed) {
                        placed = true;
                        placePieces();
                    } else {
                        calculateAndMakeMove();
                    }
                }
                System.out.println(inputMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUserName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }
    
    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = "."; // Empty cell
            }
        }
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
        }

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

    private void calculateAndMakeMove() {
        // Generate all valid moves for the current board
        List<int[]> validMoves = generateValidMoves(board);

        if (validMoves.isEmpty()) {
            System.out.println("No valid moves available.");
            return;
        }

        // Pick a random valid move
        int[] randomMove = validMoves.get(new Random().nextInt(validMoves.size()));
        int from = randomMove[0];
        int to = randomMove[1];

        int fromIndex = from;
        int toIndex = to;

        int fromRow = from / 8, fromCol = from % 8;
        int toRow = to / 8, toCol = to % 8;

        String movingPiece = board[fromRow][fromCol];
        board[fromRow][fromCol] = "."; // Empty the source cell
        board[toRow][toCol] = movingPiece; // Place the piece in the destination cell

        // Send the move to the server
        out.println("move " + fromIndex + " " + toIndex);

    }

    private List<int[]> generateValidMoves(String[][] currentBoard) {
        List<int[]> validMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (!currentBoard[row][col].equals(".")) { // Ignore empty spaces
                    validMoves.addAll(getValidMovesForPiece(row, col, currentBoard[row][col], currentBoard));
                }
            }
        }
        return validMoves;
    }

    private List<int[]> getValidMovesForPiece(int row, int col, String piece, String[][] board) {
        List<int[]> moves = new ArrayList<>();

        // Move directions: up, down, left, right
        int[] directions = {-1, 1}; // Up and Down for vertical, Left and Right for horizontal

        if (piece.equals("Scout")) {
            // Scout can move any number of spaces in any direction (without jumping over other pieces)
            for (int direction : directions) {
                for (int i = 1; i < 8; i++) { // Moving multiple steps
                    // Vertical moves
                    if (row + direction * i >= 0 && row + direction * i < 8 && board[row + direction * i][col].equals(".")) {
                        moves.add(new int[]{row * 8 + col, (row + direction * i) * 8 + col});
                    } else break; // Stop if obstacle is encountered
                    // Horizontal moves
                    if (col + direction * i >= 0 && col + direction * i < 8 && board[row][col + direction * i].equals(".")) {
                        moves.add(new int[]{row * 8 + col, row * 8 + (col + direction * i)});
                    } else break; // Stop if obstacle is encountered
                }
            }
        } else if (piece.equals("Miner") || piece.equals("Spy")) {
            // Miner can move 1 space in any direction
            for (int direction : directions) {
                // Vertical moves
                if (row + direction >= 0 && row + direction < 8 && board[row + direction][col].equals(".")) {
                    moves.add(new int[]{row * 8 + col, (row + direction) * 8 + col});
                }
                // Horizontal moves
                if (col + direction >= 0 && col + direction < 8 && board[row][col + direction].equals(".")) {
                    moves.add(new int[]{row * 8 + col, row * 8 + (col + direction)});
                }
            }
        }

        // Additional logic for other pieces like Bomb, Flag, still needs to be added, I think. I don't know if this is necessary or if I'm overcomplicating it.
        return moves;
    }

    private String[][] simulateMove(String[][] currentBoard, int from, int to) {
        String[][] newBoard = new String[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(currentBoard[i], 0, newBoard[i], 0, 8);
        }
        int fromRow = from / 8, fromCol = from % 8;
        int toRow = to / 8, toCol = to % 8;

        newBoard[toRow][toCol] = newBoard[fromRow][fromCol];
        newBoard[fromRow][fromCol] = ".";
        return newBoard;
    }

    private int evaluateBoard(String[][] board) {
        // Basic evaluation of pieces
        int score = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String piece = board[row][col];
                if (piece.equals("Marshal")) score += 10;
                else if (piece.equals("General")) score += 8;
                else if (piece.equals("Flag")) score -= 50; // Flag loss
            }
        }
        return score;
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
