package game;

import hexagon_puzzle_model.Move;
import hexagon_puzzle_model.Position;
import hexagon_puzzle_model.PuzzleState;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public class Tile extends Polygon {

        private final static double radius = 70; //the distance from hexagon center to corner
        public final static double d = Math.sqrt(radius * radius * 0.75); //the distance from hexagon center to middle of the axis
        public final static double tileHeight = 2 * radius;
        public final static double tileWidth = 2 * d;

        Tile(double x, double y, int id) {
            getPoints().addAll(
                    x, y,
                    x, y + radius,
                    x + d, y + radius * 1.5,
                    x + tileWidth, y + radius,
                    x + tileWidth, y,
                    x + d, y - radius * 0.5
            );

            setStrokeWidth(4);
            setStroke(Color.LIGHTGREY);

            setId(String.valueOf(id));

            setOnMouseClicked(e -> onTileClicked(this));
        }
    }

    @FXML
    private AnchorPane board;
    @FXML
    private Label stepCounter;
    private List<Polygon> playfieldTiles = new ArrayList<>();
    public PuzzleState state;
    private Tile selectedTile;

    public void resetBoard(){
        state = new PuzzleState();
        updateBoard();

        stepCounter.setText("0");

        if (!(selectedTile==null)){
            selectedTile.setScaleX(1);
            selectedTile.setScaleY(1);
            selectedTile.setStroke(Color.LIGHTGREY);
        }
        selectedTile=null;

        resultScreen.setVisible(false);
        GameResultManager.resetResults();

        Logger.info("Game board was reset");
    }

    public void updateBoard(){
        List<Position> positionsOfPlayfield = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            positionsOfPlayfield.add(new Position(1, i));
        }for (int i = 1; i <= 4; i++) {
            positionsOfPlayfield.add(new Position(2, i));
        }for (int i = 1; i <= 5; i++) {
            positionsOfPlayfield.add(new Position(3, i));
        }for (int i = 1; i <= 4; i++) {
            positionsOfPlayfield.add(new Position(4, i));
        }for (int i = 1; i <= 3; i++) {
            positionsOfPlayfield.add(new Position(5, i));
        }

        int c=0;

        for (Position pos : positionsOfPlayfield){
            int[] coor = Position.convertPositionToCoordinates(pos);
            PuzzleState.Node node = state.getCurrentState()[coor[0]][coor[1]];

            if (node == PuzzleState.Node.BLUE) {
                playfieldTiles.get(c).setFill(Color.BLUE); }
            if (node == PuzzleState.Node.GREEN) {
                playfieldTiles.get(c).setFill(Color.FORESTGREEN); }
            if (node == PuzzleState.Node.RED) {
                playfieldTiles.get(c).setFill(Color.RED); }
            c+=1;
        }
    }

    @FXML
    private TextField alias;
    @FXML
    private AnchorPane aliasQueryPanel;
    private String username;

    private void aliasQuery(){
        aliasQueryPanel.setVisible(true);
        aliasQueryPanel.toFront();
        Logger.info("showed alias query panel");

        board.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode()== KeyCode.ENTER){
                if (!alias.getText().isBlank()){
                    username = alias.getText();
                }
                else {
                    Logger.info("user did not specify an alias. was named anonymous");
                    username="anonymous";
                }

                Logger.info("user alias: {}", username);

                aliasQueryPanel.setVisible(false);
            }
        });
    }

    @FXML
    private AnchorPane resultScreen;
    @FXML
    private ListView<String> highscoreList;
    @FXML
    private Label yourScore;

    private void onComplete(){
        resultScreen.setVisible(true);
        resultScreen.toFront();

        Logger.info("Puzzle solved, showed results panel");

        GameResult currentResult = new GameResult(username, Integer.parseInt(stepCounter.getText()));
        yourScore.setText(currentResult.toString());

        List<GameResult> highscores = GameResultManager.getBestResults();
        GameResultManager.addResult(currentResult);

        if (highscores.isEmpty()){
            Logger.info("no previous results were stored");
        }

        highscoreList.getItems().removeAll(highscoreList.getItems()); //the list retains items after beginning new game, we need to empty it

        //display of highscore could be made much nicer using a cell factory
        for (GameResult result : highscores){
            highscoreList.getItems().add(result.toString());
            System.out.println(result);
        }

    }

    private int dir;
    private Position position;

    public void handleKeyEvent(KeyCode code){
        if (code.getCode() == KeyCode.ESCAPE.getCode()){
            if (!(selectedTile==null)){
                selectedTile.setScaleX(1);
                selectedTile.setScaleY(1);
                selectedTile.setStroke(Color.LIGHTGREY);
            }

            dir=0;
            selectedTile=null;
        }

        if (selectedTile==null){
            return;
        }
        if (code.getCode() == KeyCode.LEFT.getCode() || code.getCode() == KeyCode.A.getCode()){
            dir=-1;
        }
        else if (code.getCode() == KeyCode.RIGHT.getCode() || code.getCode() == KeyCode.D.getCode()){
            dir=1;
        }

        if (dir!=0){
            makeMove(new Move(position, dir));
        }
        dir=0;
    }

    public void makeMove(Move move){
        state.makeMove(move);
        updateBoard();

        stepCounter.setText(String.valueOf((Integer.parseInt(stepCounter.getText()) + 1)));

        if (state.isSolved()){
            onComplete();
        }
    }

    public void translateTileSelection(int id){
        List<Integer> ids = List.of(5,6,9,10,11,14,15);

        int index = ids.indexOf(id);

        List<Position> positions = List.of(
                new Position(2,2), new Position(2,3),
                new Position(3,2), new Position(3,3), new Position(3,4),
                new Position(4,2), new Position(4,3));
        position = positions.get(index);
    }

    public void onTileClicked(Tile clickedTile){
        if (!(selectedTile==null)){
            selectedTile.setScaleX(1);
            selectedTile.setScaleY(1);
            selectedTile.setStroke(Color.LIGHTGREY);
        }

        int index = Integer.parseInt(clickedTile.getId());
        List<Integer> legalTilesToMoveFrom = new ArrayList<>(List.of(5, 6, 9, 10, 11, 14, 15));

        if (!legalTilesToMoveFrom.contains(index)){
            selectedTile=null;
            return;
        }

        selectedTile = clickedTile;
        selectedTile.setScaleX(1.05);
        selectedTile.setScaleY(1.05);
        selectedTile.setStroke(Color.SLATEGRAY);
        selectedTile.toFront();


        translateTileSelection(index);
    }

    void postInitialize(){
        aliasQuery();
        board.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            handleKeyEvent(key.getCode());
        });
        Logger.info("Event listener was registered");
    }

    @FXML
    private void initialize(){
        int rowCount = 5;
        int tilesPerRow = 5;
        int xStartOffset = 360;
        int yStartOffset = 100;

        int c =1;
        int diff = 2;
        for (int i = 0; i < rowCount; i++) {
            double xOffset = xStartOffset;

            //inset of first and last line
            if (Math.abs(diff)==2 ){
                xOffset=xStartOffset+(81 * 1.5); //this specific number looks the best
            }

            for (int j = 0; j < tilesPerRow-Math.abs(diff); j++) {
                double x = j * Tile.tileWidth + (i % 2) * Tile.d + xOffset;
                double y = i * Tile.tileHeight * 0.75 + yStartOffset;

                Polygon tile = new Tile(x, y, c); //c is the id/fx:id of the tiles
                playfieldTiles.add(tile);

                board.getChildren().add(tile);
                c+=1;
            }
            diff-=1;
        }

        Logger.info("Initialized playfield");

        state = new PuzzleState();
        updateBoard();
    }


}
