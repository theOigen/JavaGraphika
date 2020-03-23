import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        Group root = new Group();
        Scene scene = new Scene(root, 307,187);

        scene.setFill(Color.web("FF0000"));
        Polygon poli = new Polygon(149,94,
                81,105,
                68,27,
                141,13,
                153,86,
                172,26,
                248,59,
                214,121,
                157,95,
                192,147,
                136,181,
                106,121);
        root.getChildren().add(poli);
        poli.setFill(Color.web("00FF00"));

        Polyline polyline = new Polyline(111,56,
                194,76,
                149,133,
                111,56);
        polyline.setStroke(Color.web("B50093"));
        polyline.setStrokeWidth(5.5);
        polyline.setStrokeLineCap(StrokeLineCap.ROUND);
        polyline.setStrokeLineJoin(StrokeLineJoin.ROUND);
        root.getChildren().add(polyline);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
