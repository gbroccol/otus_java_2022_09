package homework.service;

import homework.util.Banknote;

import java.util.HashMap;

public interface AtmService {
    void deposit(HashMap<Banknote, Integer> banknotes);
    void withdraw(Integer amount);
    void printBalance();
    Integer getBalance();
}
