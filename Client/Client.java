package Client;

import Ccontroller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Client extends Application {

    Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("clientPage1.fxml"));
        Parent root = loader.load();
        ClientController controller=loader.getController();
        controller.setMain(this);
        primaryStage.setScene(new Scene(root));
        stage=primaryStage;
        primaryStage.show();

    }
    public void start2() throws Exception
    {
        Parent root=FXMLLoader.load(getClass().getResource("clientPage2.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
//                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
//                new FileChooser.ExtensionFilter("All Files", "*.*"));
//        File selectedFile = fileChooser.showOpenDialog(stage);
//        if (selectedFile != null) {
//
//        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
