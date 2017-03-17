package Server;/**
 * Created by rifat on 3/16/17.
 */

import Ccontroller.ClientController;
import Scontroller.Scontrol1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Server extends Application {
    Stage stage;
    Scontrol1 scontrol1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/serverPage1.fxml"));
        Parent root = loader.load();

        scontrol1=loader.getController();
        scontrol1.setServer(this);
        primaryStage.setScene(new Scene(root));
        stage=primaryStage;
        stage.setTitle("Server");
        primaryStage.show();

    }
    public void filechoose()
    {
        DirectoryChooser directoryChooser=new DirectoryChooser();
        directoryChooser.setTitle("Open Destination");
       // directoryChooser.setInitialDirectory(new File(""));
        scontrol1.setDir(directoryChooser.showDialog(stage).getAbsolutePath());
        //System.out.println(directoryChooser.showDialog(stage).getAbsolutePath());


    }

}
