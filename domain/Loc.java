package domain;

import java.io.Serializable;

public class Loc implements Serializable
{
    private String nume;
    private int loc;

    public Loc(String nume, int loc) {
        this.nume = nume;
        this.loc = loc;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Loc:"+loc+" "+"Rezervat de:"+nume;
    }
}
