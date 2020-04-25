package domain;

import java.io.Serializable;
import java.util.Objects;

public class RezervareLoc implements Serializable {
    private Integer id;
    private Cursa cursa;
    private Client client;
    private Integer nrLoc;

    public RezervareLoc(Integer id, Cursa cursa, Client client, Integer nrLoc) {
        this.id = id;
        this.cursa = cursa;
        this.client = client;
        this.nrLoc = nrLoc;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cursa getCursa() {
        return cursa;
    }

    public void setCursa(Cursa cursa) {
        this.cursa = cursa;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RezervareLoc rezervareLoc = (RezervareLoc) o;
        return Objects.equals(id, rezervareLoc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id+cursa.getDestinatie()+cursa.getData_si_ora()+client.getNume()+client.getPrenume()+nrLoc;
    }

    public Integer getNrLoc() {
        return nrLoc;
    }

    public void setNrLoc(Integer nrLoc) {
        this.nrLoc = nrLoc;
    }
}
