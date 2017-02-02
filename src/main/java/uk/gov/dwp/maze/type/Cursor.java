package uk.gov.dwp.maze.type;

/**
 * Created by Aamirio on 02/02/2017.
 *
 * A Cursor which contains the direction it is facing in, along with a pair of X and Y coordinates
 */
public class Cursor {

    private Direction direction;
    private Coordinates coordinates;

    public Cursor(Direction direction, Coordinates coordinates){
        this.direction = direction;
        this.coordinates = coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int x, int y) {
        this.coordinates = new Coordinates(x, y);
    }
}
