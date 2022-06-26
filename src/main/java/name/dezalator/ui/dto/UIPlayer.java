package name.dezalator.ui.dto;

import java.util.ArrayList;

public class UIPlayer {
    private String name;
    private ArrayList<UIShip> ships;

    public UIPlayer(String name, ArrayList<UIShip> ships) {
        this.name = name;
        this.ships = ships;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<UIShip> getShips() {
        return ships;
    }

    public void addShip(UIShip ship) {
        ships.add(ship);
    }

    public void removeShip(UIShip ship) {
        ships.remove(ship);
    }
}
