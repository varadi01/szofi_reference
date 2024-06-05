package hexagon_puzzle;

import java.util.Objects;

public class Move {
    public Position center; //selected node
    public int direction; //1-right(clockwise) ;  -1 left (counter-clockwise)

    public Move(Position selectedNode, int direction) throws IllegalArgumentException {
        if (Math.abs(direction)>1 || direction == 0){
            throw new IllegalArgumentException("Invalid direction"); //smth like this
        }
        this.center = selectedNode;
        this.direction = direction;
    }

    public Position getCenter() {
        return center;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move move)) return false;
        return direction == move.direction && center.equals(move.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, direction);
    }
}
