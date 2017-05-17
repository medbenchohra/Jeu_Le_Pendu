package MainWindow;

import Core.Noyau;
import MainPackage.Main;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static MainPackage.Main.mainLayout;
import static MainPackage.Main.primaryStage;


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
        Pseudonyme.setText(Noyau.user.getPseudonyme());
        highScore.setText("" + Noyau.user.getMeilleurScore());
    }

    @FXML
    void Deconnection(ActionEvent event) throws Exception {
        Main.gotoLogin();
    }

    @FXML
    void Help(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../Help/Help.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Comment Jouer");
        primaryStage.show();
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