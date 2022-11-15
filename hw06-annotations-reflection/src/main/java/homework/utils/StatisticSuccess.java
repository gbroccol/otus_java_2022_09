package homework.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatisticSuccess {

    private Integer before = 0;
    private Integer test = 0;
    private Integer after = 0;

    public void addBefore(Integer cnt) {
        before += cnt;
    }

    public void addTest(Integer cnt) {
        test += cnt;
    }

    public void addAfter(Integer cnt) {
        after += cnt;
    }
}
