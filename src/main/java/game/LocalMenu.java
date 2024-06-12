package game;

import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class LocalMenu extends AnchorPane {
    private StackPane window;
    private Circle left;
    private Circle right;

    public LocalMenu(GameController.Tile clickedTile){
        //this aint working
        double x = clickedTile.getPoints().get(0);

        double y = clickedTile.getPoints().get(1);
        System.out.println(x + " " + y );
        window = new StackPane();
        window.setBackground(Background.fill(Color.BLACK));
        left = new Circle(100,100,35, Color.BLACK);
        right = new Circle(x,y,35, Color.BLACK);
        left.setOnMouseClicked( e -> System.out.printf("left"));
        right.setOnMouseClicked( e -> System.out.printf("right"));
        window.getChildren().add(left);
        window.getChildren().add(right);
        System.out.println(window.getChildren().get(0));
    }
}
