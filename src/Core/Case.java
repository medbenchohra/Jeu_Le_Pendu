package Core;

/**
 * Created by Dev_Devil on 20/04/2017.
 */
public abstract class Case extends Constantes {

    private char lettre;

    public Case() {}

    public Case(char lettre) {
        this.lettre = lettre;
    }

    public boolean verifier(char l){
        return  (l == this.lettre);
    }

    public abstract boolean stop(char l);

    public abstract int calculerScore();

    public char getLettre(){
        return this.lettre;
    }

    public void setLettre(char l){
        this.lettre = l;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
