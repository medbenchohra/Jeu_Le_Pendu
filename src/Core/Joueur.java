package Core;

/**
 * Created by The King Mohamed on 20/04/2017.
 */

public class Joueur {

    private String pseudonyme;
    private int meilleurScore;

    public Joueur(String pseudonyme, int meilleurScore) {
        this.pseudonyme = pseudonyme;
        this.meilleurScore = meilleurScore;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public void setMeilleurScore(int meilleurScore) {
        this.meilleurScore = meilleurScore;
    }
}
