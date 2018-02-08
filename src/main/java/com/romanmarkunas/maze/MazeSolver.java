package com.romanmarkunas.maze;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Arrays.asList;

public class MazeSolver {

    // x is left to right ~ width, y - up to down ~ height
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        // TODO - change this to BufferedReader and process lines instead of removing from start
        // also custom solution is better because you can return linked list and save on huge object allocations or ArrayList
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

        List<Coordinate> takenMoves = getPath(new Coordinate(startX, startY),
                new Coordinate(endX, endY),
                grid,
                new Coordinate(0, 0),
                new Coordinate(width - 1, height - 1));

//        path.forEach(coord -> System.out.println(coord.getX() + ":" + coord.getY()));
//
//        List<Coordinate> takenMoves = new LinkedList<>();
//        int currentX = startX;
//        int currentY = startY;
//        int possibleX;
//        int possibleY;
//        Random rn = new Random();
//
//        while (!(currentX == endX && currentY == endY)) {
//            takenMoves.add(new Coordinate(currentX, currentY));
//            List<Coordinate> possibleMoves = new ArrayList<>(4);
//
//            possibleX = currentX + 1;
//            possibleY = currentY;
//
//            if (grid[possibleY][possibleX] != 1) {
//                possibleMoves.add(new Coordinate(possibleX, possibleY));
//            }
//
//            possibleX = currentX;
//            possibleY = currentY + 1;
//
//            if (grid[possibleY][possibleX] != 1) {
//                possibleMoves.add(new Coordinate(possibleX, possibleY));
//            }
//
//            possibleX = currentX - 1;
//            possibleY = currentY;
//
//            if (grid[possibleY][possibleX] != 1) {
//                possibleMoves.add(new Coordinate(possibleX, possibleY));
//            }
//            possibleX = currentX;
//            possibleY = currentY - 1;
//
//            if (grid[possibleY][possibleX] != 1) {
//                possibleMoves.add(new Coordinate(possibleX, possibleY));
//            }
//
//            Coordinate randomMove = possibleMoves.get(rn.nextInt(possibleMoves.size()));
//            currentX = randomMove.getX();
//            currentY = randomMove.getY();
//        }
//        takenMoves.add(new Coordinate(endX, endY));
//
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

    private static void printTraversal(List<Coordinate> moves, int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == 1) {
                    System.out.print("#");
                }
                else if (moves.contains(new Coordinate(x, y))) {
                    System.out.print("X");
                }
                else {
                    System.out.print("-");
                }
            }
            System.out.println("");
        }
    }

    private static List<Coordinate> getPath(
            Coordinate vertex1,
            Coordinate vertex2,
            int[][] grid,
            Coordinate segmentLeftTop,
            Coordinate segmentRigthBottom) {
        // detect segment size
        int segmentWidth = segmentRigthBottom.getX() - segmentLeftTop.getX();
        int segmentHeight = segmentRigthBottom.getY() - segmentLeftTop.getY();

        // detect if segment needs slicing
        if ((segmentWidth + 1) * (segmentHeight + 1) < 2) {
            return new ArrayList<>();
        }

        List<Coordinate> connectionPoints = new ArrayList<>(segmentHeight + segmentWidth);
        // determine slice line
        if (segmentWidth >= segmentHeight) {
            // slice vertical
            int sliceCol = segmentLeftTop.getX() + segmentWidth / 2;
            for (int i = segmentLeftTop.getY(); i <= segmentRigthBottom.getY(); i++) {
                if (notWall(sliceCol, i, grid)) {
                    connectionPoints.add(new Coordinate(sliceCol, i));
                }
            }

            if (vertex1.getX() <= vertex2.getX()) {
                // vertex1 goes to the left
                for (Coordinate point : connectionPoints) {
                    List<Coordinate> left;
                    if (isAdjacent(point, vertex1)) {
                        left = new ArrayList<>(asList(vertex1, point));
                    }
                    else {
                        left = getPath(
                                vertex1,
                                point,
                                grid,
                                segmentLeftTop,
                                new Coordinate(sliceCol, segmentRigthBottom.getY()));
                    }
                    List<Coordinate> right;
                    if (isAdjacent(point, vertex2)) {
                        right = new ArrayList<>(asList(point, vertex2));
                    }
                    else {
                        right = getPath(
                                point,
                                vertex2,
                                grid,
                                new Coordinate(sliceCol + 1, segmentLeftTop.getY()),
                                segmentRigthBottom);
                    }

                    if (left.isEmpty() || right.isEmpty() || isAdjacent(left.get(left.size() - 1), right.get(0))) {
                        left.addAll(right);
                        return left;
                    }
                }
            }
        }
        else {
            // slice horizontal
            int sliceRow = segmentLeftTop.getY() + segmentHeight / 2;
            for (int i = segmentLeftTop.getX(); i <= segmentRigthBottom.getX(); i++) {
                if (notWall(i, sliceRow, grid)) {
                    connectionPoints.add(new Coordinate(i, sliceRow));
                }
            }

            if (vertex1.getY() <= vertex2.getY()) {
                // vertex1 goes on top
                for (Coordinate point : connectionPoints) {
                    List<Coordinate> top;
                    if (isAdjacent(point, vertex1)) {
                        top = new ArrayList<>(asList(vertex1, point));
                    }
                    else {
                        top = getPath(
                                vertex1,
                                point,
                                grid,
                                segmentLeftTop,
                                new Coordinate(segmentRigthBottom.getX(), sliceRow));
                    }
                    List<Coordinate> bottom;
                    if (isAdjacent(point, vertex2)) {
                        bottom = new ArrayList<>(asList(point, vertex2));
                    }
                    else {
                        bottom = getPath(
                                point,
                                vertex2,
                                grid,
                                new Coordinate(segmentLeftTop.getX(), sliceRow + 1),
                                segmentRigthBottom);
                    }
                    if (top.isEmpty() || bottom.isEmpty() || isAdjacent(top.get(top.size() - 1), bottom.get(0))) {
                        top.addAll(bottom);
                        return top;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    private static boolean notWall(int x, int y, int[][] grid) {
        return grid[y][x] != 1;
    }

    private static boolean isAdjacent(Coordinate c1, Coordinate c2) {
        if (c1.equals(c2)) {
            return true;
        }
        else if (c1.getX() == c2.getX()) {
            return Math.abs(c1.getY() - c2.getY()) == 1;
        }
        else if (c1.getY() == c2.getY()) {
            return Math.abs(c1.getX() - c2.getX()) == 1;
        }
        else {
            return false;
        }
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
