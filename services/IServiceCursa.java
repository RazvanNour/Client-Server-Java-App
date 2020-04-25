package services;

import domain.Client;
import domain.Cursa;
import domain.Loc;

import java.util.List;

public interface IServiceCursa {
    List<Cursa>getAllCurse() throws AppException;
    Client findNumeClient(String nume) throws AppException;
    void AdaugaRezervare(int id,int id_client,int id_cursa,int nrLoc) throws AppException;
    int NrRezervari() throws AppException;
    List<Loc>getLocuriCursa(Integer id) throws AppException;
    boolean Logare(String username,String password,ICursaObserver observer) throws AppException;
     void logout(String username,ICursaObserver client) throws AppException;
}
