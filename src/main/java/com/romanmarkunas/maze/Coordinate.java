package com.romanmarkunas.maze;

/**
 * Created by Romans Markuns
 */
public class Coordinate {

    private final int x;
    private final int y;


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
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Coordinate that = (Coordinate) other;

        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        int result = this.x;
        result = 31 * result + this.y;
        return result;
    }
}
