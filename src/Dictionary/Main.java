package Dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(this.getClass().getResource("JavaFX.fxml"));
            Scene scene = new Scene(root,  956, 640);
            root.setId("pane");
            scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Từ điển Anh - Việt");
            Image icon = new Image(getClass().getResourceAsStream("icon.jpg"));
            primaryStage.getIcons().add(icon);
            primaryStage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
