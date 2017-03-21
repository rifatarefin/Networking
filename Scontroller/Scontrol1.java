package Scontroller;

/**
 * Created by rifat on 3/17/17.
 */

import Server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Server.TestServer;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class Scontrol1 {

    Server server;
    String dir= new File("").getAbsolutePath();

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @FXML
    private TextField fileType=null;

    @FXML
    private Slider fileNum=null;

    @FXML
    private Slider maxFileSize=null;

    @FXML
    private TextField IDRange=null;

    @FXML
    private TextField syncDelay=null;

    @FXML
    void sendConstraints(ActionEvent event) {
        //System.out.println(dir);
        Constraints constraints=new Constraints();
        constraints.setFileType(fileType.getText());
        constraints.setFileNum((int) fileNum.getValue());
        constraints.setMaxFileSize((long) maxFileSize.getValue());
        constraints.setIDRange(IDRange.getText());
        constraints.setDestinationDir(dir);
        constraints.setSyncDelay(syncDelay.getText());
        new TestServer(constraints);

    }

    @FXML
    void setDestination(ActionEvent event) {
        server.filechoose();


    }

}
