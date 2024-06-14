package hexagon_puzzle_model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleStateTest {

    private PuzzleState state;
    //private Position testPosition //HA

    @BeforeEach
    void setUp() {
        state = new PuzzleState();
        //testPosition = new Position(3,3);
    }

    //ilyet lehet?
    @Test
    void PuzzleState() {
        assertInstanceOf(PuzzleState.class, state);
        assertTrue(Arrays.deepEquals(PuzzleState.startState, state.getCurrentState()));
    }

    @Test
    void isSolved() {
        assertFalse(state.isSolved());
        state.setCurrentState(PuzzleState.goalState);
        assertTrue(state.isSolved());
    }

    @Test
    void isLegalMove() {
        assertTrue(state.isLegalMove(new Move(new Position(3,3), 1)));
        assertTrue(state.isLegalMove(new Move(new Position(2,2), -1)));
        assertTrue(state.isLegalMove(new Move(new Position(4,2), 1)));
        assertFalse(state.isLegalMove(new Move(new Position(5,3), 1)));
        assertFalse(state.isLegalMove(new Move(new Position(1,2), 1)));
    }

    /*
    @Test
    void getNeighboursCoordinates() {
        List<int[]> neighbouringNodesCoordinates = PuzzleState.getNeighboursCoordinates(new Position(3,3));

        assertEquals(6, neighbouringNodesCoordinates.size());

        assertArrayEquals(new int[]{1, 5}, neighbouringNodesCoordinates.get(0));
        assertArrayEquals(new int[]{2, 6}, neighbouringNodesCoordinates.get(1));
        assertArrayEquals(new int[]{3, 3}, neighbouringNodesCoordinates.get(3));
        assertArrayEquals(new int[]{1, 3}, neighbouringNodesCoordinates.get(5));
    }

     */

    /*
    @Test
    void getNeighbours() {
        List<PuzzleState.Node> neighbours = state.getNeighbours(new Position(3,3));
        assertEquals(6,neighbours.size()); //KÁVÉ

        assertEquals(state.getCurrentState()[1][5], neighbours.get(0));
        assertEquals(state.getCurrentState()[2][6], neighbours.get(1));
        assertEquals(state.getCurrentState()[1][3], neighbours.get(5));

    }

     */
/*
    @Test
    void setNeighbours() {
        List<PuzzleState.Node> originalNeighbours = state.getNeighbours(new Position(3,3));
        state.setNeighbours(new Position(3,3), originalNeighbours);
        assertEquals(originalNeighbours, state.getNeighbours(new Position(3,3)));

        //makeMove calls setNeighbours, the neighbours rotate around 3,3 clockwise
        state.makeMove(new Move(new Position(3,3), 1));

        List<PuzzleState.Node> newNeighbours = state.getNeighbours(new Position(3,3));
        assertNotEquals(newNeighbours, originalNeighbours);
        assertEquals(newNeighbours.size(), originalNeighbours.size());
        assertEquals(originalNeighbours.get(0), newNeighbours.get(1));
        assertEquals(originalNeighbours.get(4), newNeighbours.get(5));
    }

 */
/*
    @Test
    void permutateNodes() {
        List<PuzzleState.Node> nodes = state.getNeighbours(new Position(3,3));

        List<PuzzleState.Node> clockwiseRotatedNodes = PuzzleState.permutateNodes(nodes, 1);

        assertEquals(nodes.get(0), clockwiseRotatedNodes.get(1));
        assertEquals(nodes.get(1), clockwiseRotatedNodes.get(2));
        assertEquals(nodes.get(4), clockwiseRotatedNodes.get(5));
        assertEquals(nodes.get(5), clockwiseRotatedNodes.get(0));

        List<PuzzleState.Node> counterClockwiseRotatedNodes = PuzzleState.permutateNodes(nodes, -1);

        assertEquals(nodes.get(1), counterClockwiseRotatedNodes.get(0));
        assertEquals(nodes.get(2), counterClockwiseRotatedNodes.get(1));
        assertEquals(nodes.get(5), counterClockwiseRotatedNodes.get(4));
        assertEquals(nodes.get(0), counterClockwiseRotatedNodes.get(5));
    }

 */

    @Test
    void makeMove() {
        Move move = new Move(new Position(3,3), 1);
        PuzzleState otherState = new PuzzleState();

        otherState.makeMove(move);
        assertNotEquals(state, otherState);

        state.makeMove(move);
        assertEquals(state, otherState);

        assertThrows(IllegalArgumentException.class, () -> state.makeMove(null));

        Move illegalMove = new Move(new Position(3,3), 0);
        assertThrows(IllegalArgumentException.class, () -> state.makeMove(illegalMove));

        Move illegalMove2 = new Move(new Position(1,2), 1);
        assertThrows(IllegalArgumentException.class, () -> state.makeMove(illegalMove2));
    }

    @Test
    void getLegalMoves() {
        Set<Move> legalMoves = state.getLegalMoves();
        for (Move move : legalMoves) {
            assertTrue(state.isLegalMove(move));
        }

        PuzzleState otherState = state.clone();
        otherState.makeMove(new Move(new Position(3,3), 1));
        assertEquals(state.getLegalMoves(), otherState.getLegalMoves());
    }

    @Test
    void testClone() {
        PuzzleState clone = state.clone();
        assertEquals(state, clone);
        clone.setCurrentState(PuzzleState.goalState);
        assertNotEquals(state, clone);
    }

    /*
    @Test
    void makeDeepCopy(){
        PuzzleState.Node[][] copy = PuzzleState.makeDeepCopy(state.getCurrentState());
        assertTrue(Arrays.deepEquals(state.getCurrentState(), copy));

        copy[0][0] = PuzzleState.Node.BLUE;
        assertFalse(Arrays.deepEquals(state.getCurrentState(), copy));

    }

     */

    @Test
    void testEquals() {
        assertEquals(state, state);
        assertEquals(state, new PuzzleState());

        PuzzleState clone = state.clone();
        assertEquals(state, clone);

        clone.setCurrentState(PuzzleState.goalState);
        assertNotEquals(state, clone);
    }

    @Test
    void testHashCode() {
        assertEquals(state.hashCode(), new PuzzleState().hashCode());

        PuzzleState clone = state.clone();
        assertEquals(state.hashCode(), clone.hashCode());

        clone.setCurrentState(PuzzleState.goalState);
        assertNotEquals(state.hashCode(), clone.hashCode());
    }
}