package homework.service;

import homework.exception.NoProperBanknotes;
import homework.exception.NotEnoughMoneyException;
import homework.model.AtmImpl;
import homework.util.Banknote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Банкомат (сервис) ")
class AtmServiceImplTest {

    private AtmServiceImpl atmServiceImpl;

    @BeforeEach
    void setUp() {
        atmServiceImpl = new AtmServiceImpl(new AtmImpl());
    }

    @Test
    @DisplayName("должен положить деньги и вернуть корректный баланс.")
    void depositMoneyAndCheckBalance() {
        Map<Banknote, Integer> deposit = new HashMap<>();
        deposit.put(Banknote.ONE_HUNDRED, 2);
        deposit.put(Banknote.FIVE_HUNDREDS, 10);

        atmServiceImpl.deposit((HashMap<Banknote, Integer>) deposit);

        Assertions.assertEquals(5200, atmServiceImpl.getBalance());
    }

    @Test
    @DisplayName("должен положить и снять деньги и вернуть корректный баланс.")
    void depositAndWithdrawMoneyAndCheckBalance() {
        Map<Banknote, Integer> deposit = new HashMap<>();
        deposit.put(Banknote.ONE_HUNDRED, 2);
        deposit.put(Banknote.FIVE_HUNDREDS, 10);

        atmServiceImpl.deposit((HashMap<Banknote, Integer>) deposit);
        atmServiceImpl.withdraw(500);

        Assertions.assertEquals(4700, atmServiceImpl.getBalance());
    }

    @Test
    @DisplayName("должен выбросить исключение - Нет необходимых банкнот.")
    void throwExceptionNoProperBanknotes() {
        Map<Banknote, Integer> deposit = new HashMap<>();
        deposit.put(Banknote.ONE_HUNDRED, 10);
        atmServiceImpl.deposit((HashMap<Banknote, Integer>) deposit);

        Assertions.assertThrows(NoProperBanknotes.class, () -> atmServiceImpl.withdraw(550));
        Assertions.assertEquals(1000, atmServiceImpl.getBalance());
    }

    @Test
    @DisplayName("должен выбросить исключение - Недостаточно средств в банкомате.")
    void throwExceptionNotEnoughMoneyException() {
        Map<Banknote, Integer> deposit = new HashMap<>();
        deposit.put(Banknote.ONE_HUNDRED, 10);

        atmServiceImpl.deposit((HashMap<Banknote, Integer>) deposit);

        Assertions.assertThrows(NotEnoughMoneyException.class, () -> atmServiceImpl.withdraw(1100));
        Assertions.assertEquals(1000, atmServiceImpl.getBalance());
    }
}


