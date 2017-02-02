package uk.gov.dwp.maze.type;

/**
 * Created by Aamirio on 02/02/2017.
 *
 * A Pair of x and y coordinates.
 *
 */
public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {

        boolean isEqual = false;

        if (other instanceof Coordinates) {
            Coordinates coordinates = (Coordinates) other;
            isEqual = (this.getX() == coordinates.getX() && this.getY() == coordinates.getY());
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.getX() * 31 + this.getY();
    }
}
