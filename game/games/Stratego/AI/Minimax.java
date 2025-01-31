package game.games.Stratego.AI;

import java.util.*;

import game.games.Stratego.StrategoGame;

public class Minimax {
    


    private static final int MAX_DEPTH = 2; // Maximale diepte voor de zoekboom
    private static String color;
    private static String enemycolor;


    public static Move findBestMove(String[][] board, int currentPlayer) {
        if (currentPlayer == 1) {
            color = "Red";
            enemycolor = "Blue";
        } else {
            color = "Blue";
            enemycolor = "Red";
        }
        return expectiminimax(board, currentPlayer,MAX_DEPTH, true);
    }

    public static Move expectiminimax(String[][] board, int currentPlayer, int depth, boolean isMaximizingPlayer) {
        if (depth == 0) {
            // Bereken en retourneer de evaluatiewaarde van het bord
            return new Move(null, evaluateBoard(board, currentPlayer));
        }
    
        if (isMaximizingPlayer) {
            System.out.println("Maximizing player");
            Move bestMove = new Move(null, Integer.MIN_VALUE);
    
            for (Move move : generateValidMoves(board, currentPlayer)) {
                String[][] newBoard = applyMove(board, move); // Pas de zet toe
                Move result = expectiminimax(newBoard, currentPlayer, depth - 1, false); // Recursieve aanroep
    
                // Update de beste zet als deze beter is
                if (result.value > bestMove.value) {
                    bestMove = new Move(move.from, move.to);
                    bestMove.value = result.value;
                    System.out.println("best move changed");
                }
            }
            System.out.println("Best move for maximizing player: " + Arrays.toString(bestMove.getFrom()) + " " + Arrays.toString(bestMove.getTo() ));
            return bestMove;
    
        } else {
            System.out.println("Minimizing player");
            Move bestMove = new Move(null, Integer.MAX_VALUE);
    
            for (Move move : generateValidMoves(board, 3 - currentPlayer)) {
                String[][] newBoard = applyMove(board, move); // Pas de zet toe
                Move result = expectiminimax(newBoard, currentPlayer, depth - 1, true); // Recursieve aanroep
    
                // Update de beste zet als deze slechter is
                if (result.value < bestMove.value) {
                    bestMove = new Move(move.from, move.to);
                    bestMove.value = result.value;
                    System.out.println("best move changed");
                }
            }
            System.out.println("Best move for minimizing player: " + Arrays.toString(bestMove.getFrom()) + " " + Arrays.toString(bestMove.getTo() ));
            return bestMove;
        }
    }

    private static int evaluateBoard(String[][] board, int currentPlayer) {
        int score = 0;
    
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                String piece = board[i][j];
                if (piece != null && !piece.equals("Water") && !piece.equals("-")) {
                    int pieceValue = getPieceValue(piece);
                  score += pieceValue;
                }
            }
        }
    
        System.out.println("Evaluated board for player " + currentPlayer + ": " + score);
        return score;
    }
    

    private static List<Move> generateValidMoves(String[][] board, int currentPlayer) {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (getPlayer(board[i][j]) == currentPlayer) {
                    for (int[] direction : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                        int newX = i + direction[0];
                        int newY = j + direction[1];
                        System.out.println("Checking move: " + i + " " + j + " " + newX + " " + newY);
                        System.out.println(board[i][j]);
                        if (StrategoGame.isValidMove(i, j, newX, newY,board[i][j],currentPlayer, board, board.length )) {
                            System.out.println("Valid move found: " + i + " " + j + " " + newX + " " + newY);
                            moves.add(new Move(new int[]{i, j}, new int[]{newX, newY}));
                        }
                    }
                }
            }
        }
        return moves;
    }

    private static String[][] applyMove(String[][] board, Move move) {
        System.out.println("Applying move: " + move);
        String[][] newBoard = copyBoard(board);

        int[] from = move.from;
        int[] to = move.to;

        newBoard[to[0]][to[1]] = newBoard[from[0]][from[1]];
        newBoard[from[0]][from[1]] = null;

        return newBoard;
    }

    private static int getPieceValue(String piece) {

        if(piece.equals(enemycolor)){
            return -5;
        }
        String[] parts = piece.split(" ");
        if(parts.length < 3){
            return 0;
        }
        int intpiece = Integer.parseInt(parts[2]);
        if (piece.contains("Flag")){ intpiece = -50; // Flag loss
            }

        if (piece.contains(enemycolor)) {
            return -intpiece;
        }
        return intpiece;
        }
    

    private static int getPlayer(String piece) {
        if (piece == null || piece.equals("-") || piece.equals("Water")) return 0;
        return piece != null && piece.startsWith("B") ? 1 : 2;
    }

    private static String[][] copyBoard(String[][] board) {
        String[][] newBoard = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }
}

