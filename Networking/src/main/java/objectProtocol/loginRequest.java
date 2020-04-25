package objectProtocol;

public class loginRequest implements Request {
    private String username;
    private String password;

    public loginRequest(String username,String password){
        this.username=username;
        this.password=password;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
