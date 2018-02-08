package com.romanmarkunas.maze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Romans Markuns
 */
public class MazeMap {

    private final int[][] coordinates;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;


    public MazeMap(
            int[][] coordinates,
            int startX,
            int startY,
            int endX,
            int endY) {
        this.coordinates = coordinates;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }


    public boolean isWall(Coordinate coordinate) {
        return this.coordinates[coordinate.getY()][coordinate.getX()] == 1;
    }

    public boolean isEnd(Coordinate coordinate) {
        return this.endX == coordinate.getX() && this.endY == coordinate.getY();
    }

    public Coordinate getStart() {
        return new Coordinate(this.startX, this.startY);
    }

    public void printMapWithPath(List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.coordinates.length; y++) {
            for (int x = 0; x < this.coordinates[0].length; x++) {
                if (this.coordinates[y][x] == 1) {
                    sb.append("#");
                }
                else if (y == this.startY && x == this.startX) {
                    sb.append("S");
                }
                else if (y == this.endY && x == this.endX) {
                    sb.append("E");
                }
                else if (path.contains(new Coordinate(x, y))) {
                    sb.append("X");
                }
                else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }


    public static MazeMap fromFile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

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

        return new MazeMap(grid, startX, startY, endX, endY);
    }
}
