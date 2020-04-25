import repository.ClientRepo;
import repository.CursaRepo;
import repository.RezervareRepo;
import repository.UserRepo;
import server.ServiceCursa;
import services.IServiceCursa;
import utils.AbstractServer;
import utils.ObjectConcurrentServer;
import utils.ServerException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55555;

    private static Properties getProperty(){
        Properties properties=new Properties();
        try {
            properties.load(new FileReader("C:\\Users\\Polizei\\Desktop\\MPP\\project2\\Server\\src\\main\\resources\\bd.config"));
            System.out.println("Properties set.");
            properties.list(System.out);
        }catch (IOException e){
            System.out.println("Cannot find bd.config"+e);
            return null;

        }
        return properties;
    }

    public static void main(String[] args) {
        Properties properties=getProperty();

        UserRepo userRepository=new UserRepo(properties);
        ClientRepo clientRepo =new ClientRepo(properties);
        CursaRepo cursaRepo =new CursaRepo(properties);
        RezervareRepo rezervareRepository=new RezervareRepo(properties, clientRepo, cursaRepo);
        IServiceCursa serverImpl=new ServiceCursa(cursaRepo, clientRepo,rezervareRepository,userRepository);
        int serverPort=defaultPort;

        System.out.println("Starting server on port: "+serverPort);
        AbstractServer server=new ObjectConcurrentServer(serverPort,serverImpl);
        try{
            server.start();
        }catch (ServerException e){
            System.err.println("Error starting the server"+e.getMessage());
        }
    }

}
