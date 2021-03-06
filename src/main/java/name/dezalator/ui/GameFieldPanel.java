package name.dezalator.ui;

import name.dezalator.core.Data;
import name.dezalator.core.Engine;
import name.dezalator.core.Player;
import name.dezalator.core.util.Event;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GameFieldPanel extends JPanel implements ActionListener {
    int screenWidth;
    int screenHeight;
    static final int CELL_SIZE = 50;
    Coordinates hoveredCell;
    Coordinates previousHoveredCell;
    Coordinates selectedCell;
    SpaceShip selectedShip;
    MenuData menuData;
    boolean inGame;
    Coordinates min;
    Coordinates max;
    Map<SpaceShip, Boolean> usedShips;

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
        this.inGame = false;
        this.min = new Coordinates(0,0);
        this.max = new Coordinates(screenWidth/CELL_SIZE, screenHeight/CELL_SIZE);
        this.selectedShip = null;
        this.usedShips = new HashMap<>();
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame) {
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


        // turn, player
        g.setColor(Color.white);
        g.setFont(new Font("Sans", Font.PLAIN, 15));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String message = "Player: " + Data.getCurrentPlayerName() + "    Turn: " + Data.getTurn();
        g.drawString(message, screenWidth - metrics.stringWidth(message), g.getFont().getSize());

        // ships
        drawShipsOfPlayer(g, Data.getPlayer1());
        drawShipsOfPlayer(g, Data.getPlayer2());

        // ship info

        if (selectedShip == null){
            drawShipInfoIfHoveredOrSelected(g, Data.getPlayer1(), hoveredCell);
            drawShipInfoIfHoveredOrSelected(g, Data.getPlayer2(), hoveredCell);
        } else {
            drawShipInfoIfHoveredOrSelected(g, Data.getCurrentPlayer(), selectedShip.getCoordinates().scaled(CELL_SIZE));
        }

        // available for move

        for (SpaceShip ship: Data.getCurrentPlayerShips()) {
            if (!usedShips.containsKey(ship)) {
                BFS(g, ship.getCoordinates(), ship.getSpeed());
            }
        }

        // hovered cell
        if (hoveredCell != null){
            boolean found = findCurrentPlayerShipByCoordinates(hoveredCell) != null;
            if (found) {
                drawCellOfColor(g, hoveredCell, Color.green);
            }
            else {
                drawCellOfColor(g, hoveredCell, Color.gray);
            }
            g.drawRect(hoveredCell.x, hoveredCell.y, CELL_SIZE, CELL_SIZE);
            previousHoveredCell = hoveredCell;
        }

        // selected cell

        if (selectedShip != null) {
            drawCellOfColor(g, selectedShip.getCoordinates().scaled(CELL_SIZE), Color.green);
        }

    }

    private void BFS(Graphics g, Coordinates coordinates, int maxRange) {
        Coordinates initCoordinates = coordinates;
        Map<Coordinates, Boolean> visited = new HashMap<>();

        LinkedList<Coordinates> queue = new LinkedList<>();

        visited.put(coordinates, true);
        queue.add(coordinates);
        while (queue.size() != 0) {
            coordinates = queue.poll();
            drawCellOfColor(g, coordinates.scaled(CELL_SIZE), Color.blue);

            for (Coordinates c: coordinates.getNeighbours()) {
                if(!visited.containsKey(c) && c.inRange(min, max) && c.distance(initCoordinates) <= maxRange) {
                    visited.put(c, true);
                    queue.add(c);
                }
            }
        }
    }


    private SpaceShip findCurrentPlayerShipByCoordinates(Coordinates coordinates) {
        return Data.getCurrentPlayerShips().stream()
                .filter(ship -> coordinates.equals(ship.getCoordinates().scaled(CELL_SIZE)))
                .findAny().orElse(null);
    }

    private void drawCellOfColor(Graphics g, Coordinates cellCoordinates, Color color) {
        g.setColor(color);
        g.drawRect(cellCoordinates.x, cellCoordinates.y, CELL_SIZE, CELL_SIZE);
    }

    private void drawShipsOfPlayer(Graphics g, Player player) {
        for(SpaceShip ship: player.getShips()) {
            Coordinates coordinates = ship.getCoordinates().scaled(CELL_SIZE);
            if (Data.isCurrentPlayer(player)) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.red);
            }
            int radius = CELL_SIZE / 2;
            g.fillOval(coordinates.x+radius/2, coordinates.y+radius/2, radius, radius);
        }
    }

    private void drawShipInfoIfHoveredOrSelected(Graphics g, Player player, Coordinates mouseCoordinates) {
        if (mouseCoordinates == null) {
            return;
        }
        SpaceShip found = player.getShips().stream()
                .filter(ship -> mouseCoordinates.equals(ship.getCoordinates().scaled(CELL_SIZE)))
                .findAny().orElse(null);
        if (found != null) {
            g.setColor(Color.white);
            g.setFont(new Font("Sans", Font.PLAIN, 15));
            String message = "Ship of type " + found.getType() + " of player: " + player.getName();
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
            if (inGame) {
                Coordinates clickCoordinates = getCellCoordinatesFromMouseCoordinates(e.getX(), e.getY());

                if(selectedShip != null &&
                        clickCoordinates.unscaled(CELL_SIZE).distance(selectedShip.getCoordinates()) <= selectedShip.getSpeed() &&
                        !usedShips.containsKey(selectedShip)) {
                    selectedShip.moveTo(clickCoordinates.unscaled(CELL_SIZE));
                    usedShips.put(selectedShip, true);
                    repaint(clickCoordinates.x, clickCoordinates.y, CELL_SIZE + 1, CELL_SIZE + 1);
                    repaint(selectedShip.getCoordinates().scaled(CELL_SIZE).x, selectedShip.getCoordinates().scaled(CELL_SIZE).y, CELL_SIZE + 1, CELL_SIZE + 1);
                }

                if (selectedShip != null && clickCoordinates.equals(selectedShip.getCoordinates())) {
                    selectedShip = null;
                    repaint(clickCoordinates.x, clickCoordinates.y, CELL_SIZE + 1, CELL_SIZE + 1);
                } else {
                    selectedShip = findCurrentPlayerShipByCoordinates(clickCoordinates); // null if not found
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
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE -> endTurn();
                case KeyEvent.VK_ESCAPE -> endGame();
            }
        }
    }

    public void endGame() {
        // TODO actual clear of game data
//        Engine.notifyGame(Event.END_GAME);
        this.selectedCell = null;
        inGame = false;
        repaint();
    }

    public void endTurn() {
        Engine.notifyGame(Event.END_TURN);
        this.selectedCell = null;
        this.usedShips.clear();
        repaint();
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
        this.inGame = true;
        repaint();
    }

}
