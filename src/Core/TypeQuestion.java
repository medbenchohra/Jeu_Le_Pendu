package Core;

/**
 * Created by The King Mohamed on 20/04/2017.
 */
public enum TypeQuestion {

    DEFINITION(3) , SYNONYME(2) , ANTONYME(1) ;

    private int coef;

    TypeQuestion(int coef) {
        this.coef = coef;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }
    @Override
    public String toString(){
        String s = "";
        switch (this.getCoef()){
            case 3:
                s = "DEFINITION";
                break;
            case 2:
                s = "SYNONYME";
                break;
            case 1:
                s = "ANTONYME";
                break;
        }
        return s;
    }
}
