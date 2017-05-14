package Core;

import java.security.spec.ECFieldF2m;

/**
 * Created by The King Mohamed on 20/04/2017.
 */
public class AuthentificationException extends Exception {
    @Override
    public String getMessage() {
        return "Erreur d'authentification" ;
    }
}
