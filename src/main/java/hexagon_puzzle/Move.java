package hexagon_puzzle;

public class Move {
    public Position selectedNode;
    public int direction; //1-right(clockwise) ;  -1 left (counter-clockwise)

    public Move(Position selectedNode, int direction) {
        this.selectedNode = selectedNode;
        this.direction = direction;
    }
}
