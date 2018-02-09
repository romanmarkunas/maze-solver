package com.romanmarkunas.maze;

import com.romanmarkunas.maze.direction.Direction;
import com.romanmarkunas.maze.direction.Directions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romans Markuns
 */
public class MazePath {

    private final MazeMap map;


    public MazePath(MazeMap map) {
        this.map = map;
    }


    public List<Coordinate> get() {
        Coordinate coordinate = this.map.getStart();
        Direction direction = initalizeDirection(coordinate);
        int passThroughStart = 0;
        List<Coordinate> path = new ArrayList<>();
        path.add(coordinate);

        while (!this.map.isEnd(coordinate)) {
            // calculate next coordinate
            for (Direction dir : direction.getRightHandRuleDirections()) {
                Coordinate possibleNext = dir.getCoordinateFrom(coordinate);
                if (!this.map.isWall(possibleNext)) {
                    coordinate = possibleNext;
                    direction = dir;
                    break;
                }
            }

            // remove loops
            int previousOccurence = path.indexOf(coordinate);
            if (previousOccurence != -1) {
                path = new ArrayList<>(path.subList(0, previousOccurence));
            }

            // save next coordinate
            path.add(coordinate);

            // check if maze is solvable
            if (this.map.getStart().equals(coordinate)) {
                passThroughStart++;
                if (passThroughStart > 5) {
                    throw new RuntimeException("Maze cannot be solved!");
                }
            }
        }

        return path;
    }

    private Direction initalizeDirection(Coordinate coordinate) {
        if (coordinate.getY() == 1 || coordinate.getX() == 1) {
            return Directions.SOUTH;
        }
        else {
            return Directions.NORTH;
        }
    }
}
