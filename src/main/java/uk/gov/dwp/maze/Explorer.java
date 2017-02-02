package uk.gov.dwp.maze;

import uk.gov.dwp.maze.type.Coordinates;
import uk.gov.dwp.maze.type.Cursor;
import uk.gov.dwp.maze.type.Direction;
import uk.gov.dwp.maze.type.SpaceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Explores through a Maze object with single lettered commands from user.
 *
 * Given a maze the explorer can drop in to the Start point (facing north)
 * An explorer on a maze can move forward
 * An explorer on a maze can turn left and right (changing direction the explorer is facing)
 * An explorer on a maze can see what is in front of them
 * An explorer on a maze can see all movement options from their given location
 * An explorer on a maze knows if they have or have not already been to any area they step into
 *
 */
public class Explorer {

    private Maze maze;
    private Scanner userInput;
    private Cursor cursor;
    private SpaceType forward, right, left, behind;
    private List<Coordinates> record = new ArrayList<>();

    public static void main(String[] args) {
        Explorer explorer = new Explorer();
        explorer.beginExploring(args[0]);
    }

    private static void printValidCommands() {

        System.out.println("The following keys in parentheses are valid commands: " +
                "(F)orward, (L)eft, (R)ight, GetCurrent(C)oordinates");
    }

    private void beginExploring(String fileName) {

        userInput = new Scanner(System.in);
        maze = new Maze(fileName);
        cursor = maze.getStartPoint();

        System.out.println("Begin exploring");
        printValidCommands();
        identifyAvailableOptions();
    }

    private void identifyAvailableOptions() {

        Coordinates coordinates = cursor.getCoordinates();

        SpaceType north = maze.getSpaceType(coordinates.getX(), coordinates.getY() - 1);
        SpaceType south = maze.getSpaceType(coordinates.getX(), coordinates.getY() + 1);
        SpaceType east = maze.getSpaceType(coordinates.getX() + 1, coordinates.getY());
        SpaceType west = maze.getSpaceType(coordinates.getX() - 1, coordinates.getY());

        switch(cursor.getDirection()) {
            case NORTH:
                setBearings(north, east, west, south);
                break;
            case SOUTH:
                setBearings(south, west, east, north);
                break;
            case EAST:
                setBearings(east, south, north, west);
                break;
            case WEST:
                setBearings(west, north, south, east);
                break;
        }

        printOptions();
        readInput();
    }

    private void setBearings(SpaceType forward, SpaceType right, SpaceType left, SpaceType behind) {

        this.forward = forward;
        this.right = right;
        this.left = left;
        this.behind = behind;
    }

    private void printOptions() {

        System.out.println(String.format("In front is %s", forward.getDescription()));
        System.out.println(String.format("On the right is %s", right.getDescription()));
        System.out.println(String.format("On the left is %s", left.getDescription()));
        System.out.println(String.format("Behind is %s", behind.getDescription()));
    }

    private void readInput() {

        String value = userInput.next();

        switch(value) {
            case "F":
                tryMovingForward();
                break;
            case "L":
                rotateLeft();
                break;
            case "R":
                rotateRight();
                break;
            case "C":
                printCurrentCoords();
                break;
            default:
                System.out.println("Invalid command");
                printValidCommands();
                readInput();
        }
        readInput();
    }

    private void tryMovingForward() {

        if (forward.equals(SpaceType.EXIT)) {
            moveForward();
            System.out.println("Congratulations, you have found your way out. Goodbye");
            userInput.close();
            System.exit(0);
        }
        else if (!forward.equals(SpaceType.WALL)) {
            moveForward();
            identifyAvailableOptions();
            readInput();
        } else {
            System.out.println("Cannot move forward, wall ahead, you must turn first");
            printOptions();
            readInput();
        }
    }

    private void moveForward() {

        int currentXCoord = cursor.getCoordinates().getX();
        int currentYCoord = cursor.getCoordinates().getY();

        switch (cursor.getDirection()) {
            case NORTH:
                cursor.setCoordinates(currentXCoord, currentYCoord - 1);
                break;
            case SOUTH:
                cursor.setCoordinates(currentXCoord, currentYCoord + 1);
                break;
            case EAST:
                cursor.setCoordinates(currentXCoord + 1, currentYCoord);
                break;
            case WEST:
                cursor.setCoordinates(currentXCoord - 1, currentYCoord);
                break;
        }

        currentXCoord = cursor.getCoordinates().getX();
        currentYCoord = cursor.getCoordinates().getY();

        System.out.println(String.format("Moved forward to coords (%s,%s).", currentXCoord, currentYCoord));

        checkRecord(currentXCoord, currentYCoord);
    }

    private void checkRecord(int xCoord, int yCoord) {
        Coordinates coordinates = new Coordinates(xCoord, yCoord);

        if (record.contains(coordinates)) {
            System.out.println("Familiar territory. You have already been here");
        } else {
            System.out.println("New pastures. You have not been here before");
            record.add(new Coordinates(xCoord, yCoord));
        }
    }

    private void rotateLeft() {
        switch (cursor.getDirection()) {
            case NORTH:
                cursor.setDirection(Direction.WEST);
                break;
            case SOUTH:
                cursor.setDirection(Direction.EAST);
                break;
            case EAST:
                cursor.setDirection(Direction.NORTH);
                break;
            case WEST:
                cursor.setDirection(Direction.SOUTH);
                break;
        }
        System.out.println("Rotated left");
        identifyAvailableOptions();
    }

    private void rotateRight() {
        switch (cursor.getDirection()) {
            case NORTH:
                cursor.setDirection(Direction.EAST);
                break;
            case SOUTH:
                cursor.setDirection(Direction.WEST);
                break;
            case EAST:
                cursor.setDirection(Direction.SOUTH);
                break;
            case WEST:
                cursor.setDirection(Direction.NORTH);
                break;
        }
        System.out.println("Rotated right");
        identifyAvailableOptions();
    }

    private void printCurrentCoords() {
        System.out.println(String.format("X: %s Y: %s", cursor.getCoordinates().getX(), cursor.getCoordinates().getY()));
    }
}
