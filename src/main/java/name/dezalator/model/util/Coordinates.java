package name.dezalator.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
    }

    public Coordinates scaled(int scale) {
        return new Coordinates(this.x*scale, this.y*scale);
    }

    public Coordinates unscaled(int scale) {
        return new Coordinates(this.x/scale, this.y/scale);
    }

    public ArrayList<Coordinates> getNeighbours() {
        return new ArrayList<>(Arrays.asList(
                new Coordinates(this.x+1, this.y),
                new Coordinates(this.x-1, this.y),
                new Coordinates(this.x, this.y+1),
                new Coordinates(this.x, this.y-1)
        ));
    }

    public boolean inRange(Coordinates min, Coordinates max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y;
    }

    public int distance(Coordinates coordinates) {
        return Math.abs(coordinates.x-this.x) + Math.abs(coordinates.y - this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
