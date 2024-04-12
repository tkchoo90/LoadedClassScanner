package org.tchoo;

import java.util.ArrayList;
import java.util.List;

public class ClassLoadingInfo implements ClassLoadingInfoMBean {

    private final List<String> loadedClasses = new ArrayList<>();

    @Override
    public int getLoadedClassCount() {
        return loadedClasses.size();
    }

    @Override
    public List<String> getTestString() {
        return loadedClasses;
    }

    public void addStrings(String str) {
        loadedClasses.add(str);
    }
}
