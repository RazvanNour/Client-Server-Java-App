package objectProtocol;

public class gasesteLocuriCursaRequest implements Request {
    private Integer id;
    public gasesteLocuriCursaRequest(Integer id){
        this.id=id;
    }

    public Integer getIdLoc(){
        return id;
    }
}
