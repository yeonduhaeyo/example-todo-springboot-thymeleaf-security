package com.example.my.util;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UtilFunction {

    public static boolean isWindows() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.toLowerCase().contains("win");
    }

    public static boolean isMac() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.toLowerCase().contains("mac");
    }

    public static boolean isUnix() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }

    public static Map<String, String> parseQueryString(String url) {
        Map<String, String> queryPairs = new HashMap<>();
        try {
            URL urlObj = new URL(url);
            String query = urlObj.getQuery();
            String[] pairs = query != null ? query.split("&") : new String[0];
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryPairs;
    }

}
