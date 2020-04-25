package objectProtocol;

import domain.Loc;

import java.util.List;

public class gasesteLocuriCursaResponse implements Response {
    private List<Loc>lista;
    public gasesteLocuriCursaResponse(List<Loc>lista){
        this.lista=lista;
    }
    public List<Loc>getLocuri(){
        return lista;
    }
}
