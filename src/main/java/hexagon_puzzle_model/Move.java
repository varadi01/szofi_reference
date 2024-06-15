package hexagon_puzzle_model;

import java.util.Objects;

/**
 * Class which represents a move which can be performed on a given state of the puzzle.
 * A move can be made by selecting one of the hexagons that has six neighbouring hexagons, and
 * specifying a direction to rotate said neighbours in, around the selected hexagon acting as the central point.
 * Each move is described by a <code>Position</code> object which is the position of the selected hexagon, and
 * an <code>int</code>, either 1 or -1, that describes the direction or rotation.
 * 1 corresponds to clockwise rotation, -1 corresponds to counter-clockwise rotation.
 */
public class Move {

    /**
     * <code>Position</code> object which is the position of the move's selected hexagon.
     */
    public Position center;

    /**
     * The direction of rotation. Indicated by either 1 or -1.
     * 1 corresponds to clockwise rotation, -1 corresponds to counter-clockwise rotation.
     */
    public int direction;

    /**
     * Creates a new <code>Move</code> object given the position of the selected hexagon to move from, and a direction to rotate neighbours in.
     *
     * @param selectedNode <code>Position</code> object describing the position of the selected hexagon.
     * @param direction The direction of rotation. Indicated by either 1 (clockwise) or -1 (counter-clockwise).
     */
    public Move(Position selectedNode, int direction) {
        //we need not check if the created move is valid, because we do so in PuzzleState when we attempt to make the move
        this.center = selectedNode;
        this.direction = direction;
    }

    /**
     * {@return the position of the move's central hexagon}
     */
    public Position getCenter() {
        return center;
    }

    /**
     * {@return the <code>direction</code> of the move}
     */
    public int getDirection() {
        return direction;
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
        if (!(o instanceof Move move)) return false;
        return direction == move.direction && center.equals(move.center);
    }

    /**
     * {@return the hash code of the move}
     */
    @Override
    public int hashCode() {
        return Objects.hash(center, direction);
    }
}
