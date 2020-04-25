package objectProtocol;

public class findClientNumeRequest implements Request {
    private String nume;
    public findClientNumeRequest(String nume){
        this.nume=nume;
    }

    public String getNume(){
        return nume;
    }

}
