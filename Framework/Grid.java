package Framework;

import javax.swing.*;

public class Grid {

    static int width;
    static int height;
    static JFrame frame;
    static String frameName;
    static JLabel textLabel;
    static JPanel textPanel;
    static JPanel boardPanel;

    public Grid (String frameName, int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JLabel createLabel(String name) {
        return textLabel = new JLabel(name);
    }

    public JPanel createPanel(String name) {
        return textPanel = new JPanel();
    }

}
