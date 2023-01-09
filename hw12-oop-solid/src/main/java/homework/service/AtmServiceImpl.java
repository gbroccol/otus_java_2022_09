package homework.service;

import homework.exception.NoProperBanknotes;
import homework.exception.NotEnoughMoneyException;
import homework.model.Atm;
import homework.util.Banknote;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class AtmServiceImpl implements AtmService {

    private final Atm atm;

    @Override
    public void deposit(HashMap<Banknote, Integer> banknotes) {
        atm.deposit(banknotes);
    }

    @Override
    public void withdraw(Integer requestCount) {

        if (atm.getBalance() < requestCount)
            throw new NotEnoughMoneyException();

        Map<Banknote, Integer> outcomeBanknotes = new HashMap<>();
        Set<Banknote> availableBanknotes = atm.getAvailableBanknotes();

        int withdrawCount = 0;

        for (Banknote banknote : availableBanknotes) {
            int availableBanknoteCount = atm.getAvailableBanknoteCount(banknote);
            while (banknote.getValue() <= requestCount - withdrawCount) {
                if (availableBanknoteCount > 0) {
                    int outcomeBanknotesCount = outcomeBanknotes.get(banknote) == null ? 0 : outcomeBanknotes.get(banknote);
                    outcomeBanknotes.put(banknote, outcomeBanknotesCount + 1);
                    availableBanknoteCount--;
                    withdrawCount += banknote.getValue();
                } else {
                    break;
                }
            }
        }

        if (withdrawCount == requestCount) {
            atm.withdraw(outcomeBanknotes);
        } else {
            throw new NoProperBanknotes();
        }
    }

    @Override
    public void printBalance() {
        System.out.println("Balance = " + atm.getBalance());
        for (Banknote b : atm.getAvailableBanknotes()) {
            System.out.println("\t\t" + b.getValue() + "\t\t" + atm.getAvailableBanknoteCount(b) + "шт.");
        }
    }

    @Override
    public Integer getBalance() {
        return atm.getBalance();
    }
}
