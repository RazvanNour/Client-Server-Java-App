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

public class ClientObjWorker implements Runnable, ICursaObserver {
    private IServiceCursa server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private volatile boolean connected;

    public ClientObjWorker(IServiceCursa server, Socket connection){
        this.server=server;
        this.connection=connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());

            output.flush();

            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(connected){
            try{
                Object request=input.readObject();
                Object response=handleRequest((Request)request);
                if(response!=null){
                    sendResponse((Response)response);
                }
            }catch (IOException|ClassNotFoundException e){
                e.printStackTrace();
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        }catch (IOException e){
            System.out.println("Error "+e);
        }
    }



    private Response handleRequest(Request request){

        if(request instanceof loginRequest){
            System.out.println("login request");
            loginRequest request1=(loginRequest)request;
            String username=request1.getUsername();
            String password=request1.getPassword();
            try {
                return new loginResponse(server.Logare(username,password,this));
            }catch (AppException e){
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof gasesteCurseRequest){
            System.out.println("Get curse request");
            try {
                List<Cursa> curse = server.getAllCurse();
                return new gasesteCurseResponse(curse);
            }catch (AppException e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof gasesteLocuriCursaRequest){
            System.out.println("Get locuri request");
            gasesteLocuriCursaRequest request1=(gasesteLocuriCursaRequest)request;
            Integer id=request1.getIdLoc();
            try{
                List<Loc>locuri=server.getLocuriCursa(id);
                return new gasesteLocuriCursaResponse(locuri);
            }catch (AppException e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof findClientNumeRequest){
            System.out.println("get client request");
            findClientNumeRequest request1=(findClientNumeRequest)request;
            String nume=request1.getNume();
            try{
                Client c=server.findNumeClient(nume);
                return new findClientNumeResponse(c);
            }catch (AppException e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof NumarRezervariRequest){
            System.out.println("get size request");
            try{
                int size=server.NrRezervari();
                return new NumarRezervariResponse(size);
            }catch (AppException e){
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof addRezervareRequest){
            System.out.println("adauga rezervare request");
            addRezervareRequest request1=(addRezervareRequest)request;
            int id=request1.getId();
            int id_client=request1.getId_client();
            int id_cursa=request1.getId_cursa();
            int nrLoc=request1.getNrLoc();
            try{
                server.AdaugaRezervare(id,id_client,id_cursa,nrLoc);
                return new addRezervareResponse();
            }catch (AppException e){
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof logoutRequest){
            System.out.println("Logout request....");
            logoutRequest request1=(logoutRequest)request;
            String user=request1.getUsername();
            try{
                server.logout(user,this);
                connected=false;
                return new OkResponse();
            }catch (AppException e){
                return new ErrorResponse(e.getMessage());
            }
        }
        return null;
    }
    private void sendResponse(Response response)throws IOException{
        System.out.println("sending response "+response+"...");
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void updateCurse(List<Cursa> l) throws AppException {
        System.out.println("updating curse list...");
        updateGasesteCursa response=new updateGasesteCursa(l);
        try{
            sendResponse(response);
        }catch (IOException e){
            throw new AppException(e.getMessage());
        }
    }
}
