package homework;

import homework.test.AnnotationsTest;
import homework.utils.TestRunner;

public class Main {

    public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException {
        TestRunner testRunner = new TestRunner();
        testRunner.runTests(AnnotationsTest.class.getName());
    }
}
