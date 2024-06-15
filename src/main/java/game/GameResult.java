package game;

public class GameResult {
    private final String playerName;
    private final int steps;

    public GameResult(String playerName, int steps) {
        this.playerName = playerName;
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return playerName + "       -        " + steps;
    }
}
