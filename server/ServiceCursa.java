package server;

import domain.Client;
import domain.Cursa;
import domain.Loc;
import domain.RezervareLoc;
import repository.IClientRepo;
import repository.ICursaRepo;
import repository.IRezervareRepo;
import repository.IUserRepo;
import services.AppException;
import services.ICursaObserver;
import services.IServiceCursa;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceCursa implements IServiceCursa {
    private ICursaRepo cursaRepository;
    private IClientRepo clientRepository;
    private IRezervareRepo rezervareRepository;
    private IUserRepo userRepository;
    private Map<String, ICursaObserver>loggedUsers;
    private final  int defaultNoThreads=5;

    public ServiceCursa(ICursaRepo cursaRepository, IClientRepo clientRepository, IRezervareRepo rezervareRepository, IUserRepo userRepository){
        this.clientRepository=clientRepository;
        this.cursaRepository=cursaRepository;
        this.rezervareRepository=rezervareRepository;
        this.userRepository=userRepository;
        loggedUsers=new ConcurrentHashMap<>();

    }

    @Override
    public synchronized List<Cursa> getAllCurse(){
        return cursaRepository.findAll();
    }

    @Override
    public synchronized Client findNumeClient(String nume) {
        for(Client c:clientRepository.findAll()){
            if(c.getNume().equals(nume))
                return c;
        }
        return null;
    }

    @Override
    public synchronized void AdaugaRezervare (int id, int id_client, int id_cursa, int nrLoc) {
        Cursa c=cursaRepository.findOne(id_cursa);
        Client d=clientRepository.findOne(id_client);
        RezervareLoc r=new RezervareLoc(id,c,d,nrLoc);
        int locD=c.getNrLocuriDisponibile();
        c.setNrLocuriDisponibile(c.getNrLocuriDisponibile()-nrLoc);
        int locS=c.getNrLocuriDisponibile()+1;

        cursaRepository.Update(c.getId(),c);
        cursaRepository.updateLocuri(c.getId(),d.getNume(),locD,locS);
        rezervareRepository.Save(r);
        notifyAllLoggedClients();
        System.out.println("ok");
        System.out.println(loggedUsers.values());
    }

    @Override
    public synchronized int NrRezervari() throws AppException {
        return rezervareRepository.size();
    }

    @Override
    public synchronized List<Loc> getLocuriCursa(Integer id) throws AppException {
        return cursaRepository.getAllLocuri(id);
    }

    @Override
    public synchronized boolean Logare(String username, String password, ICursaObserver observer) throws AppException {
        if(username!=null){
            if(loggedUsers.get(username)!=null)
                throw new AppException("user already logged in!");
            if(userRepository.Login(username,password)) {
                loggedUsers.put(username,observer);
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized void logout(String username,ICursaObserver client) throws AppException {
        ICursaObserver localClient=loggedUsers.remove(username);
        if(localClient==null)
            throw new AppException("User"+username+"is not logged in");
    }

    private synchronized void notifyAllLoggedClients(){
        System.out.println("Adaugare rezervare...\nNotificare despre rezervare ...");

        ExecutorService executor = Executors.newFixedThreadPool(defaultNoThreads);

        for(ICursaObserver client : loggedUsers.values()){

            executor.execute(()->{
                System.out.println("Notifying client ...");
                try {
                    client.updateCurse(getAllCurse());
                } catch (AppException e) {
                    System.out.println("Error notifying client ...");
                }
            });
        }
        executor.shutdown();

    }

}
