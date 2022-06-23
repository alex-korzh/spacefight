package name.dezalator.ui;

import name.dezalator.model.util.Coordinates;

public class UIShip {
    private String name;
    private Coordinates coordinates;
    private int speed;
    private String type;

    public UIShip(String name, Coordinates coordinates, int speed, String type) {
        this.name = name;
        this.coordinates = coordinates;
        this.speed = speed;
        this.type = type;
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

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getType() {
        return type;
    }
}
