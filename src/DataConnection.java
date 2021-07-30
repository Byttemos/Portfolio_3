import java.sql.Connection;
import java.sql.SQLException;
import java.lang.*;
import org.sqlite.JDBC;

import static java.sql.DriverManager.getConnection;

public class DataConnection {

    Connection conn = null;
    String url;

    DataConnection(String url) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");

        this.url = url;

    }
    public void connect() throws SQLException {
        conn = getConnection(url);
    }
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}