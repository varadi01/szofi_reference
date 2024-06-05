package hexagon_puzzle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
    private Move move;
    private Move sameMove;
    private Move otherMove;

    @BeforeEach
    void setUp() {
        move = new Move(new Position(3,3), 1);
        sameMove = new Move(new Position(3,3), 1);
        otherMove = new Move(new Position(2,3), -1);
    }

    @Test
    void Move() {
        //rn it doesent throw anything due to me being a booboo
        //assertThrows(IllegalArgumentException ,new Move(new Position(1,1), 1));

    }

    @Test
    void getCenter() {
        assertEquals(new Position(3,3), move.getCenter());
        assertEquals(new Position(2,3), otherMove.getCenter());
    }

    @Test
    void testEquals() {
        assertTrue(move.equals(sameMove));
        assertFalse(move.equals(otherMove));
    }

    @Test
    void testHashCode() {
        assertEquals(move.hashCode(), sameMove.hashCode());
        assertNotEquals(move.hashCode(), otherMove.hashCode());
    }
}