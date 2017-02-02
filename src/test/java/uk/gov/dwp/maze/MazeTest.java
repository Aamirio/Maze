package uk.gov.dwp.maze;

import org.junit.Test;
import uk.gov.dwp.maze.exception.InvalidCoordinateException;
import uk.gov.dwp.maze.type.Cursor;
import uk.gov.dwp.maze.type.Direction;
import uk.gov.dwp.maze.type.SpaceType;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Aamirio on 01/02/2017.
 *
 * Tests Maze functionality
 */

public class MazeTest {

    private Maze maze = new Maze("Maze1.txt");

    @Test
    public void getNumberOfWalls() {
        assertEquals(149, maze.getNumberOfWalls());
    }

    @Test
    public void getNumberOfEmptySpaces() {
        assertEquals(74, maze.getNumberOfEmptySpaces());
    }

    @Test
    public void getSpaceType() {
        SpaceType spaceType = maze.getSpaceType(14,14);

        assertNotNull(spaceType);
        assertEquals(SpaceType.WALL, spaceType);
    }

    @Test
    public void getStartPoint() {
        Cursor cursor = maze.getStartPoint();

        assertNotNull(cursor.getCoordinates());
        assertEquals(Direction.NORTH, cursor.getDirection());
        assertEquals(3, cursor.getCoordinates().getX());
        assertEquals(3, cursor.getCoordinates().getY());
    }

    @Test(expected = InvalidCoordinateException.class)
    public void getSpaceTypeInvalidXCoord() {
        maze.getSpaceType(15,14);
    }

    @Test(expected = InvalidCoordinateException.class)
    public void getSpaceTypeInvalidYCoord() {
        maze.getSpaceType(14,15);
    }

    @Test
    public void loadNewMaze() {
        maze.loadNewMaze("Maze2.txt");

        SpaceType spaceType = maze.getSpaceType(1,16);
        assertNotNull(spaceType);
        assertEquals(SpaceType.EXIT, spaceType);
    }
}
