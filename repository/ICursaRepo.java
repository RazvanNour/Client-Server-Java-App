package repository;

import domain.Cursa;
import domain.Loc;

import java.util.List;

public interface ICursaRepo {
    Cursa findOne(Integer id);
    List<Cursa> findAll();
    void Save(Cursa c);
    void Update(Integer id, Cursa cursa);
    void Delete(Integer id);
    List<Loc>getAllLocuri(Integer id);
    void updateLocuri(Integer id,String nume,Integer locD,Integer locS);
}
