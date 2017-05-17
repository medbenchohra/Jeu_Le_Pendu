package Help;

/**
 * Created by Dev_Devil on 14/05/2017.
 */

import Core.Noyau;
import MainPackage.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label Pseudonyme;

    @FXML
    private Label highScore;

    @FXML
    private JFXButton homeBtn;


    @FXML
    void returnToHome(ActionEvent event) {
        try {
            Main.gotoMainWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Deconnection(ActionEvent event) throws Exception {
        Main.gotoLogin();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pseudonyme.setText(Noyau.user.getPseudonyme());
        highScore.setText("" + Noyau.user.getMeilleurScore());
    }
}

