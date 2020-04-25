package objectProtocol;

import domain.Client;
import domain.Cursa;
import domain.Loc;
import services.AppException;
import services.ICursaObserver;
import services.IServiceCursa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceCursaObjectProxy implements IServiceCursa {
    private String host;
    private int port;
    private ICursaObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response>qresponse;
    private volatile boolean finished;

    public ServiceCursaObjectProxy(String host, int port){
        this.host=host;
        this.port=port;
        qresponse=new LinkedBlockingQueue<>();
    }

    private void initializeConnection(){
        try{
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Cursa> getAllCurse() {
        sendRequest(new gasesteCurseRequest());
        Response response=readResponse();
        gasesteCurseResponse response1=(gasesteCurseResponse)response;
        return response1.getCurse();
    }

    @Override
    public Client findNumeClient(String nume) {
        sendRequest(new findClientNumeRequest(nume));
        Response response=readResponse();
        findClientNumeResponse response1=(findClientNumeResponse)response;
        return response1.getClient();
    }

    @Override
    public void AdaugaRezervare(int id, int id_client, int id_cursa, int nrLoc) {
            sendRequest(new addRezervareRequest(id,id_client,id_cursa,nrLoc));
            Response response=readResponse();
    }

    @Override
    public int NrRezervari() {
        sendRequest(new NumarRezervariRequest());
        Response response=readResponse();
        NumarRezervariResponse response1=(NumarRezervariResponse)response;
        return response1.getSize();
    }

    @Override
    public List<Loc> getLocuriCursa(Integer id) {
        sendRequest(new gasesteLocuriCursaRequest(id));
        Response response=readResponse();
        gasesteLocuriCursaResponse response1=(gasesteLocuriCursaResponse)response;
        return response1.getLocuri();
    }

    @Override
    public boolean Logare(String username, String password, ICursaObserver observer) throws AppException {

        initializeConnection();
        sendRequest(new loginRequest(username, password));
        Response response = readResponse();
        loginResponse response1=(loginResponse)response;
        if(response1.getRez()) {
            this.client = observer;
            System.out.println("Client saved...");
            return true;
        }


        return false;
    }

    @Override
    public void logout(String username, ICursaObserver client) throws AppException {
        sendRequest(new logoutRequest(username));
        Response response=readResponse();
        closeConnection();
        if(response!=null){
            if(response instanceof OkResponse){
                return;
            }
            if(response instanceof ErrorResponse){
                ErrorResponse err=(ErrorResponse)response;
                throw new AppException(err.getMesaj());
            }
        }
    }



    private Response readResponse(){
        Response response=null;
        try{
            response=qresponse.take();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return response;
    }
    private void sendRequest(Request request){
        try{
            output.writeObject(request);
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void closeConnection(){
        finished=true;
        try{
            input.close();
            output.close();
            connection.close();
            client=null;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }
    private void handleUpdate(UpdateResponse response){
        if(response instanceof updateGasesteCursa){
            updateGasesteCursa update=(updateGasesteCursa)response;
            try{
                client.updateCurse(update.getList());
            }catch (AppException e){
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while(!finished){
                try {
                    Object response=input.readObject();
                    if (response instanceof UpdateResponse)
                        System.out.println("s-a adaugat o rezervare");

                    if(response instanceof gasesteCurseResponse)
                        System.out.println("S-a updatat tabelul de curse");
                    if(response instanceof gasesteLocuriCursaResponse)
                        System.out.println("S-au gasit locurile de la cursa respectiva");
                    if(response instanceof UpdateResponse ){
                        handleUpdate((UpdateResponse)response);
                    }else {

                        try{
                            qresponse.put((Response)response);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }catch (IOException e){
                    System.out.println("reading error"+e);
                }catch (ClassNotFoundException e){
                    System.out.println("reading error"+e);
                }
            }
        }
    }
}
