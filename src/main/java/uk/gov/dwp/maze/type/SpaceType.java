package uk.gov.dwp.maze.type;

/**
 * Created by Aamirio on 01/02/2017.
 *
 * Space Type In Maze
 */
public enum SpaceType {

    EMPTY(' ', "an empty space"),
    EXIT('F', "the exit"),
    START('S', "the start point"),
    WALL('X', "a wall");

    private final char name;
    private final String description;

    SpaceType(char name, String description) {
        this.name = name;
        this.description = description;
    }

    public char toChar() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
