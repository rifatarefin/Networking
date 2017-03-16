import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientController {

    Client main;

    public void setMain(Client main) {
        this.main = main;
    }

    @FXML
    private TextField sid=null;

    @FXML
    private TextField port=null;

    @FXML
    private TextField ip=null;

    @FXML
    private Button con;

    @FXML
    void connect(ActionEvent event) {
        System.out.println(port.getCharacters());
        if (sid!=null && port !=null && ip !=null)
        {
            new SimpleClient(ip.getText(),port.getText());
            try {
                main.start2();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
