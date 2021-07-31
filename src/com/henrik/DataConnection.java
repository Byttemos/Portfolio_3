package com.henrik;
import java.sql.Connection;
import java.sql.SQLException;
import java.lang.*;
import org.sqlite.JDBC;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import static java.sql.DriverManager.getConnection;



public class DataConnection {
    static Connection conn;
    static String url;

    DataConnection(String url) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        try {
            this.url = url;
            this.conn = getConnection(url);
        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void pickCourse(String course_id){
        try{
            String sql = "SELECT Courses.CourseName, Courses.CourseYear, Courses.CourseSemester" +
                ", AVG(Registrations.Grade) AS Grade " +
                "FROM Courses " + "LEFT JOIN Registrations ON Registrations.CourseID=Courses.ID " +
                "WHERE Courses.ID=" + course_id;
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println("Course name: " + rs.getString("CourseName") + " " + rs.getString("CourseSemester") +
                                   " " + rs.getString("CourseYear"));
                System.out.println("Grade average: " + rs.getString("Grade"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void pickStudent(String student_id){
        String sql = "SELECT s.name, c.CourseName, c.CourseYear, c.CourseSemester, AVG(r.Grade) as avg " +
            "FROM Students s LEFT JOIN Registrations r ON r.StudentID=s.ID " +
            "LEFT JOIN Courses c ON c.ID=r.CourseID " +
            "WHERE s.ID=" + student_id;
        try {
            ResultSet rs = this.query(sql).get();
            this.printResult(rs);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("SQL error");
        }
    }


    private Optional<ResultSet> query(String query) {
        try{
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return Optional.of(rs);
        }catch(SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void printResult(ResultSet rs) {
        try {
            ResultSetMetaData rsmeta = rs.getMetaData();
            int columnCount = rsmeta.getColumnCount();
            while(rs.next()){
                for (int i = 1; i <= columnCount; i++) {
                    String colVal = rs.getString(i);
                    System.out.print(colVal + " " + rsmeta.getColumnName(i));
                }
            }
            System.out.println("");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
