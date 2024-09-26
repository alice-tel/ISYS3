import java.awt.*;
import javax.swing.*;

public class ZeeslagGRID {
    int boardWidth = 1300;
    int boardHeight = 850;

    JFrame frame = new JFrame("Zeeslag");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel boardPanel2 = new JPanel();

    JButton[][] board = new JButton[8][16];

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

        boardPanel.setLayout(new GridLayout(8, 16));
        boardPanel.setForeground(Color.lightGray);
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.WEST);

        for (int r = 0; r < 8 ; r++) {
            for (int c = 0; c < 8; c++) {
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setPreferredSize(new Dimension(50,50));
                board[r][c] = tile;
                boardPanel.add(tile);
            }

            JButton button = new JButton();
            button.setBackground(Color.darkGray);
            button.setPreferredSize(new Dimension(50,850));
            button.setLabel("Next");

            for (int c = 0; c < 8; c++) {
                JButton tile = new JButton();
                tile.setBackground(Color.darkGray);
                tile.setPreferredSize(new Dimension(50,50));
                board[r][c] = tile;
                boardPanel.add(tile);
            }
        }
        frame.pack();
    }
}
