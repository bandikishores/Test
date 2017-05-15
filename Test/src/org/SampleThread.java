package org;

public class SampleThread {

    public static ThreadLocal<String> tl = new ThreadLocal<>();

    static {
        tl.set("abc");
    }
}
