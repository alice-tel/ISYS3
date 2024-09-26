import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    Dictionary<String, String> gameBoard = new Hashtable<>(); // For game logic
    boolean gameOver = false;

    TicTacToe() {
        // Initialize the game board (logic part)
        initializeGameBoard();

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
        textLabel.setText("Tic-Tac-Toe: Player " + currentPlayer);
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
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                String position = getPositionString(r, c);
                JButton tile = new JButton();
                tile.setFont(new Font("Arial", Font.BOLD, 100));
                tile.setForeground(Color.lightGray);
                tile.setBackground(Color.darkGray);
                tile.setFocusPainted(false);
                board[r][c] = tile;
                boardPanel.add(tile);

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

    // Initialize the logic game board
    private void initializeGameBoard() {
        String[] letters = {"A", "B", "C"};
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                String position = letters[i] + x; // A0, A1, etc.
                gameBoard.put(position, ""); // Initialize board positions
            }
        }
    }

    // Switch players
    private void switchPlayer() {
        if (currentPlayer.equals(playerX)) {
            currentPlayer = playerO;
        } else {
            currentPlayer = playerX;
        }
    }

    // Convert row and column indices to board position string (e.g., "A0", "B1")
    private String getPositionString(int row, int col) {
        String[] letters = {"A", "B", "C"};
        return letters[row] + col;
    }

    // Check if the game is over
    private String checkGameOver() {
        String[] letters = {"A", "B", "C"};
        String xWins = "XXX";
        String oWins = "OOO";

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            String row = "", col = "";
            for (int j = 0; j < 3; j++) {
                row += gameBoard.get(letters[i] + j); // Check row
                col += gameBoard.get(letters[j] + i); // Check column
            }

            if (row.equals(xWins)) {
                highlightRow(i);
                return "Player X Wins!";
            }

            if (row.equals(oWins)) {
                highlightRow(i);
                return "Player O Wins!";
            }

            if (col.equals(xWins)) {
                highlightColumn(i);
                return "Player X Wins!";
            }

            if (col.equals(oWins)) {
                highlightColumn(i);
                return "Player O Wins!";
            }

        }
        // Check diagonals
        String diagonal1 = gameBoard.get("A0") + gameBoard.get("B1") + gameBoard.get("C2");
        String diagonal2 = gameBoard.get("A2") + gameBoard.get("B1") + gameBoard.get("C0");

        if (diagonal1.equals(xWins)) {
            highlightDiag1();
            return "Player X Wins!";
        }

        if (diagonal1.equals(oWins)) {
            highlightDiag1();
            return "Player 0 Wins!";
        }

        if (diagonal2.equals(xWins)) {
            highlightDiag2();
            return "Player X Wins!";
        }

        if (diagonal2.equals(oWins)) {
            highlightDiag2();
            return "Player O Wins!";
        }

        // Check for draw
        boolean boardFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard.get(letters[i] + j).equals("")) {
                    boardFull = false;
                    break;
                }
            }
        }
        if (boardFull) return "It's a Draw!";

        // If no winner and the board isn't full yet
        return "No winner";
    }

    private void highlightRow(int row) {
        for (int c = 0; c < 3; c++) {
            board[row][c].setForeground(Color.green);
        }
    }

    private void highlightColumn(int col) {
        for (int r = 0; r < 3; r++) {
            board[r][col].setForeground(Color.green);
        }
    }

    private void highlightDiag1() {
        board[0][0].setForeground(Color.green);
        board[1][1].setForeground(Color.green);
        board[2][2].setForeground(Color.green);
    }

    private void highlightDiag2() {
        board[0][2].setForeground(Color.green);
        board[1][1].setForeground(Color.green);
        board[2][0].setForeground(Color.green);
    }
}