package com.romanmarkunas.maze.direction;

import com.romanmarkunas.maze.Coordinate;

import java.util.List;

/**
 * Created by Romans Markuns
 */
public interface Direction {

    List<Direction> getRightHandRuleDirections();

    Coordinate getCoordinateFrom(Coordinate base);
}
