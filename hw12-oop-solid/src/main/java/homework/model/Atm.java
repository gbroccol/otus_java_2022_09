package homework.model;

import homework.util.Banknote;

import java.util.Map;
import java.util.Set;

public interface Atm {

    Integer getBalance();

    void deposit(Map<Banknote, Integer> banknotes);
    void withdraw(Map<Banknote, Integer> banknotes);

    Set<Banknote> getAvailableBanknotes();
    int getAvailableBanknoteCount(Banknote banknote);
}
