package hexagon_puzzle_model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleStateTest {

    private PuzzleState state;

    @BeforeEach
    void setUp() {
        state = new PuzzleState();
    }

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
    void isLegalMove() {
        assertTrue(state.isLegalMove(new Move(new Position(3,3), 1)));
        assertTrue(state.isLegalMove(new Move(new Position(2,2), -1)));
        assertTrue(state.isLegalMove(new Move(new Position(4,2), 1)));
        assertFalse(state.isLegalMove(new Move(new Position(5,3), 1)));
        assertFalse(state.isLegalMove(new Move(new Position(1,2), 1)));
    }

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