package objectProtocol;

public class addRezervareRequest implements Request {
    private int id;
    private int id_client;
    private int id_cursa;
    private int nrLoc;

    public addRezervareRequest(int id, int id_client, int id_cursa, int nrLoc) {
        this.id = id;
        this.id_client = id_client;
        this.id_cursa = id_cursa;
        this.nrLoc = nrLoc;
    }

    public int getId() {
        return id;
    }


    public int getId_client() {
        return id_client;
    }


    public int getId_cursa() {
        return id_cursa;
    }


    public int getNrLoc() {
        return nrLoc;
    }


}
