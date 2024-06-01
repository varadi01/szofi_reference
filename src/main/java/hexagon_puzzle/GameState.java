package hexagon_puzzle;

import puzzle.State;
import puzzle.solver.BreadthFirstSearch;

import java.util.*;


public class GameState implements State<Move> {

    public enum NodeColours {
        BLUE,
        RED,
        GREEN,
        EMPTY
    }

    public NodeColours[][] currentState;

    //sorfolytonosan?
    public static final NodeColours[][] startState = new NodeColours[5][9];
    public static final NodeColours[][] goalState = new NodeColours[5][9];
    static {
        //initialize arrays
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                startState[i][j] = NodeColours.EMPTY;
                goalState[i][j] = NodeColours.EMPTY;
            }
        }

        //set start state // do smarter pls
        startState[0][2] = NodeColours.RED;
        startState[0][4] = NodeColours.RED;
        startState[0][6] = NodeColours.RED;
        startState[1][1] = NodeColours.RED;
        startState[1][3] = NodeColours.RED;
        startState[1][5] = NodeColours.RED;
        startState[1][7] = NodeColours.BLUE;
        startState[2][0] = NodeColours.BLUE;
        startState[2][2] = NodeColours.BLUE;
        startState[2][4] = NodeColours.BLUE;
        startState[2][6] = NodeColours.BLUE;
        startState[2][8] = NodeColours.BLUE;
        startState[3][1] = NodeColours.BLUE;
        startState[3][3] = NodeColours.GREEN;
        startState[3][5] = NodeColours.GREEN;
        startState[3][7] = NodeColours.GREEN;
        startState[4][2] = NodeColours.GREEN;
        startState[4][4] = NodeColours.GREEN;
        startState[4][6] = NodeColours.GREEN;

        //set goal state
        goalState[0][2] = NodeColours.GREEN;
        goalState[0][4] = NodeColours.RED;
        goalState[0][6] = NodeColours.GREEN;
        goalState[1][1] = NodeColours.RED;
        goalState[1][3] = NodeColours.BLUE;
        goalState[1][5] = NodeColours.BLUE;
        goalState[1][7] = NodeColours.RED;
        goalState[2][0] = NodeColours.GREEN;
        goalState[2][2] = NodeColours.BLUE;
        goalState[2][4] = NodeColours.BLUE;
        goalState[2][6] = NodeColours.BLUE;
        goalState[2][8] = NodeColours.GREEN;
        goalState[3][1] = NodeColours.RED;
        goalState[3][3] = NodeColours.BLUE;
        goalState[3][5] = NodeColours.BLUE;
        goalState[3][7] = NodeColours.RED;
        goalState[4][2] = NodeColours.GREEN;
        goalState[4][4] = NodeColours.RED;
        goalState[4][6] = NodeColours.GREEN;
    }

    private static final Set<Move> legalMoves = new HashSet<>();

    //generate possible moves
    static {
        //refactor pls

        for (int i = 2; i <=3; i++) {
            legalMoves.add(new Move(new Position(2,i), 1));
            legalMoves.add(new Move(new Position(2,i), -1));
        }
        for (int i = 2; i <=4; i++) {
            legalMoves.add(new Move(new Position(3,i), 1));
            legalMoves.add(new Move(new Position(3,i), -1));
        }
        for (int i = 2; i <=3; i++) {
            legalMoves.add(new Move(new Position(4,i), 1));
            legalMoves.add(new Move(new Position(4,i), -1));
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
    public boolean isLegalMove(Move o) {
        if (!(o instanceof Move)) { return false; }//should throw smth
        return legalMoves.contains(o); //hashset?
    }

    public List<int[]> getNeighboursCoordinates(Position position){

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

        return neighbouringNodesCoordinates;
    }

    public List<NodeColours> getNeighbours(Position position) {
        //TODO
        //we store neighbours in clockwise order !?
        List<NodeColours> neighbours = new ArrayList<>(); //dunno

        //USING A SEPERATE COORDINATE OBJECT MIGHT BE BETTER

        List<int[]> neighbouringNodesCoordinates = getNeighboursCoordinates(position);

        //getterek pls
        for (int[] neighbourCoordinate : neighbouringNodesCoordinates) {
            neighbours.add(currentState[neighbourCoordinate[0]][neighbourCoordinate[1]]);
        }

        return neighbours;
    }

    public void setNeighbours(Position position, List<NodeColours> neighbours) {
        List<int[]> neighbouringNodesCoordinates = getNeighboursCoordinates(position);
        for (int i = 0; i < neighbours.size(); i++) {
            int x = neighbouringNodesCoordinates.get(i)[0];
            int y = neighbouringNodesCoordinates.get(i)[1];
            currentState[x][y] = neighbours.get(i);
        }
    }

    public List<NodeColours> permutateNodes(List<NodeColours> nodes, int direction) {
        List<NodeColours> permutation = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            permutation.set(i, nodes.get(i-direction));
        }

        return permutation;
    }

    @Override
    public void makeMove(Move o) {
        //TODO
        //o is move
        if (!(o instanceof Move)) { return ; }//should throw smth //redundant
        if (!isLegalMove(o)) { return; }
        //IS LEGAL MOVE

        //unpack move object
        Move move = (Move) o;

        //get neighbours
        List<NodeColours> neighbours = getNeighbours(move.getCenter());
        //permutate list of neighbours
        List<NodeColours> rotatedNeighbours = permutateNodes(neighbours, move.getDirection());

        //change neighbours
        setNeighbours(move.getCenter(), rotatedNeighbours);



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


    public static void main(String[] args) {
        System.out.println("solution");
        new BreadthFirstSearch<Move>().solveAndPrintSolution(new GameState());
        
    }
}
