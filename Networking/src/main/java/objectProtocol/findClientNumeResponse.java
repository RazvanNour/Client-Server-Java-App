package objectProtocol;

import domain.Client;

public class findClientNumeResponse implements Response {
    private Client c;
    public findClientNumeResponse(Client c){
        this.c=c;
    }

    public Client getClient(){
        return c;
    }
}
