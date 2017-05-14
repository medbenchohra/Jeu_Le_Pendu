package ListScores;

/**
 * Created by Dev_Devil on 14/05/2017.
 */
import Core.Noyau;
import MainPackage.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    @FXML
    private Label Pseudonyme;

    @FXML
    private Label highScore;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXListView<Integer> Scores;


    @FXML
    void returnToHome(ActionEvent event) {
        try {
            Main.gotoMainWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pseudonyme.setText("Pseudonyme : " + Noyau.user.getPseudonyme());
        highScore.setText("Meuilleur Score : " + Noyau.user.getMeilleurScore());

        Scores = new JFXListView<>();
        ArrayList<Integer> list = Noyau.user.getScores();
        System.out.println(list);
//        for (int i=0;i<list.size();i++) {
//            Scores.getItems().add(list.get(i));
//        }
        Scores.setItems(FXCollections.observableArrayList(list));
    }
}

