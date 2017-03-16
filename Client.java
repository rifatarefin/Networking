
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
