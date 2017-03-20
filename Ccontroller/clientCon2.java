package Ccontroller;

import Client.Client;
import Scontroller.Constraints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * Created by rifat on 3/16/17.
 */




    public class clientCon2 {
        Client main;

    public void setMain(Client main) {
        this.main = main;
    }


    @FXML
    private Text fType;

    @FXML
    private Text fileNum;

    @FXML
    private Text maxSize;

    @FXML
    private Text stdID;

    @FXML
    private Text syncT;

    @FXML
    void chooseFile(ActionEvent event) {

        main.filechoose();
    }

    @FXML
    void syncAction(ActionEvent event) {
        main.simpleClient.syncAll();

    }
    public void show(Constraints constraints)
    {

        fType.setText(constraints.getFileType());
        fileNum.setText(String.valueOf(constraints.getFileNum()));
        maxSize.setText(String.valueOf(constraints.getMaxFileSize()));
        stdID.setText(constraints.getIDRange());
        syncT.setText(constraints.getSyncDelay());
    }

    }



