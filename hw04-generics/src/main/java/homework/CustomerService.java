package homework;

import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private NavigableMap<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Customer res = customers.firstEntry().getKey();
        return new AbstractMap.SimpleEntry<>(new Customer(res.getId(), res.getName(), res.getScores()), customers.get(res));
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Customer res = customers.higherKey(customer);
        return res == null ? null : new AbstractMap.SimpleEntry<>(new Customer(res.getId(), res.getName(), res.getScores()), customers.get(res));

    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
