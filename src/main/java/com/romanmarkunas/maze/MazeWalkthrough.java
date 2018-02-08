package com.romanmarkunas.maze;

import com.romanmarkunas.maze.direction.Direction;
import com.romanmarkunas.maze.direction.Directions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romans Markuns
 */
public class MazeWalkthrough {

    private final MazeMap map;


    public MazeWalkthrough(MazeMap map) {
        this.map = map;
    }


    public List<Coordinate> get() {
        Coordinate coordinate = this.map.getStart();

        Direction direction = Directions.NORTH;
        if (coordinate.getY() == 1 || coordinate.getX() == 1) {
            direction = Directions.SOUTH;
        }

        List<Coordinate> path = new ArrayList<>();
        path.add(coordinate);

        while (!this.map.isEnd(coordinate)) {

            for (Direction dir : direction.getRightHandRuleDirections()) {
                Coordinate possibleNext = dir.getCoordinateFrom(coordinate);
                if (!this.map.isWall(possibleNext)) {
                    coordinate = possibleNext;
                    direction = dir;
                    break;
                }
            }

            int previousOccurence = path.indexOf(coordinate);
            if (previousOccurence != -1) {
                path = new ArrayList<>(path.subList(0, previousOccurence));
            }

            path.add(coordinate);
        }

        return path;
    }
}
