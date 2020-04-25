package utils;

import objectProtocol.ClientObjWorker;
import services.IServiceCursa;

import java.net.Socket;

public class ObjectConcurrentServer extends AbsConcurrentServer {
    private IServiceCursa server;
    public ObjectConcurrentServer(int port, IServiceCursa server){
        super(port);
        this.server=server;
        System.out.println("ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client){
        ClientObjWorker worker=new ClientObjWorker(server,client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
