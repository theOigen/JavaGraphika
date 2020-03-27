package sample;


import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Duck extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);

        // body
        var body1 = new MoveTo(428, 295);
        var body2 = new QuadCurveTo(481,244, 408, 193);
        var body3 = new LineTo(331, 197);
        var body4 = new LineTo(290, 162);
        var body5 = new QuadCurveTo(276, 145, 260 , 183);
        var body6 = new QuadCurveTo(234, 245, 259, 284);
        var body7 = new QuadCurveTo(345, 312, 428, 295);

        var body = new Path();
        body.setStrokeWidth(1);
        body.setStroke(Color.BLACK);
        body.setFill(Color.YELLOW);
        body.getElements().addAll(body1, body2, body3, body4, body5, body6, body7);
        root.getChildren().add(body);

        // head
        var head = new Ellipse(385, 155, 60, 50);
        head.setFill(Color.YELLOW);
        head.setStroke(Color.BLACK);
        head.setStrokeWidth(1);

        root.getChildren().add(head);

        //eye

        var eyeBack = new Ellipse(405, 138, 6, 12);
        eyeBack.setFill(Color.BLACK);
        eyeBack.setStroke(Color.BLACK);
        eyeBack.setStrokeWidth(1);

        root.getChildren().add(eyeBack);

        var eyeFront = new Ellipse(406, 135, 3, 7);
        eyeFront.setFill(Color.WHITE);
        eyeFront.setStroke(Color.BLACK);
        eyeFront.setStrokeWidth(1);

        root.getChildren().add(eyeFront);

        //nose

        var nose1 = new MoveTo(434, 132);
        var nose2 = new QuadCurveTo(448, 136, 463, 133);
        var nose3 = new QuadCurveTo(473, 132, 452, 156);
        var nose4 = new LineTo(448, 173);
        var nose5 = new QuadCurveTo(427, 184, 399, 172);
        var nose6 = new QuadCurveTo(421, 150, 434, 132);

        var nose = new Path();
        nose.setStrokeWidth(1);
        nose.setStroke(Color.BLACK);
        nose.setFill(Color.ORANGE);
        nose.getElements().addAll(nose1, nose2, nose3, nose4, nose5, nose6);
        root.getChildren().add(nose);

        var fNose1 = new MoveTo(423, 164);
        var fNose2 = new LineTo(452, 150);
        var fNose3 = new LineTo(449, 167);

        var fNose = new Path();
        fNose.setStrokeWidth(0);
        fNose.setStroke(Color.BLACK);
        fNose.setFill(Color.ORANGERED);
        fNose.getElements().addAll(fNose1, fNose2, fNose3);
        root.getChildren().add(fNose);

        // Animation
        int cycleCount = 2;
        int time = 2000;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(cycleCount);
        rotateTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(150);
        translateTransition.setToX(50);
        translateTransition.setCycleCount(cycleCount + 1);
        translateTransition.setAutoReverse(true);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(time), root);
        translateTransition2.setFromX(50);
        translateTransition2.setToX(150);
        translateTransition2.setCycleCount(cycleCount + 1);
        translateTransition2.setAutoReverse(true);

        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(time), root);
        scaleTransition2.setToX(0.1);
        scaleTransition2.setToY(0.1);
        scaleTransition2.setCycleCount(cycleCount);
        scaleTransition2.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                rotateTransition,
                scaleTransition,
                scaleTransition2,
                translateTransition
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
//        // End of animation

        primaryStage.setResizable(false);
        primaryStage.setTitle("Lab 3");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
