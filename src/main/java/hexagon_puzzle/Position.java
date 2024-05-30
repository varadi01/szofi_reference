package hexagon_puzzle;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //viccy mukodes
    //GETEKKEL
    public static int[] convertPositionToCoordinates(Position position) {
        //TODO
        if (!(4-position.getX()>0)) {
            if (position.getX()==4){
                //negyedik sorban van
                return new int[] {position.x, 2 + (position.y-1) * 2}; //htis legal?
            }
            //otodik sorban van
            return new int[] {position.x, 3 + (position.y-1) * 2}; //htis legal?
        }
        int startI=4-position.x;
        return new int[] {position.x,startI + (position.y-1) * 2}; //htis legal?
    }

    //Ugyan ez visszafele?

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
