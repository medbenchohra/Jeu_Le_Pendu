package Core;

import Core.Case;
import Core.Malus;

/**
 * Created by Dev_Devil on 20/04/2017.
 */
public class MultiChancesCase extends Case implements Malus {

    public static final int nbTrompPossible = 2;
    public static final int coefMultiChances = 2;
    private int nbTromp = 0;
    private boolean reponse;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public MultiChancesCase() {}

    public MultiChancesCase(char lettre) {
        super(lettre);
    }

    public boolean getReponse() {
        return reponse;
    }

    public void setReponse(boolean reponse) {
        this.reponse = reponse;
    }

    @Override
    public boolean verifier(char l){
        if (!super.verifier(l)) nbTromp++;

        return super.verifier(l);
    }
    public boolean stop(char l){
        active = true;
        reponse = verifier(l);
        return (nbTromp > nbTrompPossible && !reponse);
    }

    @Override
        public int calculerScore() {
            if (reponse) return (coefMultiChances);
            else return 0;
        }

    public int getNbTromp() {
        return nbTromp;
    }

    public void setNbTromp(int nbTromp) {
        this.nbTromp = nbTromp;
    }

    public int calculerMalus()
    {
        if (active) {
            return (nbTromp * coefMultiChances * multiChancesMalus);
        }

        else
        {
            return 0;
        }
    }

}
