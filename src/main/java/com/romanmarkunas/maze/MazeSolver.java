package com.romanmarkunas.maze;

import java.util.List;

public class MazeSolver {

    // x is left to right ~ width, y - up to down ~ height
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        MazeMap map = MazeMap.fromFile(args[0]);
        List<Coordinate> path = new MazePath(map).get();
        map.printMapWithPath(path);

        System.out.println(String.format(
                "Maze solved in: %s ms",
                System.currentTimeMillis() - start));
    }
}
