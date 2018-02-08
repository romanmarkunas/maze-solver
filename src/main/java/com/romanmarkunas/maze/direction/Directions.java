package com.romanmarkunas.maze.direction;

import com.romanmarkunas.maze.Coordinate;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Romans Markuns
 */
public enum Directions implements Direction {

    NORTH {
        @Override
        public List<Direction> getRightHandRuleDirections() {
            return asList(EAST, NORTH, WEST, SOUTH);
        }

        @Override
        public Coordinate getCoordinateFrom(Coordinate base) {
            return new Coordinate(base.getX(), base.getY() - 1);
        }
    },
    WEST {
        @Override
        public List<Direction> getRightHandRuleDirections() {
            return asList(NORTH, WEST, SOUTH, EAST);
        }

        @Override
        public Coordinate getCoordinateFrom(Coordinate base) {
            return new Coordinate(base.getX() - 1, base.getY());
        }
    },
    SOUTH {
        @Override
        public List<Direction> getRightHandRuleDirections() {
            return asList(WEST, SOUTH, EAST, NORTH);
        }

        @Override
        public Coordinate getCoordinateFrom(Coordinate base) {
            return new Coordinate(base.getX(), base.getY() + 1);
        }
    },
    EAST {
        @Override
        public List<Direction> getRightHandRuleDirections() {
            return asList(SOUTH, EAST, NORTH, WEST);
        }

        @Override
        public Coordinate getCoordinateFrom(Coordinate base) {
            return new Coordinate(base.getX() + 1, base.getY());
        }
    }
}
