package homework.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class SortedMethods {

    private final List<Method> before = new ArrayList<>();
    private final List<Method> test = new ArrayList<>();
    private final List<Method> after = new ArrayList<>();

}
