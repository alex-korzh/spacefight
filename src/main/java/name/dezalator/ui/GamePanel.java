package name.dezalator.ui;

import name.dezalator.core.Engine;
import name.dezalator.core.util.Event;
import name.dezalator.model.util.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1200;
    static final int SCREEN_HEIGHT = 1200;
    static final int CELL_SIZE = 50;
    Coordinates hoveredCell;
    Coordinates previousHoveredCell;
    int turn;
    String currentPlayerName;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMovementListener());
        this.addKeyListener(new KAdapter());
        startGame();
    }

    private void startGame() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // GRID
        for (int i = 0; i < SCREEN_HEIGHT / CELL_SIZE; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * CELL_SIZE, SCREEN_WIDTH, i * CELL_SIZE);
        }

        // internal cell
        if (hoveredCell != null){
            g.setColor(Color.gray);
            g.drawRect(hoveredCell.x, hoveredCell.y, CELL_SIZE, CELL_SIZE);
            previousHoveredCell = hoveredCell;
        }

        // turn, player
        g.setColor(Color.white);
        g.setFont(new Font("Sans", Font.PLAIN, 15));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String message = "Player: " + currentPlayerName + "    Turn: " + turn;
        g.drawString(message, SCREEN_WIDTH - metrics.stringWidth(message), g.getFont().getSize());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    class MouseMovementListener extends MouseAdapter {
        public void mouseMoved(MouseEvent evt) {
            hoveredCell = getCellCoordinatesFromMouseCoordinates(evt.getX(), evt.getY());
            repaint(hoveredCell.x, hoveredCell.y, CELL_SIZE+1, CELL_SIZE+1);
            if (previousHoveredCell != null) {
                repaint(previousHoveredCell.x, previousHoveredCell.y, CELL_SIZE+1, CELL_SIZE+1);
            }
        }
    }

    public Coordinates getCellCoordinatesFromMouseCoordinates(int x, int y) {
        return new Coordinates(x/CELL_SIZE*CELL_SIZE, y/CELL_SIZE*CELL_SIZE);
    }

    public class KAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                endTurn();
            }
        }
    }

    public void endTurn() {
        Engine.notifyGame(Event.END_TURN);
        repaint();
    }

    public void updateTurn(int turn) {
        this.turn = turn;
    }

    public void updatePlayerName(String name) {
        currentPlayerName = name;
    }
}
