package software.ii.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String db = "U05xD3";
    private static final String url = "jdbc:mysql://52.206.157.109/" + db;
    private static final String user = "U05xD3";
    private static final String pass = "53688636419";
    private static final String driver = "com.mysql.jdbc.Driver";
    public static Connection conn;
    
    public static boolean makeConnection(String url, String user, String pass) throws ClassNotFoundException {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            System.out.println("Connected to database : " + db);
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
            return false;
        }
    }
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection closed!");
    }
}
