package homework.utils;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static homework.utils.Utils.BEFORE;
import static homework.utils.Utils.TEST;
import static homework.utils.Utils.AFTER;

public class TestRunner {

    public static void runTests(String className) throws ClassNotFoundException, NoSuchMethodException  {

        Class<?> clazz = Class.forName(className);
        Method[] methods = clazz.getDeclaredMethods();
        Map<String, List<Method>> sortedMethods = sortMethods(methods);
        int[] countSuccess = runMethodsInOrder(clazz, sortedMethods);
        System.out.println("\nStatistic");
        printStatistic(BEFORE, countSuccess[0], sortedMethods.get(BEFORE).size() * sortedMethods.get(TEST).size());
        printStatistic(TEST, countSuccess[1], sortedMethods.get(TEST).size());
        printStatistic(AFTER, countSuccess[2], sortedMethods.get(AFTER).size() * sortedMethods.get(TEST).size());
        System.out.println();
    }

    private static void printStatistic(String annotation, int countSuccess, int allSize) {
        System.out.println("----------------- " + annotation + " ---------------------");
        System.out.println("All = " + allSize);
        System.out.println("Success = " + countSuccess);
        System.out.println("Failed = " + (allSize - countSuccess));
    }

    private static int[] runMethodsInOrder(Class<?> clazz, Map<String, List<Method>> sortedMethods) throws NoSuchMethodException {

        Object object = null;
        int[] count = new int[3];

        for (Method m : sortedMethods.get(TEST)) {
            try {
                object = clazz.getDeclaredConstructor().newInstance();
                count[0] += runMethodsList(object, sortedMethods.get(BEFORE));
                m.invoke(object);
                count[1] += 1;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    count[2] += runMethodsList(object, sortedMethods.get(AFTER));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    private static int runMethodsList(Object object, List<Method> methods) throws InvocationTargetException, IllegalAccessException {
        int cnt = 0;
        for(Method m : methods) {
            m.invoke(object);
            cnt++;
        }
        return cnt;
    }

    private static Map<String, List<Method>> sortMethods(Method[] methods) {

        Map<String, List<Method>> sortedMethods = new HashMap<>();
        sortedMethods.put(BEFORE, new ArrayList<>());
        sortedMethods.put(TEST, new ArrayList<>());
        sortedMethods.put(AFTER, new ArrayList<>());

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                sortedMethods.get(BEFORE).add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                sortedMethods.get(TEST).add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                sortedMethods.get(AFTER).add(method);
            }
        }
        return sortedMethods;
    }
}
