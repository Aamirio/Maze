package uk.gov.dwp.maze;

import uk.gov.dwp.maze.exception.InvalidCoordinateException;
import uk.gov.dwp.maze.type.Cursor;
import uk.gov.dwp.maze.type.Direction;
import uk.gov.dwp.maze.type.SpaceType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Loads a Maze from a txt file into memory.
 */
public class Maze {

    private List<String> lines;
    private int noOfWalls;
    private int noOfEmptySpaces;

    /**
     * Streams the file and loads the Maze into memory.
     * @param fileName name of the file containing the maze
     */
    public Maze(String fileName) {
        streamFile(fileName);
    }

    /**
     * Streams the file and loads a new Maze into memory.
     * @param fileName name of the file containing the maze
     */
    void loadNewMaze(String fileName) {
        streamFile(fileName);
    }

    /**
     * Returns the number of walls the maze has
     * @return number of walls
     */
    int getNumberOfWalls() {
        return noOfWalls;
    }

    /**
     * Returns the number of empty spaces within the maze
     * @return number of empty spaces
     */
    int getNumberOfEmptySpaces() {
        return noOfEmptySpaces;
    }

    /**
     * Returns the type of space given a pair of coordinates. Coordinates' index begin from 0.
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The type of space e.g. WALL
     * @throws InvalidCoordinateException When coordinates are out of bounds
     */
    SpaceType getSpaceType(int x, int y) throws InvalidCoordinateException {
        try {
            for (SpaceType value : SpaceType.values()) {
                if (lines.get(y).charAt(x) == value.toChar()) { return value; }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCoordinateException();
        }
        return null;
    }

    /**
     * Returns a cursor for where the start point is marked on the Maze
     * @return The cursor which includes direction as well as coordinates
     */
    Cursor getStartPoint() {
        Cursor cursor = new Cursor(Direction.NORTH, null);

        int x = 0;
        for (String line : lines) {
            if (line.contains("S")) {
                cursor.setCoordinates(x, line.indexOf('S'));
            }
            x++;
        }
        return cursor;
    }

    private void streamFile(String fileName) {
        try {
            Path path = Paths.get(this.getClass().getResource(String.format("/%s", fileName)).getPath());
            lines = Files.readAllLines(path, Charset.defaultCharset());
            noOfWalls = getCharCountFromMaze(lines, 'X');
            noOfEmptySpaces = getCharCountFromMaze(lines, ' ');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getCharCountFromMaze(List<String> lines, char character) {
        int charCount = 0;

        for (String line : lines) {
            charCount += getCharCountFromLine(line, character);
        }
        return charCount;
    }

    private int getCharCountFromLine(String line, char character) {
        int noOfChars = 0;

        for(char letter : line.toCharArray()) {
            if (letter == character) { noOfChars++; }
        }
        return noOfChars;
    }
}
