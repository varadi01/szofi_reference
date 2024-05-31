package hexagon_puzzle;

public class Move {
    public Position center; //selected node
    public int direction; //1-right(clockwise) ;  -1 left (counter-clockwise)

    public Move(Position selectedNode, int direction) {
        this.center = selectedNode;
        this.direction = direction;
    }

    public Position getCenter() {
        return center;
    }

    public int getDirection() {
        return direction;
    }
}
