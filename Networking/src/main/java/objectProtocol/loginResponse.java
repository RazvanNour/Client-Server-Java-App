package objectProtocol;

public class loginResponse implements Response {
    private boolean rez;
    public  loginResponse(boolean rez){
        this.rez=rez;
    }

    public boolean getRez(){
        return rez;
    }
}
