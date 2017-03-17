package Scontroller;

/**
 * Created by rifat on 3/17/17.
 */

import Server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class Scontrol1 {

    Server server;
    String dir;

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @FXML
    private TextField fileType;

    @FXML
    private Slider fileNum;

    @FXML
    private Slider maxFileSize;

    @FXML
    private TextField IDRange;

    @FXML
    void sendConstraints(ActionEvent event) {
        Constraints constraints=new Constraints();
        constraints.setFileType(fileType.getText());
        constraints.setFileNum((int) fileNum.getValue());
        constraints.setMaxFileSize((int) maxFileSize.getValue());
        constraints.setIDRange(IDRange.getText());
        constraints.setDestinationDir(dir);

    }

    @FXML
    void setDestination(ActionEvent event) {
        server.filechoose();


    }

}
