package name.dezalator.model.ship;

import name.dezalator.model.module.Weapon;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;

import java.util.ArrayList;

public class Wing extends SpaceShip {
    public static final int PLACES = 2;


    public Wing(String name, Coordinates coordinates, int speed, Weapon firstWeapon, Weapon secondWeapon) {

        super(
                name,
                coordinates,
                speed,
                new ArrayList<>() {
                    {
                        add(firstWeapon);
                        add(secondWeapon);
                    }
                },
                null
        );
    }
}
