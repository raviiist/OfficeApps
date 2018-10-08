package sample;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.nio.file.Paths;

public class Main extends Application {
    private double x,y;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Schedule");
        primaryStage.setScene(new Scene(root));

        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() -x);
            primaryStage.setY(event.getScreenY() -y);
        });

        primaryStage.show();
        //Controller cr = new Controller();
        //cr.loadData(Paths.get("F:\\Study\\IntelliJ projects\\Office Files\\satsch.c2c"));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
