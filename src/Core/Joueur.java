package Core;

import java.util.ArrayList;

/**
 * Created by The King Mohamed on 20/04/2017.
 */

public class Joueur {

    private String pseudonyme;
    private int meilleurScore;
    private ArrayList<Integer> scores;


    public Joueur() {}

    public Joueur(String pseudonyme, int meilleurScore) {
        this.pseudonyme = pseudonyme;
        this.meilleurScore = meilleurScore;
    }

    public Joueur(String pseudonyme, int meilleurScore, ArrayList<Integer> scores) {
        this.pseudonyme = pseudonyme;
        this.meilleurScore = meilleurScore;
        this.scores = scores;
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

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> score) {
        this.scores = score;
    }
}
