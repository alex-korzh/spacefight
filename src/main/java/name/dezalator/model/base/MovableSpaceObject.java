package name.dezalator.model.base;

import name.dezalator.model.util.Coordinates;

public class MovableSpaceObject extends SpaceObject{
    public MovableSpaceObject(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    public void moveTo(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
