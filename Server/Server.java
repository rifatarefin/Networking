package Server;/**
 * Created by rifat on 3/16/17.
 */

import Ccontroller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Server extends Application {
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/serverPage1.fxml"));
        Parent root = loader.load();
        //ClientController controller=loader.getController();
        //controller.setMain(this);
        primaryStage.setScene(new Scene(root));
        stage=primaryStage;
        primaryStage.show();

    }
}
