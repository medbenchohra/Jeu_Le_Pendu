package Login;
import Core.Joueur;
import Core.Noyau;
import MainPackage.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;


public class Controller {



        @FXML
        private JFXTextField username;

        @FXML
        private Label messageAlert;

        @FXML
        private JFXButton signup;

        @FXML
        private JFXButton login;

        @FXML
        void MakeSignUp(ActionEvent event) throws Exception {
            String pseud = username.getText();
            if (Noyau.pseudonymeValide(pseud)) {
                Joueur user = Noyau.inscription(pseud);
                if (user != null) {
                    messageAlert.setTextFill(Color.GREEN);
                    messageAlert.setText("Inscription réussite, essayez de se connecter");
                } else {
                    messageAlert.setTextFill(Color.RED);
                    messageAlert.setText("Vous êtes deja inscris, essayez de se connecter");
                }
            } else{
                messageAlert.setTextFill(Color.RED);
                messageAlert.setText("Ce pseudonyme n'est pas valide");
            }
        }

        @FXML
        void makeLogin(ActionEvent event) throws Exception {
            String pseud = username.getText();
            if (Noyau.pseudonymeValide(pseud)) {
                Joueur user = Noyau.connecter(pseud);
                if (user != null) {
                    Noyau.user = user;
                    Main.gotoMainWindow();
                } else {
                    messageAlert.setTextFill(Color.RED);
                    messageAlert.setText("Vous n'êtes pas inscris, veuillez vous s'inscrire");
                }
            } else{
                messageAlert.setTextFill(Color.RED);
                messageAlert.setText("Ce pseudonyme n'est pas valide");
            }
        }

    }

