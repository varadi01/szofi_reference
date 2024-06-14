package game;

import com.sun.javafx.collections.ImmutableObservableList;
import hexagon_puzzle_model.Move;
import hexagon_puzzle_model.Position;
import hexagon_puzzle_model.PuzzleState;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public class Tile extends Polygon {

        private final static double r = 70; // the inner radius from hexagon center to outer corner
        public final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
        public final static double TILE_HEIGHT = 2 * r;
        public final static double TILE_WIDTH = 2 * n;

        Tile(double x, double y, int id) {
            // creates the polygon using the corner coordinates
            getPoints().addAll(
                    x, y,
                    x, y + r,
                    x + n, y + r * 1.5,
                    x + TILE_WIDTH, y + r,
                    x + TILE_WIDTH, y,
                    x + n, y - r * 0.5
            );

            // set up the visuals and a click listener for the tile
            setStrokeWidth(4);
            setStroke(Color.LIGHTGREY);


            //works but could be prettier
            setId(String.valueOf(id));
            setOnMouseClicked(e -> onTileClicked(this));
        }
    }

    private boolean listenerAdded = false; //do smarter pls

    private final double TILE_WIDTH = Tile.TILE_WIDTH;
    private final double TILE_HEIGHT = Tile.TILE_HEIGHT;

    @FXML
    private AnchorPane board;

    public PuzzleState state;

    private List<Polygon> tiles = new ArrayList<>();
    private Tile selectedTile;

    //step counter //reset button
    @FXML
    private Label stepCounter;

    public void resetBoard(){
        state = new PuzzleState(); //hm???
        updatePlayField();
        stepCounter.setText("0");
        selectedTile=null;
        resultScreen.setVisible(false);
        GameResultManager.resetResults();
        Logger.info("Game board was reset");
        //more?
    }

    //playfield today
    //dunno

    //login screen
    private String username;
    @FXML
    private TextField alias;
    @FXML
    private AnchorPane loginPanel;

    private void userthing(){
        //needs to be called after init
        loginPanel.setVisible(true);
        loginPanel.toFront();
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

                System.out.println(username);

                loginPanel.setVisible(false);
                //might wanna remove this after getting name
            }
        });



    }

    //result screen, //result storing

    @FXML
    private AnchorPane resultScreen;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label yourScore;

    private void onComplete(){
        //set fxid for window
        //set visble
        resultScreen.setVisible(true);
        resultScreen.toFront();

        Logger.info("Puzzle solved, showed reults panel");

        //dunno about presentation
        //do record storing
        GameResult currentResult = new GameResult(username, Integer.parseInt(stepCounter.getText()));
        yourScore.setText(currentResult.toString());

        List<GameResult> highscores = GameResultManager.getBestResults(); //duplicates
        GameResultManager.addResult(currentResult); //wanna switch this cus it makes sense, bestresult is null somehow

        if (highscores.isEmpty()){
            Logger.info("no previous results were stored");
        }
        //get into list
        listView.getItems().removeAll(listView.getItems()); //not pretty but it retains items after beginning new game
        //I know you could make this much nicer using a cell factory, but I am not a ui/ux person
        for (GameResult result : highscores){
            listView.getItems().add(result.toString());
            System.out.println(result);
        }

    }

    private int dir;
    private Position position;

    public void updatePlayField(){
        //TODO
        //call after made move
        //re render tiles

        /*
        int c=0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                PuzzleState.Node node = state.getCurrentState()[i][j];
                if (node == PuzzleState.Node.BLUE) {tiles.get(c).setFill(Color.BLUE); }
                if (node == PuzzleState.Node.GREEN) {tiles.get(c).setFill(Color.GREEN); }
                if (node == PuzzleState.Node.RED) {tiles.get(c).setFill(Color.RED); }
                c+=1;
            }
        }

         */
        //generate all positions
        //convert them to coordinates
        //index them and iterate through

        List<Position> poses = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            poses.add(new Position(1, i));
        }for (int i = 1; i <= 4; i++) {
            poses.add(new Position(2, i));
        }for (int i = 1; i <= 5; i++) {
            poses.add(new Position(3, i));
        }for (int i = 1; i <= 4; i++) {
            poses.add(new Position(4, i));
        }for (int i = 1; i <= 3; i++) {
            poses.add(new Position(5, i));
        }

        int c=0;

        for (Position pos : poses){
            int[] coor = Position.convertPositionToCoordinates(pos);
            PuzzleState.Node node = state.getCurrentState()[coor[0]][coor[1]];
            if (node == PuzzleState.Node.BLUE) {tiles.get(c).setFill(Color.BLUE); }
            if (node == PuzzleState.Node.GREEN) {tiles.get(c).setFill(Color.FORESTGREEN); }
            if (node == PuzzleState.Node.RED) {tiles.get(c).setFill(Color.RED); }
            c+=1;
        }

    }

    public void handleKeyEvent(KeyCode code){
        //forget selection if esc is pressed
        System.out.println(code.getChar());
        if (code.getCode() == KeyCode.ESCAPE.getCode()){
            dir=0;
            selectedTile=null;
        }
        //get and set the direction from a key event, provided we have a selected tile
        if (selectedTile==null){
            System.out.println("tile is null");
            return;
        }
        if (code.getCode() == KeyCode.LEFT.getCode() || code.getCode() == KeyCode.A.getCode()){
            dir=-1;
        }
        else if (code.getCode() == KeyCode.RIGHT.getCode() || code.getCode() == KeyCode.D.getCode()){
            dir=1;
        }
        System.out.println(dir);
        //call makemove
        if (dir!=0){
            makeMove(new Move(position, dir));
        }
        dir=0;
        //TODO
    }

    public void makeMove(Move move){
        //TODO
        //is legal?
        //calls makemove
        System.out.println(move.center.getX() + " "+ move.center.getY() + " " + move.direction);
        state.makeMove(move);
        //calls update
        updatePlayField();
        stepCounter.setText(String.valueOf((Integer.parseInt(stepCounter.getText()) + 1)));

        if (state.isSolved()){
            onComplete();
        }
    }

    public void translateTileSelection(int id){
        //prob works
        //could do this smarter
        //translate to pos
        List<Integer> ids = List.of(5,6,9,10,11,14,15);

        int index = ids.indexOf(id);

        List<Position> positions = List.of(
                new Position(2,2), new Position(2,3),
                new Position(3,2), new Position(3,3), new Position(3,4),
                new Position(4,2), new Position(4,3));
        position = positions.get(index);
        System.out.println(position.getX() + " " + position.getY());
        //set pos
    }

    //start screen
    public void onTileClicked(Tile clickedTile){

        //cant do i init, palce somewhere



        //verify if its a legal move
        int index = Integer.parseInt(clickedTile.getId());
        List<Integer> legalTilesToMoveFrom = new ArrayList<>(List.of(5, 6, 9, 10, 11, 14, 15));
        if (!legalTilesToMoveFrom.contains(index)){
            selectedTile=null;
            return;
        }

        selectedTile = clickedTile;
        //      System.out.println(clickedTile.getId());
        //translate this to a position
        System.out.println(clickedTile.getId());
        translateTileSelection(index);



        //TODO might do
        //open the lil menu

        //board.getChildren().add(new LocalMenu(clickedTile));
    }

    void postInitialize(){
        userthing();
        if (!listenerAdded){ //redundant checking
            board.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                handleKeyEvent(key.getCode());
            });
            System.out.println("evetn listener added");
            listenerAdded=true;
        }
    }
    @FXML
    private void initialize(){
        //get them hexagons!
        //store them in order in a list for access dunno fi works bc of reference shenanigans
        //binding thingy
        //buttons on them
        //know what node is selected
        /*
        VBox vBox = (VBox) board.getChildren();
        for (Node node : vBox.getChildren()){
            HBox hBox = (HBox) node;
            for (Node iNode : hBox.getChildren()){
                Pane pane = (Pane) iNode;
                Polygon hexagon = new Polygon(-0.5, 0.5,1 , -1 );
            }
        }*/

        //KISZERVEZNI

        int diff = 2;

        int rowCount = 5; // how many rows of tiles should be created
        int tilesPerRow = 5; // the amount of tiles that are contained in each row
        int xStartOffset = 360; // offsets the entire field to the right
        int yStartOffset = 100; // offsets the entire fiels downwards

        /*
        for (int x = 0; x < tilesPerRow; x++) {
            for (int y = 0; y < rowCount; y++) {
                double xCoord = x * TILE_WIDTH + (y % 2) * Tile.n + xStartOffset;
                double TILE_HEIGHT = Tile.TILE_HEIGHT;
                double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;

                Polygon tile = new Tile(xCoord, yCoord);
                tiles.add(tile);

                board.getChildren().add(tile);
            }
        }

         */

        int c =1;

        for (int i = 0; i < rowCount; i++) {
            double xOffset = xStartOffset;
            if (Math.abs(diff)==2 ){
                xOffset=xStartOffset+(81 * 1.5);
            }

            for (int j = 0; j < tilesPerRow-Math.abs(diff); j++) {
                double x = j * TILE_WIDTH + (i % 2) * Tile.n + xOffset;
                double y = i * TILE_HEIGHT * 0.75 + yStartOffset;


                Polygon tile = new Tile(x, y, c);
                tiles.add(tile);

                board.getChildren().add(tile);
                c+=1;
            }
            diff-=1;
        }
        Logger.info("initialized playfield");

        /*

        for (int i = 0; i < 19; i++) {
            tiles.get(i).setFill(Color.RED);
            if (i>5){
                tiles.get(i).setFill(Color.BLUE);
                if (i>12){
                    tiles.get(i).setFill(Color.GREEN);
                }
            }
        }

         */

        state = new PuzzleState();
        updatePlayField();


        /*




        board.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                handleKeyEvent(keyEvent.getCode());
            }
        });

         */


    }


}
