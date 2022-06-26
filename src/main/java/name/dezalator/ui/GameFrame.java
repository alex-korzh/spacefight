package name.dezalator.ui;

import javax.swing.*;

public class GameFrame extends JFrame {
    public static final int SCREEN_HEIGHT = 1200;
    public static final int SCREEN_WIDTH = 1200;
    public GameFieldPanel gameFieldPanel;

    public GameFrame() {
        gameFieldPanel = new GameFieldPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.add(gameFieldPanel);
        this.setTitle("SpaceFight");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
