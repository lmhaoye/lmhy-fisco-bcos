package com.lmhy.fisco.ext;

import java.util.HashMap;

public class CKit {
    private static HashMap<String, String> adrMap = new HashMap<>();

    public static void init(HashMap<String, String> _map) {
        adrMap = _map;
    }

    public static String get(String type) {
        return adrMap.get(type);
    }
}
