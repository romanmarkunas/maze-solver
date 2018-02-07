package com.romanmarkunas.maze;

public class MazeSolver {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        System.out.println(String.format(
                "Maze solved in: %s ms",
                System.currentTimeMillis() - start));
    }
}
