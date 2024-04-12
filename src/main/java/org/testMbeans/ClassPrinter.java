package org.testMbeans;

//import java.net.URLClassLoader;
//import java.util.Arrays;

import java.net.URLClassLoader;
import java.util.Arrays;

public class ClassPrinter implements ClassPrinterMBean {
//    @Override
//    public static void printLoadedClasses() {
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
////        Class<?>[] loadedClasses = classLoader.ge.getAllLoadedClasses();
//        URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
//        Arrays.stream(urlClassLoader.getURLs()).forEach(url -> System.out.println(url));
//
////        System.out.println("Loaded classes:");
////        for (Class<?> clazz : loadedClasses) {
////            System.out.println(clazz.getName());
////        }
//    }
    public void printclazz() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
        Arrays.stream(urlClassLoader.getURLs()).forEach(url -> System.out.println(url));
    }
}
