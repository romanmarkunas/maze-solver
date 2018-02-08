package com.romanmarkunas.maze;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MazeSolver {

    // x is left to right ~ width, y - up to down ~ height
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        String[] widthAndHeigth = lines.get(0).split(" ");
        int width = Integer.parseInt(widthAndHeigth[0]);
        int height = Integer.parseInt(widthAndHeigth[1]);
        lines.remove(0);

        String[] startCoordinates = lines.get(0).split(" ");
        int startX = Integer.parseInt(startCoordinates[0]);
        int startY = Integer.parseInt(startCoordinates[1]);
        lines.remove(0);

        String[] endCoordinates = lines.get(0).split(" ");
        int endX = Integer.parseInt(endCoordinates[0]);
        int endY = Integer.parseInt(endCoordinates[1]);
        lines.remove(0);

        int[][] grid = new int[height][width];
        for (int y = 0; y < height; y++) {
            String[] tilesXString = lines.get(y).split(" ");
            for (int x = 0; x < width; x++) {
                grid[y][x] = Integer.parseInt(tilesXString[x]);
            }
        }

        List<Coordinate> takenMoves = new LinkedList<>();
        int currentX = startX;
        int currentY = startY;
        int possibleX;
        int possibleY;
        Random rn = new Random();

        while (!(currentX == endX && currentY == endY)) {
            takenMoves.add(new Coordinate(currentX, currentY));
            List<Coordinate> possibleMoves = new ArrayList<>(4);

            possibleX = currentX + 1;
            possibleY = currentY;

            if (grid[possibleY][possibleX] != 1) {
                possibleMoves.add(new Coordinate(possibleX, possibleY));
            }

            possibleX = currentX;
            possibleY = currentY + 1;

            if (grid[possibleY][possibleX] != 1) {
                possibleMoves.add(new Coordinate(possibleX, possibleY));
            }

            possibleX = currentX - 1;
            possibleY = currentY;

            if (grid[possibleY][possibleX] != 1) {
                possibleMoves.add(new Coordinate(possibleX, possibleY));
            }
            possibleX = currentX;
            possibleY = currentY - 1;

            if (grid[possibleY][possibleX] != 1) {
                possibleMoves.add(new Coordinate(possibleX, possibleY));
            }

            Coordinate randomMove = possibleMoves.get(rn.nextInt(possibleMoves.size()));
            currentX = randomMove.getX();
            currentY = randomMove.getY();
        }
        takenMoves.add(new Coordinate(endX, endY));

        List<Coordinate> reducedLoopTakenMoves = new LinkedList<>(takenMoves);
        int currentMove = 0;
        while (currentMove < reducedLoopTakenMoves.size()) {
            Coordinate move = reducedLoopTakenMoves.get(currentMove);
            int lastSamePosition = reducedLoopTakenMoves.lastIndexOf(move);
            if (lastSamePosition > currentMove) {
                List<Coordinate> temp = new LinkedList<>();
                temp.addAll(reducedLoopTakenMoves.subList(0, currentMove));
                temp.addAll(reducedLoopTakenMoves.subList(lastSamePosition, reducedLoopTakenMoves.size()));
                reducedLoopTakenMoves = temp;
            }
            currentMove++;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == 1) {
                    System.out.print("#");
                }
                else if (y == startY && x == startX) {
                    System.out.print("S");
                }
                else if (y == endY && x == endX) {
                    System.out.print("E");
                }
                else if (reducedLoopTakenMoves.contains(new Coordinate(x, y))) {
                    System.out.print("X");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }

        System.out.println(String.format(
                "Maze solved in: %s ms",
                System.currentTimeMillis() - start));
    }

    private static class Coordinate {

        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
