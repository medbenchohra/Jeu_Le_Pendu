package Core;

import java.security.SecureRandom;

/**
 * Created by The King Mohamed on 20/04/2017.
 */

public class ZeroChanceCase extends Case {

    public static final int coefZeroChances = 3;
    private boolean reponse;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ZeroChanceCase() {}

    public ZeroChanceCase(char l) {
        super(l);
    }

    public boolean getReponse() {
        return reponse;
    }

    public void setReponse(boolean reponse) {
        this.reponse = reponse;
    }

    @Override
    public boolean stop(char l) {
        active = true;
        reponse = super.verifier(l);
        return !reponse;
    }

    @Override
    public int calculerScore() {
        if (reponse) return (coefZeroChances);
        else return 0;
    }

}
