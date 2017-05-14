package Core;

import com.sun.org.apache.regexp.internal.REUtil;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by The King Mohamed on 20/04/2017.
 */
public class PropositionCase extends ZeroChanceCase implements Malus {

    public static final int coefPropositions = 1;
    private char[] lettresPoss = new char[4] ;

    public PropositionCase() {}

    public PropositionCase(char l)
    {
        super(l);
        String Alphabets = new String(ALPHABETS);
        int indiceLettreJuste = new SecureRandom().nextInt(4),indice;
        lettresPoss[indiceLettreJuste] = l;
        for (int i=0;i<4;i++) {
            if (i != indiceLettreJuste) {
                do {
                    indice = new SecureRandom().nextInt(26);
                }while (Alphabets.toCharArray()[indice] == l);
                lettresPoss[i] = Alphabets.toCharArray()[indice];
                Alphabets.replaceFirst("" + lettresPoss[i],"");
            }
        }
    }
    public boolean isActive(){
        return super.isActive();
    }
    public void setActive(boolean active){
        super.setActive(active);
    }
    public void setReponse(boolean reponse){
        super.setReponse(reponse);
    }
    public char[] getLettresPoss() {
        return lettresPoss;
    }

    public void setLettresPoss(char[] lettresPoss) {
        this.lettresPoss = lettresPoss;
    }

    @Override
    public int calculerScore() {
        if (super.getReponse())
            return coefPropositions;
        else
            return 0;
    }

    @Override
    public int calculerMalus() {
        if (super.isActive() && !getReponse())
            return propositionsMalus;
        else
            return 0;
    }
}
