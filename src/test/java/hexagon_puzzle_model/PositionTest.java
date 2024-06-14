package hexagon_puzzle_model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    private Position position;
    private Position samePosition;
    private Position otherPosition;

    @BeforeEach
    void setUp() {
        position = new Position(2,2);
        samePosition = new Position(2,2);
        otherPosition = new Position(5,3);
    }

    @Test
    void convertPositionToCoordinates() {
        int[] coordinates = Position.convertPositionToCoordinates(position);
        assertEquals(1, coordinates[0]);
        assertEquals(3, coordinates[1]);

        coordinates = Position.convertPositionToCoordinates(otherPosition);
        assertEquals(4, coordinates[0]);
        assertEquals(6, coordinates[1]);

    }

    @Test
    void testEquals() {
        assertEquals(samePosition,position);
        assertEquals(samePosition,new Position(2,2));
        assertNotEquals(samePosition,otherPosition);
    }

    @Test
    void testHashCode() {
        assertNotEquals(position.hashCode(),otherPosition.hashCode());
        assertEquals(position.hashCode(),samePosition.hashCode());
    }
}