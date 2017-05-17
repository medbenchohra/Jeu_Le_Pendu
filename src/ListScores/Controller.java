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
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Label Pseudonyme;

    @FXML
    private Label highScore;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXListView<Label> Scores;


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

        ArrayList<Integer> list = Noyau.user.getScores();
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1.intValue() > o2.intValue())
                    return -1;
                else
                    return 1;
            }
        });
        for (int i=0;i<list.size();i++) {
            Scores.getItems().add(new Label("" + list.get(i)));
        }
    }
}

