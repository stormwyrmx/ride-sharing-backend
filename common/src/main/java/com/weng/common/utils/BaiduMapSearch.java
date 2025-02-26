package com.weng.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class BaiduMapSearch {
    
    public static String URL = "https://api.map.baidu.com/location/ip?";
    public static String AK = "N9k2Q0OCY62Ao9FHlLyfGcGZ94Fq3aaP";
    public static Map<String, String> param = new LinkedHashMap<>();

    // 静态代码块，类加载时就会执行
    static {
        param.put("ak", AK);
    }

    public String requestGetAK() throws Exception {
        if (URL == null || URL.length() <= 0 || param == null || param.size() <= 0) {
            return null;
        }

        StringBuffer queryString = new StringBuffer();
        queryString.append(URL);
        for (Map.Entry<?, ?> pair : param.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(UriUtils.encode((String) pair.getValue(), "UTF-8") + "&");
        }

        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }

        java.net.URL url = new URL(queryString.toString());
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();

        try (InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream(), "UTF-8");
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } finally {
            httpConnection.disconnect();
        }
    }
}
