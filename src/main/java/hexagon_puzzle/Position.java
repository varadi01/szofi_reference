package hexagon_puzzle;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) throws IllegalArgumentException {
        //cant access legal moves bc its empty at this point, generated at runtime booboo
        /*
        boolean contained = false;
        for (Move move : PuzzleState.legalMoves){
            Position position = move.getCenter();
            if (position.getX() == x && position.getY() == y) { contained = true; break; }
        }
        if (!contained) { throw new IllegalArgumentException("Illegal move");}

         */

        this.x = x;
        this.y = y;
    }

    //viccy mukodes
    //GETEKKEL
    public static int[] convertPositionToCoordinates(Position position) {
        //TODO
        //WHAT
        if (position.getX()>=4){
            if (position.getX() == 5){
                //x =5
                return new int[] {position.getX() - 1, 2 + (position.getY()-1) * 2}; //htis legal?
            }
            //x = 4
            return new int[] {position.getX() - 1, 1 + (position.getY() - 1) * 2 }; //htis legal?
        }

        int startI=3-position.getX();

        return new int[] {position.getX() - 1, startI + (position.getY() - 1) * 2}; //htis legal?
    }

    //Ugyan ez visszafele?

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
