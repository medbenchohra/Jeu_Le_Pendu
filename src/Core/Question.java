package Core;

import java.io.*;

/**
 * Created by The King Mohamed on 20/04/2017.
 */
public class Question {
    private TypeQuestion typeQuestion;
    private String question;
    private Case[] cases;
    private boolean calculerMalus;

    public Question () {}

    public Question(TypeQuestion typeQuestion, Case[] cases, String question) {

        this.typeQuestion = typeQuestion;
        this.cases = cases;
        this.question = question;
    }

    public boolean getCalculerMalus() {
        return calculerMalus;
    }

    public void setCalculerMalus(boolean calculerMalus) {
        this.calculerMalus = calculerMalus;
    }

    public TypeQuestion getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(TypeQuestion typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Case[] getCases() {
        return cases;
    }

    public void setCases(Case[] cases) {
        this.cases = cases;
    }
}
