package name.dezalator.model.ship;

import name.dezalator.model.module.Weapon;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;
import name.dezalator.util.exceptions.WingLandedException;

import java.util.ArrayList;

public class Wing extends SpaceShip {
    public static final int PLACES = 2;

    private WingState state;

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
        state = WingState.LANDED;
    }

    @Override
    public void moveTo(Coordinates coordinates) {
        if (state == WingState.LANDED) {
            throw new WingLandedException();
        }
        super.moveTo(coordinates);
    }

    public WingState getState() {
        return state;
    }

    public void setState(WingState state) {
        this.state = state;
    }
}
