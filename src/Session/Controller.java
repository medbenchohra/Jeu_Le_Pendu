package Session;

import Core.Case;
import Core.MultiChancesCase;
import Core.PropositionCase;
import Core.Session;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import MainPackage.Main;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Label Mot;

    @FXML
    private Label TypeQuestion;

    @FXML
    private Label Question;

    @FXML
    private HBox CasesMot;

    @FXML
    private Label Pseud;

    @FXML
    private Label HighScore;

    @FXML
    private Label Score;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i = 0;
        //while (i < 10) {
            Pseud.setText("Pseudonyme : " + Main.user.getPseudonyme());
            HighScore.setText("HighScore : " + Main.user.getMeilleurScore());
            Score.setText("Score : " + 0);
            Main.sess = new Session();
            Mot.setText("Mot N°" + (i + 1));
            TypeQuestion.setText(Main.sess.getQuestions()[i].getTypeQuestion().toString());
            Question.setText(Main.sess.getQuestions()[i].getQuestion());
            Case[] cases = Main.sess.getQuestions()[i].getCases();
            int j;
            for (j = 0; j < cases.length; j++)
                if (cases[j] instanceof PropositionCase) {
                    JFXComboBox props = new JFXComboBox();
                    for (int k = 0; k < 4; k++) {
                        props.getItems().add(((PropositionCase) cases[j]).getLettresPoss()[k]);
                        props.setStyle("-fx-background-color: #FFD54F;");
                        props.setId(j + "");
                    }
                    props.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous confirmez votre reponse?");
                        alert.showAndWait();
                        if (alert.getResult().getText().equals("OK")) {
                            invisible(Integer.parseInt(props.getId()));
                            //if la réponse est juste :
                            //
                            props.setDisable(true); // on ne peut plus le changer
                            visible();
                            //alert = new Alert(Alert.AlertType.INFORMATION,"Votre réponse est juste!");
                            if (allDisabled()) // tout le mot est trouvé
                            {
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                            } else {
                            }
                        } else {

                        }
                    });
                    CasesMot.getChildren().add(props);

                } else if (cases[j] instanceof MultiChancesCase) {
                    JFXTextField mtext = new JFXTextField();
                    mtext.setStyle("-fx-background-color: #FF8A65;");
                    mtext.setId(j + "");
                    mtext.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous confirmez votre reponse?");
                        alert.showAndWait();
                        if (alert.getResult().getText().equals("OK")) {
                            mtext.setDisable(true);
                            if (allDisabled()) {
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                            }
                        } else {
                            mtext.setText("");
                        }
                    });
                    CasesMot.getChildren().add(mtext);
                } else {
                    JFXTextField text = new JFXTextField();
                    text.setStyle("-fx-background-color: #AED581;");
                    text.setId(j + "");
                    text.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous confirmez votre reponse?");
                        alert.showAndWait();
                        if (alert.getResult().getText().equals("OK")) {
                            text.setDisable(true);
                            if (allDisabled()) {
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                            }
                        } else {
                            text.setText("");
                        }
                    });
                    CasesMot.getChildren().add(text);
                }
        //}
        }

    private boolean allDisabled(){ // retourne vrai si le mot est completement trouver, faux sinon
        for (int i=0;i<CasesMot.getChildren().size();i++)
        {
            if (!CasesMot.getChildren().get(i).isDisable())
            {
                return false;
            }
        }
        return true;
    }

    public void invisible (int pos) { // mettre toutes les cases invisibles sauf la case de position pos
        for (int i=0;i<CasesMot.getChildren().size();i++)
        {
            if (i!=pos)
            {
                CasesMot.getChildren().get(i).setVisible(false);
            }
        }
    }

    public void visible () { // mettre toutes les cases visibles
        for (int i=0;i<CasesMot.getChildren().size();i++)
        {
            CasesMot.getChildren().get(i).setVisible(true);
        }
    }
}