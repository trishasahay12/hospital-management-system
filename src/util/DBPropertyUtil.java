package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getProperty(String propertyFileName){
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(propertyFileName)){
            properties.load(input);
            
            String hostname=properties.getProperty("hostname");
            String dbname = properties.getProperty("dbname");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String port = properties.getProperty("port");

            return "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //properties.load(new FileInputStream("resources/db.properties")); 
        return null;
    }
}
