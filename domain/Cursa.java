package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cursa implements Serializable {
    private Integer id;
    private String destinatie;
    private String data_si_ora;
    private Integer nrLocuriDisponibile;
    private List<Loc>locuri;

    public Cursa(Integer id, String destinatie, String data_si_ora, Integer nrLocuriDisponibile) {
        this.id = id;
        this.destinatie = destinatie;
        this.data_si_ora = data_si_ora;
        this.nrLocuriDisponibile = nrLocuriDisponibile;
        this.locuri=startLocuri(nrLocuriDisponibile);
    }

    public List<Loc>startLocuri(Integer nrLocuriDisponibile){
        List<Loc>lista=new ArrayList<>();
        for(int i=1;i<=nrLocuriDisponibile;i++){
            Loc loc=new Loc("-",i);
            lista.add(loc);
        }
        return lista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public String getData_si_ora() {
        return data_si_ora;
    }

    public void setData_si_ora(String data_si_ora) {
        this.data_si_ora = data_si_ora;
    }

    public Integer getNrLocuriDisponibile() {
        return nrLocuriDisponibile;
    }

    public void setNrLocuriDisponibile(Integer nrLocuriDisponibile) {
        this.nrLocuriDisponibile = nrLocuriDisponibile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursa cursa = (Cursa) o;
        return Objects.equals(id, cursa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id+" "+destinatie+" "+data_si_ora+" "+nrLocuriDisponibile;
    }

    public List<Loc> getLocuri() {
        return locuri;
    }

    public void setLocuri(List<Loc> locuri) {
        this.locuri = locuri;
    }
}
