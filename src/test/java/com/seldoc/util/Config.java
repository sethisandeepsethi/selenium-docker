package com.seldoc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Config {

    public static final String DEFAULT_CONFIG_PATH = "config/default.properties";
    public static final Logger log = LoggerFactory.getLogger(Config.class);
    public static Properties properties;

    //Initialize properties
    public static void initialize(){
        properties = loadProperties(DEFAULT_CONFIG_PATH);

        for(String key: properties.stringPropertyNames()){
            if(System.getProperties().containsKey(key))
                properties.setProperty(key,System.getProperty(key));
        }

        for(String key: properties.stringPropertyNames())
            log.info("{} = {}", key, properties.get(key));
    }

    //Get Property
    public static String get(String key){
        return properties.getProperty(key);
    }

    //Load properties
    public static Properties loadProperties(String propertyFilePath){
        Properties properties = new Properties();
        try {
            properties.load(ResourceLoader.getResource(propertyFilePath));
        } catch (Exception e) {
            log.error("Unable to read the property file {}", DEFAULT_CONFIG_PATH, e);
        }
        return properties;
    }

}
