package homework.utils;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

    public TestRunner() {}

    public void runTests(String className) throws ClassNotFoundException, NoSuchMethodException  {

        Class<?> clazz = Class.forName(className);
        Method[] methods = clazz.getDeclaredMethods();
        SortedMethods sortedMethods = sortMethods(methods);
        StatisticSuccess statisticSuccess = runMethodsInOrder(clazz, sortedMethods);
        System.out.println("\nStatistic");
        printStatistic("BEFORE", statisticSuccess.getBefore(), sortedMethods.getBefore().size() * sortedMethods.getTest().size());
        printStatistic("TEST", statisticSuccess.getTest(), sortedMethods.getTest().size());
        printStatistic("AFTER", statisticSuccess.getAfter(), sortedMethods.getAfter().size() * sortedMethods.getTest().size());
        System.out.println();
    }

    private static void printStatistic(String annotation, int countSuccess, int allSize) {
        System.out.println("----------------- " + annotation + " ---------------------");
        System.out.println("All = " + allSize);
        System.out.println("Success = " + countSuccess);
        System.out.println("Failed = " + (allSize - countSuccess));
    }

    private static StatisticSuccess runMethodsInOrder(Class<?> clazz, SortedMethods sortedMethods) throws NoSuchMethodException {

        Object object = null;
        StatisticSuccess statisticSuccess = new StatisticSuccess();

        for (Method m : sortedMethods.getTest()) {
            try {
                object = clazz.getDeclaredConstructor().newInstance();
                statisticSuccess.addBefore(runMethodsList(object, sortedMethods.getBefore()));
                m.invoke(object);
                statisticSuccess.addTest(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    statisticSuccess.addAfter(runMethodsList(object, sortedMethods.getAfter()));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return statisticSuccess;
    }

    private static int runMethodsList(Object object, List<Method> methods) throws InvocationTargetException, IllegalAccessException {
        int cnt = 0;
        for(Method m : methods) {
            m.invoke(object);
            cnt++;
        }
        return cnt;
    }

    private static SortedMethods sortMethods(Method[] methods) {

        SortedMethods sortedMethods = new SortedMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                sortedMethods.getBefore().add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                sortedMethods.getTest().add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                sortedMethods.getAfter().add(method);
            }
        }
        return sortedMethods;
    }
}
