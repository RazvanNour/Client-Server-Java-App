package repository;

import domain.Utilizator;

import java.util.List;

public interface IUserRepo {
    Utilizator findOne(Integer id);
    List<Utilizator>findAll();
    void Save(Utilizator c);
    void Delete(Integer id);
    void Update(Integer id, Utilizator c);
    boolean Login(String username,String password);
}
