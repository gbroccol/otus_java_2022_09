package ru.otus;

import com.google.common.collect.ComparisonChain;

/**
 *
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/gradleHelloWorld-0.1.jar
 *
 * To unzip the jar:
 * unzip -l hw01-gradle.jar
 * unzip -l gradleHelloWorld-0.1.jar
 *
 */
public class HelloOtus {
    public static void main(String... args) {

        System.out.println("---------- " + ComparisonChain.start().compare("true", "true").result() + " ----------");

    }
}
