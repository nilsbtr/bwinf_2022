package _02verzinkt;

public class Farbe {
    private int north;
    private int east;
    private int south;
    private int west;

    public Farbe(int north, int east, int south, int west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public int getNorth() {
        return north;
    }

    public int getSouth() {
        return south;
    }

    public int getEast() {
        return east;
    }

    public int getWest() {
        return west;
    }
}
