package Session;

import Core.*;
import MainPackage.Main;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Label Pseudonyme;

    @FXML
    private Label HighScore;

    @FXML
    private Label Score;

    @FXML
    private ImageView Image;

    @FXML
    private Label NbrMot;

    @FXML
    private Label TypeQuestion;

    @FXML
    private Label Question;

    @FXML
    private HBox CasesMot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            Pseudonyme.setText("" + Noyau.user.getPseudonyme());
            HighScore.setText("" + Noyau.user.getMeilleurScore());
            Score.setText("" + 0);
            CasesMot.setAlignment(Pos.CENTER);
            CasesMot.setSpacing(5);
            Noyau.session = new Session();
            PlaySession(0);
        }
    public void PlaySession(int i)  {
        if (i < 10 && Noyau.session.getNbTromp()<6) {
            NbrMot.setText("Mot N°" + (i + 1));
            TypeQuestion.setText(Noyau.session.getQuestions()[i].getTypeQuestion().toString());
            Question.setText(Noyau.session.getQuestions()[i].getQuestion());
            Case[] cases = Noyau.session.getQuestions()[i].getCases();
            int j;
            for (j = 0; j < cases.length; j++)
                if (cases[j] instanceof PropositionCase) {
                    JFXComboBox props = new JFXComboBox();
                    props.setStyle("-fx-background-color: #FFD54F;");
                    props.setMaxSize(60, 40);
                    for (int k = 0; k < 4; k++) {
                        props.getItems().add(Character.toUpperCase(((PropositionCase) cases[j]).getLettresPoss()[k]));
                    }
                    props.setId(j + "");
                    props.setOnAction(event -> {
                            invisible(Integer.parseInt(props.getId()));
                            props.setDisable(true);
                            visible();
                            char l = Character.toLowerCase((char) props.getValue());
                            if (((PropositionCase) cases[Integer.parseInt(props.getId())]).stop(l)) {
                                Noyau.session.setNbTromp(Noyau.session.getNbTromp()+1);
                                //todo:image
                                Score.setText((Integer.parseInt(Score.getText())+Noyau.session.calculerScore(cases,i))+"");
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                                PlaySession(i + 1);
                            }
                            if (allDisabled()){
                                Score.setText((Integer.parseInt(Score.getText())+Noyau.session.calculerScore(cases,i))+"");
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                                PlaySession(i + 1);
                            }
                            else{

                            }
                    });
                    CasesMot.getChildren().add(props);
                } else {
                    if (cases[j] instanceof MultiChancesCase) {
                        JFXTextField mtext = new JFXTextField();
                        mtext.setStyle("-fx-background-color: #AED581;");
                        mtext.setMaxSize(60, 40);
                        mtext.setId(j + "");
                        //mtext.addEventFilter(KeyEvent.KEY_TYPED,letter_Validation(1));
                        mtext.textProperty().addListener((ov,oldValue,newValue)-> {
                            mtext.setText(newValue.toUpperCase());
                        });
                        mtext.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
                            String newText = change.getControlNewText();
                            if (newText.length()>1 || (newText.length() == 1) && !newText.matches("[A-Za-z]")){
                                return  null;
                            }else {
                                return change;
                            }
                        }));
                        mtext.setOnAction(event -> {
                            invisible(Integer.parseInt(mtext.getId()));
                            char l = Character.toLowerCase(mtext.getText().charAt(0));
                            System.out.println(mtext.getText());
                            if (((MultiChancesCase) cases[Integer.parseInt(mtext.getId())]).stop(l)) {
                                Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                                Noyau.session.setNbTromp(Noyau.session.getNbTromp() + 1);
                                //todo:image
                                visible();
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                                PlaySession(i + 1);
                            } else if (((MultiChancesCase) cases[Integer.parseInt(mtext.getId())]).getReponse()) {
                                mtext.setDisable(true);
                                visible();
                            }
                            if (allDisabled()) {
                                Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                                PlaySession(i + 1);
                            }
                        });
                        CasesMot.getChildren().add(mtext);
                    } else {
                        JFXTextField text = new JFXTextField();
                        text.setStyle("-fx-background-color: #FF8A65;");
                        text.setMaxSize(60, 40);
                        text.setId(j + "");
                        //text.addEventFilter(KeyEvent.KEY_TYPED,letter_Validation(1));
                        text.textProperty().addListener((ov,oldValue,newValue)->{
                            text.setText(newValue.toUpperCase());
                        });
                        text.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
                            String newText = change.getControlNewText();
                            if (newText.length()>1 || (newText.length() == 1) && !newText.matches("[A-Za-z]")){
                                return  null;
                            }else {
                                return change;
                            }
                        }));
                        text.setOnAction(event -> {
                                char l = Character.toLowerCase(text.getText().charAt(0));
                                System.out.println(text.getText());
                                text.setDisable(true);
                                if (((ZeroChanceCase) cases[Integer.parseInt(text.getId())]).stop(l)) {
                                    Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                                    Noyau.session.setNbTromp(Noyau.session.getNbTromp() + 1);
                                    //todo:image
                                    CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                                    PlaySession(i + 1);
                                } else if (allDisabled()) {
                                    Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                                    CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                                    PlaySession(i + 1);
                                }
                        });
                        CasesMot.getChildren().add(text);
                    }
                }
            }
            else{
             if ( Noyau.session.getNbTromp()>=6) {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION, "Non! Vous avez perdu !!, voulez-vous sauvegarder votre score ?");
                 alert.showAndWait();
                 if (alert.getResult().getText().equals("OK")){
                     Noyau.saveScore(Integer.valueOf(Score.getText()));
                 }
             }
             else{
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bravo, Vous avez gagner !!, voulez-vous sauvegarder votre score ?");
                 alert.showAndWait();
                 if (alert.getResult().getText().equals("OK")){
                     Noyau.saveScore(Integer.valueOf(Score.getText()));
                 }
             }
            try {
                Main.gotoMainWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
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

    public javafx.event.EventHandler<KeyEvent> letter_Validation(final Integer max_length){
        return new javafx.event.EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                JFXTextField txt = (JFXTextField) event.getSource();
                if (txt.getText().length()>= max_length){
                    event.consume();
                }
                if (event.getCharacter().matches("[A-Za-z]")){
                }else{
                    event.consume();
                }
            }
        };
    }
    class UpperCaseDocumentFilter extends DocumentFilter{
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException{
            fb.insertString(offset,text.toUpperCase(),attr);
        }
        public void replace (DocumentFilter.FilterBypass fb,int offset,int length,String text,AttributeSet attrs) throws BadLocationException{
            fb.replace(offset,length,text.toLowerCase(),attrs);
        }
    }
}