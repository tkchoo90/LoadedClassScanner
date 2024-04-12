package org.tchoo;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface ClassLoadingInfoMBean {

    public int getLoadedClassCount();
    public List<String> getLoadedClasses();
//    public HashMap<String, String> getLoadedClassDetails();

}
