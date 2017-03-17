package Client;

import Ccontroller.ClientController;
import Ccontroller.clientCon2;
import Scontroller.Constraints;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Client extends Application {

    Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/clientPage1.fxml"));
        Parent root = loader.load();
        ClientController controller=loader.getController();
        controller.setMain(this);
        primaryStage.setScene(new Scene(root));
        stage=primaryStage;
        primaryStage.show();

    }
//    public void start2() throws Exception
//    {
//        Parent root=FXMLLoader.load(getClass().getResource("/clientPage2.fxml"));
//        stage.setScene(new Scene(root));
//        stage.show();
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
//
//    }
    public void showConstraints(Constraints constraints) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/constraints.fxml"));
        Parent root = loader.load();
        clientCon2 controller=loader.getController();
        controller.setMain(this);
        controller.show(constraints);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
