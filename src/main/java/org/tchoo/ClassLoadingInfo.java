package org.tchoo;

import javafx.util.Pair;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassLoadingInfo implements ClassLoadingInfoMBean {

    private final List<String> loadedClasses = new ArrayList<>();
//    private final HashMap<String, String> loadedClassMap = new HashMap<>();

    @Override
    public int getLoadedClassCount() {
        return loadedClasses.size();
    }

    @Override
    public List<String> getLoadedClasses() {
        return loadedClasses;
    }

//    @Override
//    public HashMap<String, String> getLoadedClassDetails() {
//        return loadedClassMap;
//    }

    public void addClassToBeanList(String className) {
        loadedClasses.add(className);
    }

//    public void addClassLoadingInfoToBeanList(String className, String jarName, String dateLoaded) {
//        loadedClassMap.put(className, jarName + ":" + dateLoaded);
//    }
}
