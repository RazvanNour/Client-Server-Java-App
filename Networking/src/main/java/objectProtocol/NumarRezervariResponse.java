package objectProtocol;

public class NumarRezervariResponse implements Response {
    private int size;
    public NumarRezervariResponse(int size){
        this.size=size;
    }

    public int getSize(){
        return size;
    }
}
