package com.epam.polygor.webstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManagerForPool {
    private final Properties PROPERTIES = new Properties();

    public PropertyManagerForPool(String fileName) {
        InputStream inputStream = PropertyManagerForPool.class.getClassLoader().getResourceAsStream(fileName);
        try {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}