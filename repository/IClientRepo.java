package repository;

import domain.Client;

import java.util.List;

public interface IClientRepo {
    Client findOne(Integer id);
    List<Client>findAll();
    void Save(Client c);
    void Update(Integer id,Client client);
    void Delete(Integer id);
}
