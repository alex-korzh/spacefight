package name.dezalator.ui;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GamePanel gamePanel;

    public GameFrame() {
        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("SpaceFight");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
