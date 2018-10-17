package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String path = "../frame/background.fxml";

        URL url = Main.class.getResource(path);

                Parent root = FXMLLoader.load(url);
                primaryStage.setTitle("Dictionary EngLish-VietNamese");
                primaryStage.setScene(new Scene(root, 745, 642));
                primaryStage.show();
    }
}