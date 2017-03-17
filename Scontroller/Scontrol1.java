package Scontroller;

/**
 * Created by rifat on 3/17/17.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class Scontrol1 {

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

    }

    @FXML
    void setDestination(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");


    }

}
