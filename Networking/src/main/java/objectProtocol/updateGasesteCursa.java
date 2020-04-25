package objectProtocol;

import domain.Cursa;

import java.util.List;

public class updateGasesteCursa implements UpdateResponse {
    private List<Cursa>list;
    public updateGasesteCursa(List<Cursa>l){
        list=l;
    }
    public List<Cursa>getList(){
        return list;
    }
}
