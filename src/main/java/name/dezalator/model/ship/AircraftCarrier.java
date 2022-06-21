package name.dezalator.model.ship;

import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;
import name.dezalator.util.exceptions.TooManyWingsException;

import java.util.ArrayList;

public class AircraftCarrier extends SpaceShip {
    public static final int WINGS = 1;

    ArrayList<Wing> wings;

    public AircraftCarrier(String name, Coordinates coordinates, int speed, ArrayList<Wing> wings) {
        super(name, coordinates, speed, null, null);
        if (wings.size() > WINGS) {
            throw new TooManyWingsException();
        }
        this.wings = wings;
    }
}
