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
        Direction direction = Directions.NORTH;
        Coordinate coordinate = this.map.getStart();
        InfiniteLoopDetector detector = new InfiniteLoopDetector(3);
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
                List<Coordinate> loop = new ArrayList<>(
                        path.subList(previousOccurence, path.size()));
                if (detector.infiniteLoopDetected(loop)) {
                    direction = Directions.random();
                }

                path = path.subList(0, previousOccurence);
            }

            path.add(coordinate);
        }

        return path;
    }


    private static class InfiniteLoopDetector {

        private final int loopCountThreshold;
        private int samePathLoopCount = 0;
        private List<Coordinate> lastLoop = null;


        private InfiniteLoopDetector(int loopCountThreshold) {
            this.loopCountThreshold = loopCountThreshold;
        }


        private boolean infiniteLoopDetected(List<Coordinate> loop) {
            if (loop.equals(this.lastLoop)) {
                this.samePathLoopCount++;
            }
            else {
                this.samePathLoopCount = 0;
                this.lastLoop = loop;
            }

            return this.samePathLoopCount >= this.loopCountThreshold;
        }
    }
}
