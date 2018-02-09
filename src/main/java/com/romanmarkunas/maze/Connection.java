package com.romanmarkunas.maze;

/**
 * Created by Romans Markuns
 */
public class Connection {

    private final Segment map;
    private final List<Coordinate> allConnectionCoordinates;
    private final List<Coordinate> endpoints;


    public List<Coordinate> getEndpoints() {
        return endpoints;
    }

    public List<Coordinates> getConnectionBetween(Coordinate endpoint1, Coordinate endpoint2) {

    }

    public static fromSlice(grid, Coordinate vertex1, Coordinate vertex2) {
        // this should treat a group of coordinates as a single connection
        // => we can figure out connection entry points into other 2 parts and
        // use these entry points to connect paths

        // also we can discard segment that have only one entry point
    }
}
