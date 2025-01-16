import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game {

    private int currentPlayer;
    private ArrayList<Player> players;
    JPanel board = Setup.getBoard();

    public Game(ArrayList<Player> players) {
        this.players = players;
        currentPlayer = 0;
        startGame();
    }

    private void startGame() {
        Setup.main(null);
        board = Setup.getBoard();
        for (Component component : board.getComponents()) {
            if (component instanceof JButton button) {
                button.setVisible(true); button.setEnabled(true);
                if (button.getBackground()== Color.CYAN) {button.setEnabled(false);}
                if (button.getText() != null && Integer.parseInt(button.getActionCommand())<50 && button.getBackground()!= Color.CYAN) {button.setActionCommand("0");}
                if (button.getText()!= null && Integer.parseInt(button.getActionCommand())>=50 && button.getBackground()!= Color.CYAN) {button.setActionCommand("1");}
            }
        }
        playGame();
    }

    private void playTurn() {
        //Hide opposing values
        if (currentPlayer == 0) {
            for (Component component : board.getComponents()) {
                if (component instanceof JButton button) {
                    if (button.getActionCommand().equals("1")) {
                        button.setActionCommand(button.getText());
                        button.setText("1");
                        button.setBackground(Color.RED);
                        button.setForeground(button.getBackground());
                    }
                }
            }
        }
        if (currentPlayer == 1) {
            for (Component component : board.getComponents()) {
                if (component instanceof JButton button) {
                    if (button.getActionCommand().equals("0")) {
                        button.setActionCommand(button.getText());
                        button.setText("0");
                        button.setBackground(Color.BLUE);
                        button.setForeground(button.getBackground());
                    }
                }
            }
        }
    }

    //Check if player has flag after opposing move
    private boolean isGameOver() {
        for (Component component : board.getComponents()) {
            if (component instanceof JButton button) {
                if (button.getText()!= null && !button.getText().equals(String.valueOf(currentPlayer)) && button.getActionCommand().equals("Flag")) {return false;}
            }
        }
        return true;
    }

    public void playGame(){
        while (!isGameOver()) {
            playTurn();
        }
    }

}