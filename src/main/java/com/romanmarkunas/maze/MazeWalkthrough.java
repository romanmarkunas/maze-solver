package com.romanmarkunas.maze;

import com.romanmarkunas.maze.direction.Direction;
import com.romanmarkunas.maze.direction.Directions;

import java.util.LinkedList;
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
        Direction direction;
        Coordinate coordinate = this.map.getStart();
        if (coordinate.getY() == 1) {
            direction = Directions.SOUTH;
        }
        else {
            direction = Directions.NORTH;
        }

        List<Coordinate> path = new LinkedList<>();
        path.add(coordinate);

        while (!this.map.isEnd(coordinate)) {
            Coordinate nextInFront = direction.getCoordinateFrom(coordinate);

            if (!this.map.isWall(nextInFront)) {
                coordinate = nextInFront;
            }
            else {
                for (Direction dir : direction.getRightHandRuleDirections()) {
                    Coordinate possibleNext = dir.getCoordinateFrom(coordinate);
                    if (!this.map.isWall(possibleNext)
                            && !this.map.wasVisited(possibleNext)) {
                        coordinate = possibleNext;
                        direction = dir;
                        break;
                    }
                }
            }

//            if (path.contains(coordinate)) {
//                path = path.subList(0, path.indexOf(coordinate));
//            }

            path.add(coordinate);
            this.map.markVisited(coordinate);
            System.out.println(path.size());
        }

        return path;
    }
}
