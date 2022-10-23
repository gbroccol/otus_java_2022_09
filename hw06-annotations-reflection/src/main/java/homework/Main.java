package homework;

import homework.test.AnnotationsTest;
import homework.utils.TestRunner;

public class Main {

    public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException {
        TestRunner.runTests(AnnotationsTest.class.getName());
    }
}
