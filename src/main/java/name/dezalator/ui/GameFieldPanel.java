package name.dezalator.ui;

import name.dezalator.core.Engine;
import name.dezalator.core.Player;
import name.dezalator.core.util.Event;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;
import name.dezalator.ui.dto.UIPlayer;
import name.dezalator.ui.dto.UIShip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class GameFieldPanel extends JPanel implements ActionListener {
    int screenWidth;
    int screenHeight;
    static final int CELL_SIZE = 50;
    Coordinates hoveredCell;
    Coordinates previousHoveredCell;
    int turn;
    UIPlayer player1;
    UIPlayer player2;
    UIPlayer currentPlayer;
    Coordinates selectedCell;
    MenuData menuData;
    boolean gameStarted;

    public GameFieldPanel(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addMouseMotionListener(new MouseMovementListener());
        this.addMouseListener(new MListener());
        this.addKeyListener(new KAdapter());
        this.menuData = new MenuData(screenWidth, screenHeight);
        this.gameStarted = false;
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gameStarted) {
            draw(g);
        }
        else {
            drawMenu(g);
        }
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(menuData.menuWidth, menuData.menuHeightPoint, menuData.menuWidth, menuData.menuHeight);

        Font font = new Font("Sans", Font.PLAIN, 45);
        g.setFont(font);
        FontMetrics metrics = getFontMetrics(g.getFont());


        for( int y=menuData.menuHeightPoint+menuData.internalStep, i=0; y<(menuData.menuHeightPoint+menuData.menuHeight-menuData.internalStep); y += (menuData.buttonHeight + menuData.internalStep), i++) {
            g.drawRect(menuData.menuWidth+menuData.internalStep, y, menuData.buttonWidth, menuData.buttonHeight);
            if (i < menuData.messages.length) {
                int gap = menuData.buttonWidth/2 - metrics.stringWidth(menuData.messages[i])/2;
                g.drawString(menuData.messages[i], menuData.menuWidth+menuData.internalStep+gap, y+menuData.buttonHeight*9/10);
            }

        }
    }

    private void draw(Graphics g) {
        // GRID
        for (int i = 0; i < screenHeight / CELL_SIZE; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, screenHeight);
            g.drawLine(0, i * CELL_SIZE, screenWidth, i * CELL_SIZE);
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
        g.drawString(message, screenWidth - metrics.stringWidth(message), g.getFont().getSize());

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
            if (gameStarted) {
                Coordinates clickCoordinates = getCellCoordinatesFromMouseCoordinates(e.getX(), e.getY());
                if (clickCoordinates.equals(selectedCell)) {
                    selectedCell = null;
                    repaint(clickCoordinates.x, clickCoordinates.y, CELL_SIZE + 1, CELL_SIZE + 1);
                } else {
                    selectedCell = clickCoordinates;
                    repaint(clickCoordinates.x, clickCoordinates.y, CELL_SIZE + 1, CELL_SIZE + 1);
                }
            }
            else {
                Integer number = getButtonNumberFromMouseCoordinates(e.getX(), e.getY());
                if (number != null) {
                    menuButtonClicked(number);
                }
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

    public Integer getButtonNumberFromMouseCoordinates(int x, int y) {
        if (x >= menuData.menuWidth+menuData.internalStep && x <= menuData.menuWidth+menuData.internalStep+menuData.buttonWidth){
            for (int i=0; i<=menuData.messages.length;i++) {
                if (y >=menuData.menuHeightPoint+menuData.internalStep+i*(menuData.internalStep+menuData.buttonHeight) &&
                        y <= menuData.menuHeightPoint+menuData.internalStep+i*(menuData.internalStep+menuData.buttonHeight) + menuData.buttonHeight) {
                    return i;
                }
            }
        }
        return null;
    }

    public void menuButtonClicked(int number) {
        String option = menuData.messages[number];
        switch (option) {
            case MenuData.START_GAME -> startGame();
            case MenuData.EXIT -> exit();
        }
    }

    private void exit() {
        System.exit(0);
    }

    private void startGame() {
        this.gameStarted = true;
        repaint();
    }

}
