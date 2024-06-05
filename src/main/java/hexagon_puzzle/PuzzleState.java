package hexagon_puzzle;

import puzzle.State;
import puzzle.solver.BreadthFirstSearch;

import java.util.*;


public class PuzzleState implements State<Move> {

    public enum Node {
        BLUE,
        RED,
        GREEN,
        EMPTY
    }

    private Node[][] currentState;

    public static final Node[][] startState = new Node[5][9];
    public static final Node[][] goalState = new Node[5][9];

    //initialize arrays
    static {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                startState[i][j] = Node.EMPTY;
                goalState[i][j] = Node.EMPTY;
            }
        }

        //set start state // do smarter pls
        startState[0][2] = Node.RED;
        startState[0][4] = Node.RED;
        startState[0][6] = Node.RED;
        startState[1][1] = Node.RED;
        startState[1][3] = Node.RED;
        startState[1][5] = Node.RED;
        startState[1][7] = Node.BLUE;
        startState[2][0] = Node.BLUE;
        startState[2][2] = Node.BLUE;
        startState[2][4] = Node.BLUE;
        startState[2][6] = Node.BLUE;
        startState[2][8] = Node.BLUE;
        startState[3][1] = Node.BLUE;
        startState[3][3] = Node.GREEN;
        startState[3][5] = Node.GREEN;
        startState[3][7] = Node.GREEN;
        startState[4][2] = Node.GREEN;
        startState[4][4] = Node.GREEN;
        startState[4][6] = Node.GREEN;

        //set goal state
        goalState[0][2] = Node.GREEN;
        goalState[0][4] = Node.RED;
        goalState[0][6] = Node.GREEN;
        goalState[1][1] = Node.RED;
        goalState[1][3] = Node.BLUE;
        goalState[1][5] = Node.BLUE;
        goalState[1][7] = Node.RED;
        goalState[2][0] = Node.GREEN;
        goalState[2][2] = Node.BLUE;
        goalState[2][4] = Node.BLUE;
        goalState[2][6] = Node.BLUE;
        goalState[2][8] = Node.GREEN;
        goalState[3][1] = Node.RED;
        goalState[3][3] = Node.BLUE;
        goalState[3][5] = Node.BLUE;
        goalState[3][7] = Node.RED;
        goalState[4][2] = Node.GREEN;
        goalState[4][4] = Node.RED;
        goalState[4][6] = Node.GREEN;
    }

    public static final Set<Move> legalMoves = new HashSet<>();
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

    public PuzzleState() {
        setCurrentState(startState);
    }

    public Node[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Node[][] state) {
        this.currentState = makeDeepCopy(state);
    }

    @Override
    public boolean isSolved() {
        return Arrays.deepEquals(getCurrentState(), goalState); //hmmm
    }

    @Override
    public boolean isLegalMove(Move o) {
        if (o == null) { return false; }//should throw smth
        return legalMoves.contains(o); //hashset?
    }

    public static List<int[]> getNeighboursCoordinates(Position position){
        int[] centerNodeCoordinates = Position.convertPositionToCoordinates(position);

        List<int[]> neighbouringNodesCoordinates = new ArrayList<>(); //dunno

        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]-1,centerNodeCoordinates[1]+1}); //top right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0],centerNodeCoordinates[1]+2}); //right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]+1,centerNodeCoordinates[1]+1}); //bottom right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]+1,centerNodeCoordinates[1]-1}); //bottom left neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0],centerNodeCoordinates[1]-2}); //left neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]-1,centerNodeCoordinates[1]-1}); //top left neighbour

        return neighbouringNodesCoordinates;
    }

    public List<Node> getNeighbours(Position position) {
        //we store neighbours in clockwise order
        List<Node> neighbours = new ArrayList<>(); //dunno

        List<int[]> neighbouringNodesCoordinates = getNeighboursCoordinates(position);

        for (int[] neighbourCoordinate : neighbouringNodesCoordinates) {
            int x = neighbourCoordinate[0];
            int y = neighbourCoordinate[1];
            neighbours.add(getCurrentState()[x][y]);
        }

        return neighbours;
    }


    public void setNeighbours(Position position, List<Node> neighbours) {
        List<int[]> neighbouringNodesCoordinates = getNeighboursCoordinates(position);
        Node[][] newState = makeDeepCopy(this.getCurrentState());

        for (int i = 0; i < neighbours.size(); i++) {
            int x = neighbouringNodesCoordinates.get(i)[0];
            int y = neighbouringNodesCoordinates.get(i)[1];

            newState[x][y] = neighbours.get(i);
        }
        this.setCurrentState(newState);
    }

    private Node[][] makeDeepCopy(Node[][] state) {
        return Arrays.stream(state)
                .map( s -> Arrays.copyOf(s, s.length))
                .toArray(Node[][]::new);
    }

    public static List<Node> permutateNodes(List<Node> nodes, int direction) {
        List<Node> permutation = new ArrayList<>();
        //placegolder pls refactor
        for (int i = 0; i < 6; i++) {
            permutation.add(Node.EMPTY);
        }
        //A NODESOT IS KEVERI????

        for (int i = 0; i <= nodes.size()-1; i++) {
            //direction: 1 -> clockwise; -1 -> counter-clockwise
            if (i == 5 && direction == -1){
                permutation.set(i, nodes.getFirst());
                continue;
            }

            if (i == 0 && direction == 1){
                permutation.set(i, nodes.getLast());
                continue;
            }

            permutation.set(i, nodes.get(i-direction));
        }

        return permutation;
    }

    @Override
    public void makeMove(Move move) {
        if (move == null) { return ; }//should throw smth
        if (!isLegalMove(move)) { return; }

        //get neighbours
        List<Node> neighbours = getNeighbours(move.getCenter());
        //permutate list of neighbours
        List<Node> rotatedNeighbours = permutateNodes(neighbours, move.getDirection());
        //change neighbours
        setNeighbours(move.getCenter(), rotatedNeighbours);

        //that's it, check for exceptions
    }

    @Override
    public Set<Move> getLegalMoves() {
        return legalMoves;
    }

    @Override
    public PuzzleState clone() {
        PuzzleState clonedState;
        try {
            clonedState = (PuzzleState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        clonedState.currentState = makeDeepCopy(this.currentState);
        return clonedState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleState other)) return false;
        return Arrays.deepEquals(this.currentState, other.currentState);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(currentState);
    }

    public static void main(String[] args) {
        System.out.println("print debugging");

        System.out.println("solution");
        new BreadthFirstSearch<Move>().solveAndPrintSolution(new PuzzleState());

    }
}
