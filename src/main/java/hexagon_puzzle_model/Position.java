package hexagon_puzzle_model;

import java.util.Objects;

/**
 * Class which represents a hexagon's on the board as a set of x and y coordinates, corresponding to row number and collumn number respectively.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates a <code>Position</code> object given a row and collumn number.
     *
     * @param x represents the row number
     * @param y represents the collumn number
     */
    public Position(int x, int y) {
        //we need not check if the created position is valid, because we do so in PuzzleState when we attempt to make the move
        this.x = x;
        this.y = y;
    }

    /**
     * Converts a <code>Position</code> object into an array of actual coordinates to use with <code>PuzzleState</code>'s <code>currentState</code>.
     *
     * @param position <code>Position</code> to be converted
     * @return an <code>int</code> array of size 2, consisting of two coordinates
     */
    public static int[] convertPositionToCoordinates(Position position) {
        if (position.getX() >= 4){
            if (position.getX() == 5){
                return new int[] {position.getX() - 1, 2 + (position.getY() - 1) * 2};
            }
            return new int[] {position.getX() - 1, 1 + (position.getY() - 1) * 2};
        }

        int startI = 3 - position.getX();

        return new int[] {position.getX() - 1, startI + (position.getY() - 1) * 2};
    }


    /**
     * {@return the Position object's x coordinate (row number)}
     */
    public int getX() {
        return x;
    }

    /**
     * {@return the Position object's y coordinate (collumn number)}
     */
    public int getY() {
        return y;
    }

    /**
     * Determines the equality of <code>this</code> and <code>o</code>.
     *
     * {@return true if the objects are equal, false otherwise}
     * @param o the object to be compared to <code>this</code>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.getX() && y == position.getY();
    }

    /**
     * {@return the hash code of the position}
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
