package name.dezalator.model.ship;

import name.dezalator.model.module.Protection;
import name.dezalator.model.module.Weapon;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;

import java.util.ArrayList;

public class Cruiser extends SpaceShip {
    public static final int PLACES = 2;

    public Cruiser(String name, Coordinates coordinates, int speed, ArrayList<Weapon> weapons, ArrayList<Protection> protectionItems) {
        super(name, coordinates, speed, weapons, protectionItems);
    }
}
