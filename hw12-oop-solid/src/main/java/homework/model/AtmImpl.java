package homework.model;

import homework.exception.NoProperBanknotes;
import homework.util.Banknote;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class AtmImpl implements Atm {

    private Integer balance = 0;

    private final Map<Banknote, Integer> depositBoxes = new TreeMap<>();

    @Override
    public Integer getBalance() { return balance;}

    @Override
    public Set<Banknote> getAvailableBanknotes() {
        return depositBoxes.keySet();
    }

    @Override
    public void deposit(Map<Banknote, Integer> banknotes) {

        int currentBanknoteCount;

        for (Map.Entry<Banknote, Integer> newBanknotes : banknotes.entrySet()) {
            currentBanknoteCount = depositBoxes.get(newBanknotes.getKey()) == null ? 0 : depositBoxes.get(newBanknotes.getKey());
            depositBoxes.put(newBanknotes.getKey(), currentBanknoteCount + newBanknotes.getValue());
            this.balance += newBanknotes.getValue() * newBanknotes.getKey().getValue();
        }
    }

    @Override
    public void withdraw(Map<Banknote, Integer> banknotes) {
        for (Map.Entry<Banknote, Integer> banknote : banknotes.entrySet()) {
            withdraw(banknote.getKey(), banknote.getValue());
        }
    }

    private void withdraw(Banknote banknote, Integer count) {
        int banknotesAmount = depositBoxes.get(banknote);
        if (banknotesAmount >= count) {
            depositBoxes.put(banknote, banknotesAmount - count);
            balance -= banknote.getValue();
        } else {
            throw new NoProperBanknotes();
        }
    }

    @Override
    public int getAvailableBanknoteCount(Banknote banknote) {
        return depositBoxes.get(banknote);
    }
}
