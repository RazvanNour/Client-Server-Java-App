package objectProtocol;

public class ErrorResponse implements Response {
    private String mesaj;
    public ErrorResponse(String mesaj){
        this.mesaj=mesaj;
    }

    public String getMesaj(){
        return mesaj;
    }
}
