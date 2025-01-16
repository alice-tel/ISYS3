import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Setup {

    private static JButton currentButtonSide;
    private static boolean noButtons = false;
    private static boolean setupDone = false;
    private static int setupTracker = 0;
    private static int player = 0;
    private static JPanel boardPanel;

    public static void main(String[] args) {
        // Create main board
        boardPanel = new JPanel(new GridLayout(10, 10));
        JFrame boardFrame = new JFrame("Board");
        JLabel boardLabel = new JLabel("Board");
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.setSize(800, 800);
        boardFrame.setVisible(true);

        //Create side panel
        JPanel sidePanel = new JPanel(new GridLayout(4, 10));
        JFrame sideFrame = new JFrame("Side");
        JLabel sideLabel = new JLabel("Side");
        sideFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sideFrame.setSize(600, 400);
        sideFrame.setVisible(true);

        //Create and retrieve list of Pionnen
        new spelerPionnen();
        ArrayList<Pion> pionnen = spelerPionnen.getPionnen();
        ArrayList<JButton> buttonMain = new ArrayList<>();
        ArrayList<JButton> buttonSide = new ArrayList<>();

        //Fill main board with buttonSide
        for (int i = 0; i < 100; i++) {
            JButton button = new JButton();
            button.setActionCommand(String.valueOf(i));
            button.setText(null);
            if (i < 50) {button.setVisible(false);}
            if (i == 42 || i == 43 || i == 46 || i == 47 || i == 52 || i == 53 || i == 56 || i == 57) {
                button.setBackground(Color.CYAN);
                button.setVisible(true);
                button.setEnabled(false);
            }
            button.setPreferredSize(new Dimension(8, 8));
            boardPanel.add(button);
            buttonMain.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Retrieve and cast the clicked button
                    JButton clickedButton = (JButton) e.getSource();
                    //If main board button is clicked before a pion is selected show message
                    if (currentButtonSide == null) {JOptionPane.showMessageDialog(boardFrame, "Select Pion to place");}
                    //If button is already taken
                    else if (clickedButton.getText() != null) {JOptionPane.showMessageDialog(boardFrame, "This is already occupied, you can't place a troop here");}
                    //Place sidePanel button on mainBoard and remove from sidePanel
                    else {
                        //Fill mainBoard
                        clickedButton.setText(currentButtonSide.getText());
                        for (JButton sideButton : buttonSide) {
                            if (Objects.equals(sideButton.getActionCommand(), currentButtonSide.getActionCommand())) {
                                sidePanel.remove(sideButton);
                                sidePanel.validate();
                                sidePanel.repaint();
                                //Is sidePanel empty
                                if (sidePanel.getComponents().length == 0){noButtons = true; buttonSide.clear(); setupTracker += 1;}
                                break;
                            }
                        }
                        currentButtonSide = null;
                    }
                }
            });
        }
        boardFrame.add(boardPanel);

        //Fill side panel with Pionnen
        int counter = 0;
        for (Pion pion : pionnen) {
            String naam = pion.getNaam();
            JButton button = new JButton(naam);
            buttonSide.add(button);
            button.setActionCommand(String.valueOf(counter));
            sidePanel.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //If there is already a selected button, deselect old button
                    if (currentButtonSide != null) {
                        currentButtonSide.setBackground(null);
                    }
                    currentButtonSide = new JButton();
                    currentButtonSide = (JButton) e.getSource();
                    button.setBackground(Color.RED);
                }
            });
            counter++;
        }
        sideFrame.add(sidePanel);

        //Check if player 1 is done placing
        while (!noButtons) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Refill sidePanel and recolor mainBoard for player two
        player = 1;
        int counter2 = 0;
        for (Pion pion : pionnen) {
            String naam = pion.getNaam();
            JButton button = new JButton(naam);
            buttonSide.add(button);
            button.setActionCommand(String.valueOf(counter2));
            sidePanel.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //If there is already a selected button, deselect old button
                    if (currentButtonSide != null) {
                        currentButtonSide.setBackground(null);
                    }
                    currentButtonSide = new JButton();
                    currentButtonSide = (JButton) e.getSource();
                    button.setBackground(Color.RED);
                }
            });
            counter2++;
        }
        sideFrame.add(sidePanel);
        sideFrame.repaint();
        sideFrame.validate();

        for (JButton button : buttonMain) {
            int i = Integer.parseInt(button.getActionCommand());
            if (i > 49) {button.setVisible(false);}
            if (i == 42 || i == 43 || i == 46 || i == 47 || i == 52 || i == 53 || i == 56 || i == 57) {
                button.setBackground(Color.CYAN);
                button.setVisible(true);
                button.setEnabled(false);
            }
            if (i < 50) {button.setVisible(true);}
        }
        while (setupTracker != 2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(noButtons){
            setupDone = true;}
    }

    public static boolean getSetupDone() {return setupDone;}

    public static JPanel getBoard() {return boardPanel;}
}