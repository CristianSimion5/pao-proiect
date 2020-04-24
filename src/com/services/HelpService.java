package com.services;

public class HelpService {

    static public <T> T nvl(T a, T b) {
        return (a == null) ? b : a;
    }

    static public String nvl2(String a) {
        return (a == null) ? "" : a;
    }
}
