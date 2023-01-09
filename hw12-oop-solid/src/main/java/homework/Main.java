package homework;

import homework.model.AtmImpl;
import homework.service.AtmService;
import homework.service.AtmServiceImpl;
import homework.util.Banknote;
import homework.util.Color;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String... args) {

        AtmService atmService = new AtmServiceImpl(new AtmImpl());

        Map<Banknote, Integer> deposit = new HashMap<>();
        deposit.put(Banknote.ONE_HUNDRED, 2);
        deposit.put(Banknote.FIVE_HUNDREDS, 10);

        System.out.println(Color.ANSI_BLUE + "Add money (100:2шт / 500:10шт)" + Color.ANSI_RESET);
        atmService.deposit((HashMap<Banknote, Integer>) deposit);
        atmService.printBalance();

        System.out.println(Color.ANSI_BLUE + "Get money (500:1шт)" + Color.ANSI_RESET);
        atmService.withdraw(500);
        atmService.printBalance();

        System.out.println(Color.ANSI_BLUE + "Add money (10:1шт / 5000:5шт)" + Color.ANSI_RESET);
        deposit = new HashMap<>();
        deposit.put(Banknote.TEN, 1);
        deposit.put(Banknote.FIVE_THOUSANDS, 5);
        atmService.deposit((HashMap<Banknote, Integer>) deposit);
        atmService.printBalance();

        System.out.println(Color.ANSI_BLUE + "Get money (500:1шт / 100:1шт)" + Color.ANSI_RESET);
        atmService.withdraw(700);
        atmService.printBalance();
    }
}
