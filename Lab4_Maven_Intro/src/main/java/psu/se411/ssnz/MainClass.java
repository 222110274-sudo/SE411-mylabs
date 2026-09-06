package psu.se411.ssnz;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("SSNZ");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}