package name.dezalator.ui;

import name.dezalator.core.Engine;
import name.dezalator.core.Player;
import name.dezalator.core.util.Event;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1200;
    static final int SCREEN_HEIGHT = 1200;
    static final int CELL_SIZE = 50;
    Coordinates hoveredCell;
    Coordinates previousHoveredCell;
    int turn;
    UIPlayer player1;
    UIPlayer player2;
    UIPlayer currentPlayer;
    Coordinates selectedCell;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMovementListener());
        this.addMouseListener(new MListener());
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
            boolean found = false;
            for(UIShip ship: currentPlayer.getShips()) {
                if (ship.getCoordinates().equals(hoveredCell)) {
                    g.setColor(Color.green);
                    found = true;
                    break;
                }
            }
            if (!found) {
                g.setColor(Color.gray);
            }
            g.drawRect(hoveredCell.x, hoveredCell.y, CELL_SIZE, CELL_SIZE);
            previousHoveredCell = hoveredCell;
        }

        // turn, player
        g.setColor(Color.white);
        g.setFont(new Font("Sans", Font.PLAIN, 15));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String message = "Player: " + currentPlayer.getName() + "    Turn: " + turn;
        g.drawString(message, SCREEN_WIDTH - metrics.stringWidth(message), g.getFont().getSize());

        // ships
        drawShipsOfPlayer(g, player1);
        drawShipsOfPlayer(g, player2);

        // ship info

        drawShipInfoIfHoveredOrSelected(g, player1, hoveredCell);
        drawShipInfoIfHoveredOrSelected(g, player2, hoveredCell);
        drawShipInfoIfHoveredOrSelected(g, currentPlayer, selectedCell);

        // selected cell

        if (selectedCell != null) {
            boolean found = false;
            for(UIShip ship: currentPlayer.getShips()) {
                if (ship.getCoordinates().equals(selectedCell)) {
                    g.setColor(Color.green);
                    found = true;
                    break;
                }
            }
            if (found) {
                g.drawRect(selectedCell.x, selectedCell.y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawShipsOfPlayer(Graphics g, UIPlayer player) {
        for(UIShip ship: player.getShips()) {
            if (player == currentPlayer) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.red);
            }
            int radius = CELL_SIZE / 2;
            g.fillOval(ship.getCoordinates().x+radius/2, ship.getCoordinates().y+radius/2, radius, radius);
        }
    }

    private void drawShipInfoIfHoveredOrSelected(Graphics g, UIPlayer player, Coordinates mouseCoordinates) {
        String shipType = null;
        for(UIShip ship: player.getShips()) {
            if (ship.getCoordinates().equals(mouseCoordinates)) {
                shipType = ship.getType();
                break;
            }
        }
        if (shipType != null) {
            g.setColor(Color.white);
            g.setFont(new Font("Sans", Font.PLAIN, 15));
            String message = "Ship of type " + shipType + " of player: " + player.getName();
            g.drawString(message,  0, g.getFont().getSize());
            repaint();
        }
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

    class MListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Coordinates clickCoordinates = getCellCoordinatesFromMouseCoordinates(e.getX(), e.getY());
            if (clickCoordinates.equals(selectedCell)) {
                selectedCell = null;
                repaint(clickCoordinates.x, clickCoordinates.y, CELL_SIZE+1, CELL_SIZE+1);
            }
            else {
                selectedCell = clickCoordinates;
                repaint(clickCoordinates.x, clickCoordinates.y, CELL_SIZE+1, CELL_SIZE+1);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

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
        this.selectedCell = null;
        repaint();
    }

    public void updateTurn(int turn) {
        this.turn = turn;
    }

    public void providePlayers(Player player1, Player player2) {
        this.player1 = new UIPlayer(player1.getName(), updatePlayerShips(player1));
        this.player2 = new UIPlayer(player2.getName(), updatePlayerShips(player2));
    }

    private ArrayList<UIShip> updatePlayerShips(Player player) {
        ArrayList<UIShip> playerShips = new ArrayList<>();
        for(SpaceShip ship: player.getShips()) {
            Coordinates coordinates = ship.getCoordinates();
            coordinates.x = coordinates.x * CELL_SIZE;
            coordinates.y = coordinates.y * CELL_SIZE;
            playerShips.add(new UIShip(
                    ship.getName(),
                    coordinates,
                    ship.getSpeed(),
                    ship.getClass().getSimpleName()));
        }
        return  playerShips;
    }

    public void updateCurrentPlayer(String name) {
        if(Objects.equals(player1.getName(), name)) {
            this.currentPlayer = player1;
        }
        else {
            this.currentPlayer = player2;
        }
    }

}
