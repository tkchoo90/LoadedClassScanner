package org.tchoo;

import java.util.List;

public interface ClassLoadingInfoMBean {

    public int getLoadedClassCount();
    public List<String> getTestString();

}
