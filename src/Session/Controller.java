package Session;

import Core.*;
import MainPackage.Main;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
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
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Label Pseudonyme;

    @FXML
    private Label highScore;

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

    @FXML
    void returnToHome(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous sauvegarder votre score avant de sortir ?");
            alert.showAndWait();
//            alert.setHeaderText("header");
//            alert.setContentText("");
            if (alert.getResult().getText().equals("OK"))
                Noyau.saveScore(Integer.valueOf(Score.getText()));
            Main.gotoMainWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeImage(0);
        Pseudonyme.setText(Noyau.user.getPseudonyme());
        highScore.setText("" + Noyau.user.getMeilleurScore());
        Score.setText("0");
        CasesMot.setAlignment(Pos.CENTER);
        CasesMot.setSpacing(5);
        Noyau.session = new Session();
        PlaySession(0);
    }

    void changeImage(int i) {
        if ( i >= 0 && i <=6 ) {
//            Image.setImage(new Image(new File("C:\\IdeaProjects\\Le_Pendu_TP1_POO_ESI\\src\\Core\\Images\\" + i + ".png").toURI().toString()));
//
            Image.setImage(new Image(new File("src/Core/Images/" + i + ".png").toURI().toString()));
//            Image.setImage(new Image("C:\\IdeaProjects\\Le_Pendu_TP1_POO_ESI\\src\\Core\\Images" + i + ".png"));
        }
    }

    public void PlaySession(int i)  {
        if (i < 10 && Noyau.session.getNbTromp()<6) {
            NbrMot.setText("" + (i + 1));
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
                            Noyau.session.setNbTromp(Noyau.session.getNbTromp() + 1);
                            changeImage(Noyau.session.getNbTromp());
                            Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                            CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                            PlaySession(i + 1);
                        }
                        if (allDisabled()) {
                            Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                            CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                            PlaySession(i + 1);
                        } else {

                        }
                    });
                    CasesMot.getChildren().add(props);
                } else {
                    if (cases[j] instanceof MultiChancesCase) {
                        boolean[] etat = new boolean[cases.length - 1];
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
                            setDisable(etat,((MultiChancesCase) cases[Integer.parseInt(mtext.getId())]).getNbTromp(),Integer.parseInt(mtext.getId()));
                            char l = Character.toLowerCase(mtext.getText().charAt(0));
                            System.out.println(mtext.getText());
                            if (((MultiChancesCase) cases[Integer.parseInt(mtext.getId())]).stop(l)) {
                                Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                                Noyau.session.setNbTromp(Noyau.session.getNbTromp() + 1);
                                changeImage(Noyau.session.getNbTromp());
                                CasesMot.getChildren().remove(0, CasesMot.getChildren().size());
                                PlaySession(i + 1);
                            } else if (((MultiChancesCase) cases[Integer.parseInt(mtext.getId())]).getReponse()) {
                                recoverEtat(etat,Integer.parseInt(mtext.getId()));
                                mtext.setDisable(true);
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
                                System.out.println(text.getId());
                                text.setDisable(true);
                                System.out.println("LOG: stop == " + cases[Integer.parseInt(text.getId())].stop(l));
                                if (((ZeroChanceCase) cases[Integer.parseInt(text.getId())]).stop(l)) {
                                    System.out.println("LOG: into if,");
                                    Score.setText((Integer.parseInt(Score.getText()) + Noyau.session.calculerScore(cases, i)) + "");
                                    Noyau.session.setNbTromp(Noyau.session.getNbTromp() + 1);
                                    changeImage(Noyau.session.getNbTromp());
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
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Non! Vous avez perdu !!, voulez-vous sauvegarder votre score ?");
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

    public boolean[] setDisable (boolean[] etat,int trmp,int pos) { // mettre toutes les cases invisibles sauf la case de position pos
            if (trmp == 0) {
                //etat = new boolean[CasesMot.getChildren().size() - 1];
                for (int i = 0; i < CasesMot.getChildren().size(); i++) {
                    if (i != pos) {
                        //CasesMot.getChildren().get(i).setVisible(false);
                        if (i < pos) {
                            etat[i] = CasesMot.getChildren().get(i).isDisable();
                        } else {
                            etat[i - 1] = CasesMot.getChildren().get(i).isDisable();
                        }
                        CasesMot.getChildren().get(i).setDisable(true);
                    }
                }
            }
            return etat;
        }

        public void recoverEtat (boolean[] etat,int pos) { // mettre toutes les cases visibles
            for (int i=0;i<CasesMot.getChildren().size();i++)
            {
                if (i!= pos && i<pos) {
                    CasesMot.getChildren().get(i).setDisable(etat[i]);
                }
                else if (i!= pos && i>pos){
                    CasesMot.getChildren().get(i).setDisable(etat[i-1]);
                }
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
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException{
            fb.insertString(offset,text.toUpperCase(),attr);
        }
        public void replace (FilterBypass fb,int offset,int length,String text,AttributeSet attrs) throws BadLocationException{
            fb.replace(offset,length,text.toLowerCase(),attrs);
        }
    }

}