package name.dezalator.model.base;

import name.dezalator.model.util.Coordinates;

public class SpaceObject {
    protected String name;
    protected Coordinates coordinates;

    public SpaceObject(String name, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }
}
