package com.ags.pms;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
    
    private String secretKey;
    private String IV;

    public ConfigLoader() throws IOException {
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("resources/config.properties");
        properties.load(input);    
        this.secretKey = properties.getProperty("secretKey");
        this.IV = properties.getProperty("IV");
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getIV() {
        return IV;
    }
}

