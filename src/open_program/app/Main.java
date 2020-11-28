package open_program.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/open_program.fxml"));
        primaryStage.setTitle("Abrir Programa");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/app_icon.png")));
        primaryStage.setScene(new Scene(root, 570, 350));
        primaryStage.requestFocus();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
