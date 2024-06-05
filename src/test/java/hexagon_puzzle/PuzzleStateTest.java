package hexagon_puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleStateTest {

    private PuzzleState state;

    @BeforeEach
    void setUp() {
        state = new PuzzleState();

    }

    //ilyet lehet?
    @Test
    void PuzzleState() {
        assertEquals(state, new PuzzleState());
        assertTrue(Arrays.deepEquals(state.getCurrentState(), PuzzleState.startState));
        assertFalse(Arrays.deepEquals(PuzzleState.startState,PuzzleState.goalState)); //huh
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
        //throws kell
        /*
        assertFalse(state.isLegalMove(new Move(new Position(3,3), 2)));
        assertFalse(state.isLegalMove(new Move(new Position(3,3), 0)));
        assertFalse(state.isLegalMove(new Move(new Position(3,3), -2)));

         */
        //ha nem moveot adok!!
    }

    @Test
    void getNeighboursCoordinates() {
        List<int[]> neighbouringNodesCoordinates = PuzzleState.getNeighboursCoordinates(new Position(3,3));

        assertEquals(neighbouringNodesCoordinates.size(),6);

        assertArrayEquals(neighbouringNodesCoordinates.get(0), new int[]{1, 5});
        assertArrayEquals(neighbouringNodesCoordinates.get(1), new int[]{2, 6});
        assertArrayEquals(neighbouringNodesCoordinates.get(3), new int[]{3, 3});
        assertArrayEquals(neighbouringNodesCoordinates.get(5), new int[]{1, 3});
    }

    @Test
    void getNeighbours() {
        List<PuzzleState.Node> neighbours = state.getNeighbours(new Position(3,3));
        assertEquals(6,neighbours.size()); //KÁVÉ

        assertEquals(state.getCurrentState()[1][5], neighbours.get(0));
        assertEquals(state.getCurrentState()[2][6], neighbours.get(1));
        assertEquals(state.getCurrentState()[1][3], neighbours.get(5));

    }

    @Test
    void setNeighbours() {
        List<PuzzleState.Node> originalNeighbours = state.getNeighbours(new Position(3,3));
        state.setNeighbours(new Position(3,3), originalNeighbours);
        assertEquals(state.getNeighbours(new Position(3,3)), originalNeighbours);

        state.makeMove(new Move(new Position(3,3), 1));
        List<PuzzleState.Node> newNeighbours = state.getNeighbours(new Position(3,3));
        assertNotEquals(newNeighbours, originalNeighbours);
        assertEquals(newNeighbours.size(), originalNeighbours.size());
        assertEquals(newNeighbours.get(1), originalNeighbours.get(0));
        assertEquals(newNeighbours.get(5), originalNeighbours.get(4));
    }

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

    @Test
    void makeMove() {
        Move move = new Move(new Position(3,3), 1);
        PuzzleState otherState = new PuzzleState();

        otherState.makeMove(move);
        assertNotEquals(state, otherState);
        state.makeMove(move);
        assertEquals(state, otherState);
    }

    @Test
    void getLegalMoves() {
        //mind benne van e és mind az e
        Set<Move> legalMoves = state.getLegalMoves();
        for (Move move : legalMoves) {
            assertTrue(state.isLegalMove(move));
        }

        PuzzleState otherState = state.clone();
        otherState.makeMove(new Move(new Position(3,3), 1));
        assertEquals(state.getLegalMoves(), otherState.getLegalMoves()); //huh? bf?
    }

    @Test
    void testClone() {
        PuzzleState clone = state.clone();
        assertEquals(state, clone);
        clone.setCurrentState(PuzzleState.goalState);
        assertNotEquals(state, clone);
    }

    @Test
    void testEquals() {
        assertEquals(state, state);
        assertEquals(state, new PuzzleState());
        PuzzleState clone = state.clone();
        clone.setCurrentState(PuzzleState.goalState);
        assertNotEquals(state, clone);
        //...
    }

    @Test
    void testHashCode() {
        assertEquals(state.hashCode(), new PuzzleState().hashCode());
        assertEquals(state.hashCode(), state.hashCode());
    }
}