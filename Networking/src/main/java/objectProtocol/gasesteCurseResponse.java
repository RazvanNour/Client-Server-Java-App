package objectProtocol;

import domain.Cursa;

import java.util.List;

public class gasesteCurseResponse implements Response {
    private List<Cursa>curse;
    public gasesteCurseResponse(List<Cursa>curse){
        this.curse=curse;
    }
    public List<Cursa>getCurse(){
        return curse;
    }
}
