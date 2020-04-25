package services;

import domain.Cursa;

import java.util.List;

public interface ICursaObserver {
    void updateCurse(List<Cursa>l) throws AppException;
}
