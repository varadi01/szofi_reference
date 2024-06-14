package hexagon_puzzle_model;

import org.tinylog.Logger;
import puzzle.State;
import puzzle.solver.BreadthFirstSearch;

import java.util.*;

/**
 * Class representing the state of the puzzle and providing means of manipulating said state.
 * Although the puzzle consists of hexagons tiled next to each other with varying numbers of tiles in a line,
 * we use a 5 x 9 array to represent a given state of the puzzle for mathematical simplicity reasons.
 */
public class PuzzleState implements State<Move> {

    /**
     * Enum representing the state of nodes, which make up the puzzle's state.
     */
    public enum Node {
        /**
         * do i need to wirte these aswell?
         */
        BLUE,
        /**
         * do i need to wirte these aswell?
         */
        RED,
        /**
         * do i need to wirte these aswell?
         */
        GREEN,
        /**
         * do i need to wirte these aswell?
         */
        EMPTY
    }

    private Node[][] currentState;

    /**
     * Array representing the starting state of the puzzle.
     */
    public static final Node[][] startState = new Node[5][9];
    /**
     * Array representing the goal state of the puzzle.
     */
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

    private static final Set<Move> legalMoves = new HashSet<>();

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

    /**
     * Creates a <code>PuzzleState</code> object, creating a new instance of the puzzle.
     */
    public PuzzleState() {
        setCurrentState(startState);
        Logger.info("Started new game");
    }

    /**
     * {@return the current state of the puzzle}
     */
    public Node[][] getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state of the puzzle to <code>state</code>.
     *
     * @param state the desired state
     */
    public void setCurrentState(Node[][] state) {
        this.currentState = makeDeepCopy(state);
    }

    /**
     * {@return true if the puzzle is solved, false otherwise}
     */
    @Override
    public boolean isSolved() {
        if (Arrays.deepEquals(getCurrentState(), goalState)){
            //puzzle solved strictly
            Logger.info("Found strict solution");
            return true;
        }

        ArrayList<Position> poses = new ArrayList<>();
        poses.add(new Position(2,2));
        poses.add(new Position(2,3));
        poses.add(new Position(3,2));
        poses.add(new Position(3,3));
        poses.add(new Position(3,4));
        poses.add(new Position(4,2));
        poses.add(new Position(4,3));

        for (Position pos : poses){
            int[] coor = Position.convertPositionToCoordinates(pos);
            if (getCurrentState()[coor[0]][coor[1]] != Node.BLUE){
                return false;
            }
        }
        Logger.info("Found solution");
        return true;
    }

    /**
     * Determines if the passed <code>Move</code> object is a move that can be made on the puzzle's current state.
     *
     * @param o the move to be checked
     * @return true if the move is legal to be made, false otherwise
     */
    @Override
    public boolean isLegalMove(Move o) {
        if (o == null) { return false; }
        return legalMoves.contains(o); //hashset?
    }

    //made this private
    private static List<int[]> getNeighboursCoordinates(Position position){
        int[] centerNodeCoordinates = Position.convertPositionToCoordinates(position);

        List<int[]> neighbouringNodesCoordinates = new ArrayList<>(); //dunno //may be linked list eg

        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]-1,centerNodeCoordinates[1]+1}); //top right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0],centerNodeCoordinates[1]+2}); //right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]+1,centerNodeCoordinates[1]+1}); //bottom right neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]+1,centerNodeCoordinates[1]-1}); //bottom left neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0],centerNodeCoordinates[1]-2}); //left neighbour
        neighbouringNodesCoordinates.add(new int[] {centerNodeCoordinates[0]-1,centerNodeCoordinates[1]-1}); //top left neighbour

        return neighbouringNodesCoordinates;
    }

    private List<Node> getNeighbours(Position position) {
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

    private void setNeighbours(Position position, List<Node> neighbours) {
        List<int[]> neighbouringNodesCoordinates = getNeighboursCoordinates(position);
        Node[][] newState = makeDeepCopy(this.getCurrentState());

        for (int i = 0; i < neighbours.size(); i++) {
            int x = neighbouringNodesCoordinates.get(i)[0];
            int y = neighbouringNodesCoordinates.get(i)[1];

            newState[x][y] = neighbours.get(i);
        }
        this.setCurrentState(newState);
    }

    private static Node[][] makeDeepCopy(Node[][] state) {
        return Arrays.stream(state)
                .map( s -> Arrays.copyOf(s, s.length))
                .toArray(Node[][]::new);
    }

    private static List<Node> permutateNodes(List<Node> nodes, int direction) {
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

    /**
     * Attempts to make a <code>Move</code> on the state of the puzzle.
     * If the move is legal, it is made.
     *
     * @param move the <code>Move</code> to be made
     */
    @Override
    public void makeMove(Move move) {
        if (move == null) {
            Logger.error("An illegal move was trying to be made: the move was null");
            throw new IllegalArgumentException("Move is null");
        }
        if (!isLegalMove(move)) {
            Logger.error("An illegal move was trying to be made: {}",move);
            throw new IllegalArgumentException("Illegal move");
            //logger
        } // throws

        //get neighbours
        List<Node> neighbours = getNeighbours(move.getCenter());
        //permutate list of neighbours
        List<Node> rotatedNeighbours = permutateNodes(neighbours, move.getDirection());
        //change neighbours
        setNeighbours(move.getCenter(), rotatedNeighbours);

        //that's it, check for exceptions
    }

    /**
     * {@return a set of {@code Move} objects that are legal to be made}
     */
    @Override
    public Set<Move> getLegalMoves() {
        Set<Move> moves = new HashSet<>();
        for (Move move : legalMoves){
            moves.add(move);
        }
        return moves;
    }

    /**
     * {@return a copy of the {@code PuzzleState} object}
     */
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

    /**
     * Determines the equality of <code>this</code> and <code>o</code>.
     *
     * {@return true if the objects are equal, false otherwise}
     * @param o the object to be compared to <code>this</code>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleState other)) return false;
        return Arrays.deepEquals(this.currentState, other.currentState);
    }

    /**
     * {@return the hash code of the {@code PuzzleState}}
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(currentState);
    }

    /**
     * Returns a string representation of the <code>PuzzleState</code>.
     * Used for presentation and readability reasons.
     *
     * @return a string representation of the <code>PuzzleState</code>
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.getCurrentState()[i][j]!=Node.EMPTY){
                    sb.append(this.getCurrentState()[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        sb.append("-----------------------------------");
        return sb.toString();
    }

    public static void main(String[] args) {
        new BreadthFirstSearch<Move>().solveAndPrintSolution(new PuzzleState());
    }
}
