package repository;

import domain.RezervareLoc;

import java.util.List;

public interface IRezervareRepo {
    RezervareLoc findOne(Integer id);
    void Save(RezervareLoc r);
    void Update(Integer id, RezervareLoc r);
    void Delete(Integer id);
    List<RezervareLoc> findAll();
    int size();
}
