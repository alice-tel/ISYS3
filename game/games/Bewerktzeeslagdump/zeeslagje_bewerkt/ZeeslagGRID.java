import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class ZeeslagGRID {

    public static final int MINUTE_PER_HOUR = 60; //dit is hoe je een constante maakt
    int boardWidth = 800;
    int boardHeight = 850;

    JFrame frame = new JFrame("Zeeslag");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[8][8];

    ZeeslagGRID() {
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Zeeslag");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setPreferredSize(new Dimension(100,100));
                board[r][c] = tile;
                boardPanel.add(tile);
            }
        }
        frame.pack();
    }

//    public static void main(String[] args) {   //Use this to see what the field looks like
//        new ZeeslagGRID();
//    }

}