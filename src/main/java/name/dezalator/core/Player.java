package name.dezalator.core;

import name.dezalator.model.ship.base.SpaceShip;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<SpaceShip> ships;

    public Player(String name, ArrayList<SpaceShip> ships) {
        this.name = name;
        this.ships = ships;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SpaceShip> getShips() {
        return ships;
    }

    public void addShip(SpaceShip ship) {
        ships.add(ship);
    }

    public void removeShip(SpaceShip ship) {
        ships.remove(ship);
    }
}
