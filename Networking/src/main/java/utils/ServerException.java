package utils;

public class ServerException extends Exception{
    public ServerException(){
        super();
    }
    public ServerException(String mesaj){
        super(mesaj);
    }

    public ServerException(String mesaj,Throwable cause){
        super(mesaj,cause);
    }
}
