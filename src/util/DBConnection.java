package util;

//import java.io.FileInputStream;
import java.beans.PropertyChangeEvent;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.util.Properties;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection()  {
        if (connection == null) {
            //Properties properties = new Properties();
            //properties.load(new FileInputStream("resources/db.properties"));

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String connectionString = DBPropertyUtil.getProperty("resources/db.properties");
                connection = DriverManager.getConnection(connectionString);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static void dbclose(){
        try {
           if(connection != null)
           {
            connection.close();
           } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
