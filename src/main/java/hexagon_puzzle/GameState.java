package hexagon_puzzle;

import puzzle.State;

import java.util.*;


public class GameState implements State {

    public enum NodeColours {
        BLUE,
        RED,
        GREEN
    }

    public NodeColours[][] currentState;

    //sorfolytonosan?
    public static final NodeColours[][] startState = new NodeColours[][]{};
    public static final NodeColours[][] goalState = new NodeColours[][]{};


    private static final Set<Move> legalMoves = new HashSet<>();
    //TODO
    //generate possible moves //BAD
    static {
        //NOPE NOPE NOPE
        //only internal nodes can be the axis
        //refactor pls
        //first line of nodes
        for (int i = 1; i <=3; i++) {
                legalMoves.add(new Move(new Position(1,i), 1));
                legalMoves.add(new Move(new Position(1,i), -1));
        }
        //second line of nodes ...
        for (int i = 1; i <=4; i++) {
            legalMoves.add(new Move(new Position(2,i), 1));
            legalMoves.add(new Move(new Position(2,i), -1));
        }
        for (int i = 1; i <=5; i++) {
            legalMoves.add(new Move(new Position(3,i), 1));
            legalMoves.add(new Move(new Position(3,i), -1));
        }
        for (int i = 1; i <=4; i++) {
            legalMoves.add(new Move(new Position(4,i), 1));
            legalMoves.add(new Move(new Position(4,i), -1));
        }
        for (int i = 1; i <=3; i++) {
            legalMoves.add(new Move(new Position(5,i), 1));
            legalMoves.add(new Move(new Position(5,i), -1));
        }
    }


    public GameState() {
        currentState = startState;
    }

    @Override
    public boolean isSolved() {
        return Arrays.deepEquals(currentState, goalState);
    }

    @Override
    public boolean isLegalMove(Object o) {
        if (!(o instanceof Move)) { return false; }//should throw smth
        return legalMoves.contains(o); //hashset?
    }

    public List<NodeColours> getNeighbours(Position position) {
        //TODO
        //we store neighbours in clockwise order !?
        List<NodeColours> neighbours = new ArrayList<>(); //dunno

        //USING A SEPERATE COORDINATE OBJECT MIGHT BE BETTER
        int[] centerNodeCoordinates = Position.convertPositionToCoordinates(position);
        //generate neighbours' coordinates
        List<int[]> neighbouringNodesCoordinates = new ArrayList<>(); //dunno
        //smth like this
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]-1,centerNodeCoordinates[1]+1}); //top right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0],centerNodeCoordinates[1]+2}); //right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]+1,centerNodeCoordinates[1]+1}); //bottom right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]+1,centerNodeCoordinates[1]-1}); //bottom left neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0],centerNodeCoordinates[1]-2}); //left neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]-1,centerNodeCoordinates[1]-1}); //top left neighbour

        for (int[] neighbourCoordinate : neighbouringNodesCoordinates) {
            neighbours.add(currentState[neighbourCoordinate[0]][neighbourCoordinate[1]]);
        }

        return neighbours;
    }

    public void setNeighbours(Position position, List<NodeColours> neighbours) {
        //TODO
    }

    @Override
    public void makeMove(Object o) {
        //TODO
        //o is move
        if (!(o instanceof Move)) { return ; }//should throw smth
        if (!isLegalMove(o)) { return; }
        //IS LEGAL MOVE

        //unpack move object
        Move move = (Move) o;

        //get neighbours
        //permutate list of neighbours
        //change neighbours

        //somehow
        //that's it, check for exceptions
    }

    @Override
    public Set<Move> getLegalMoves() {
        return legalMoves;
    }

    @Override
    public GameState clone() {
        GameState clonedState;
        try {
            clonedState = (GameState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        //probably safe? cloned objects' references must be primitive or immutable
        //if not; deepClone()
        clonedState.currentState =this.currentState.clone();
        return clonedState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState other)) return false;
        return Objects.deepEquals(currentState, other.currentState);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(currentState);
    }
}
