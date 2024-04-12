package org.tchoo;

public class LoadedClassDetail {

    private String className;
    private String timestamp;
    private String jarFile;

    public LoadedClassDetail(String timestamp, String className, String jarFile) {
        this.timestamp = timestamp;
        this.className = className;
        this.jarFile = jarFile;
    }

    @Override
    public String toString() {
        return timestamp + ":::" + className + ":::" + jarFile;
    }
}
