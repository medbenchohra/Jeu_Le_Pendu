package Core;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.security.SecureRandom;

/**
 * Created by Dev_Devil on 20/04/2017.
 */

public class Session extends Constantes{

    Question[] questions;
    boolean calculerMalus;
    int nbTromp = 0;

    public int getNbTromp() {
        return nbTromp;
    }

    public void setNbTromp(int nbTromp) {
        this.nbTromp = nbTromp;
    }

    public Session() {
        this.questions = recupererQuestions();
    }

    public Question[] getQuestions() {
        return questions;
    }

    private Question[] recupererQuestions() {

        BufferedReader in = null;
        Question[] questions = new Question[10];
        SecureRandom rand = new SecureRandom();

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("src/Core/Fichiers/mots.txt"), "UTF-8"));
            LineNumberReader lignes = new LineNumberReader(in);

//            long nbrMotsFichier = in.lines().count();
            int borne = rand.nextInt(nbrMotsFichier - 10);
            for (int i = 0; i < borne; i++) {
                new String(in.readLine());
            }
            String ligne = null;
            for (int i = 0; i < 10; i++) {
                ligne = in.readLine();
                if (ligne != null) {
                    String[] elementsLigne = ligne.split(";");
                    questions[i] = new Question();
                    questions[i].setTypeQuestion(TypeQuestion.valueOf(elementsLigne[0]));
                    questions[i].setQuestion(elementsLigne[1]);
                    questions[i].setCases(genererCases(elementsLigne[2]));
                    questions[i].setCalculerMalus(calculerMalus);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return questions;
    }

    private Case[] genererCases(@NotNull String mot) {
        char[] lettres = mot.toCharArray();
        Case[] cases = new Case[lettres.length];

        int nbZeroChance = 0;
        int nbPropositions = 0;
        int nbMultiChance = 0;
        int choix = 0;

        if (lettres.length > 6) {
            for (int i = 0; i < lettres.length; i++) {
                choix = 1 + new SecureRandom().nextInt(3);
                switch (choix){
                    case 1:
                        if (nbZeroChance < 3){
                            cases[i] = new ZeroChanceCase(lettres[i]);
                            nbZeroChance++;
                            break;
                        }
                        else {
                            choix = 2;
                        }
                    case 2:
                        if (nbPropositions < 3) {
                            cases[i] = new PropositionCase(lettres[i]);
                            nbPropositions++;
                            break;
                        }
                        else {
                            choix = 3;
                        }
                    case 3:
                        cases[i] = new MultiChancesCase(lettres[i]);
                        nbMultiChance++;
                        break;
                }
            }

            if (nbPropositions + nbMultiChance > 5)
                calculerMalus = true;
            else
                calculerMalus = false;
        }
        return cases;
    }

    public int calculerScore(Case[] cases,int numeroDeQuestion) {
        Question question = questions[numeroDeQuestion];
        int coefQuestion = question.getTypeQuestion().getCoef();
        int score = 0;
        int coefCase = 0;

        for (int i = 0; i < cases.length; i++) {
            score = score + cases[i].calculerScore();
        }

        score = score*coefQuestion;
        System.out.println(calculerMalus);
        if (question.getCalculerMalus()) for (int i = 0; i < cases.length; i++) {
            if (!(cases[i]).getClass().getSimpleName().equals(Constantes.TAG_ZERO)) {
                score = score + ((Malus)cases[i]).calculerMalus();
            }
        }

        return score;
    }

}
