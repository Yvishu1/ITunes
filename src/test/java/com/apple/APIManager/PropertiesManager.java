package com.apple.APIManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesManager {

    private static final ThreadLocal<Properties> threadLocalProperties = new ThreadLocal<>();

    public static Properties loadProperties(String filePath) {
        Properties properties = threadLocalProperties.get();

        if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream(filePath)) {
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            threadLocalProperties.set(properties);
        }

        return properties;
    }

    public static Properties loadProperties1(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Map<String, String> extractQueryParams(Properties properties, String... keys) {
        Map<String, String> queryParams = new LinkedHashMap<>();
        for (String key : keys) {
            String value = properties.getProperty(key);
            if (value != null) {
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }

    public static Map<String, String> extractKeyValueSet(String filepath)
    {
        Map<String, String> propertiesMap = new LinkedHashMap<>();
        Properties properties=loadProperties1(filepath);
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            propertiesMap.put(key, value);
        }
        return propertiesMap;
    }
}
