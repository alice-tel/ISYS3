import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class ZeeSlag {
    int boardWidth = 1600; //200 per square so 200 * 8
    int boardHeight = 1728;   //original was 650 + 3 = 216 * 8 = 1728

    JFrame frame = new JFrame("Zeeslag");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[8][8];
    String player1 = "Player 1";
    String player0 = "Player 2";
    String currentPlayer = player1;

    Dictionary<String, String> gameBoard = new Hashtable<>(); // For game logic
    boolean gameOver = false;

    ZeeSlag() {
        // Initialize the game board (logic part)
        initializeGameBoard();
        Startupphase();

        // Setup the frame
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Setup the text label
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Zeeslag: Player " + currentPlayer);
        textLabel.setOpaque(true);

        // Setup the text panel
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Setup the board panel
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        // Create buttons for each cell in the 3x3 grid
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                String position = getPositionString(r, c);
                JButton tile = new JButton();
                tile.setFont(new Font("Arial", Font.BOLD, 100));
                tile.setForeground(Color.white);
                tile.setBackground(Color.darkGray);
                tile.setFocusPainted(false);
                board[r][c] = tile;
                boardPanel.add(tile);


                //todo this below
                // Add action listener to each tile
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!gameOver && tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            gameBoard.put(position, currentPlayer); // Update logic board

                            // Check for game over
                            String result = checkGameOver();
                            if (!result.equals("No winner")) {
                                gameOver = true;
                                textLabel.setText(result);
                            } else {
                                switchPlayer(); // Switch to the other player
                                textLabel.setText("Tic-Tac-Toe: Player " + currentPlayer);
                            }
                        }
                    }
                });
            }
        }
    }


    //Gamefunctions

    private void initializeGameBoard() {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0; i < 8; i++) {
            for (int x = 0; x < 8; x++) {
                String position = letters[i] + x; // A0, A1, etc.
                gameBoard.put(position, ""); // Initialize board positions
            }
        }
    }

    private void Startupphase() {
        Scanner scanner = new Scanner(System.in);
        int[] schiplengtes = {1, 2, 3, 4};
        System.out.print("Oke " + currentPlayer + "waar wil je je schepen hebben?");

        for (int schiplengte : schiplengtes) {
            boolean KanPlaatsen = false;

            while (!KanPlaatsen) {
                System.out.print("Schip lengte " + schiplengte + " invoeren (bijv. 'A4' voor een schip met een lengte van 1 of 'A2 A4' voor lengte 3)");
                String input = scanner.nextLine().toUpperCase(); // anders gaat ie stom doen met kleine sh

                String[] positions = input.split(" ");
                if (positions.length == 1 && schiplengte == 1) {
                    String start = positions[0];
                    KanPlaatsen = placeShip(start, start, schiplengte);
                } else if (positions.length == 2 && schiplengte > 1) {
                   //plaats schip met een lengte van 1
                    String start = positions[0];
                    String end = positions[1];
                    KanPlaatsen = placeShip(start, end, schiplengte);
                }

                if (!KanPlaatsen) {
                    System.out.println("Ongeldige invoer. Probeer het opnieuw.");
                }
            }
        }
    }

    private boolean placeShip(String start, String end, int length) {
        // Convert start and end to coordinates
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int startRow = Arrays.asList(letters).indexOf(start.substring(0, 1)); //Dit Stukje is chatgpt ( tot de volgende // genaamd chatgptstop. Heb dit tijdelijk gedaan want ik snap String sh nog niet.
        int startCol = Integer.parseInt(start.substring(1)) - 1;

        int endRow = Arrays.asList(letters).indexOf(end.substring(0, 1));
        int endCol = Integer.parseInt(end.substring(1)) - 1;

        // Check if the ship is placed in a valid line
        if (startRow != endRow && startCol != endCol) {
            return false; // Ship must be placed in a straight line
        }

        // Calculate the distance between start and end
        int rowDistance = Math.abs(endRow - startRow) + 1;
        int colDistance = Math.abs(endCol - startCol) + 1;

        if (rowDistance != length && colDistance != length) {
            return false; // The length must match the expected length
        }

        // Place the ship
        if (startRow == endRow) {
            for (int col = Math.min(startCol, endCol); col <= Math.max(startCol, endCol); col++) {
                String position = letters[startRow] + (col + 1);
                if (!gameBoard.get(position).equals("")) {
                    return false; // Position already occupied
                }
                gameBoard.put(position, currentPlayer);
            }
        } else {
            for (int row = Math.min(startRow, endRow); row <= Math.max(startRow, endRow); row++) {
                String position = letters[row] + (startCol + 1);
                if (!gameBoard.get(position).equals("")) {
                    return false; // Position already occupied
                }
                gameBoard.put(position, currentPlayer);
            }
        }

        return true;                //chatgptstop
    }
}


    private void switchPlayer() {
        //moet nog wat bedenken
    }
private String getPositionString(int r, int c) {
    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
    return letters[r] + (c + 1);
}

private void PlayGame() {
    //Hier moeten we dus ook echt de game starten, dat ze om ste beurt kunnen schieten enzo
}





