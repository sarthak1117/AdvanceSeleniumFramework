package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
    
    public static Logger getLogger(Class<?> clazz) {
        System.out.println("Logger initialized for: " + clazz.getName());
        return LogManager.getLogger(clazz);  // FIXED: Pass the class to LogManager
    }
}
