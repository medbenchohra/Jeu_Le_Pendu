package MainWindow;

import Core.Noyau;
import MainPackage.Main;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable   {

    @FXML
    private Label Pseudonyme;

    @FXML
    private Label highScore;

    @FXML
    private JFXButton sessionStartBtn;

    @FXML
    private JFXButton scoresListBtn;

    @FXML
    private JFXButton disconnnecte;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pseudonyme.setText("Username : " + Noyau.user.getPseudonyme());
        highScore.setText("HighScore : " + Noyau.user.getMeilleurScore());
    }

    @FXML
    void Deconnection(ActionEvent event) throws Exception {
        Main.gotoLogin();
    }

    @FXML
    void BeginSession(ActionEvent event) throws Exception {
        Main.gotoSessionMenu();
    }

    @FXML
    void ShowScores(ActionEvent event) throws Exception {
        Main.gotoListScore();
    }

}