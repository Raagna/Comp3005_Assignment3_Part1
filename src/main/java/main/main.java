package main;
import java.sql.*;


public class main {
    public static void getAllStudents(Connection conn) throws SQLException {
        // Create statement
        Statement stmt = conn.createStatement(); // Execute SQL query
        String SQL = "SELECT * FROM students";
        ResultSet rs = stmt.executeQuery(SQL); // Process the result
        while(rs.next()){
            int stuID = rs.getInt("student_id");
            String fName = rs.getString("first_name");
            String lName = rs.getString("last_name");
            String email = rs.getString("email");
            Date enrollDate = rs.getDate("enrollment_date");
            System.out.println("First Name: " + fName + ", Last Name: " + lName + ", student_id: " + stuID + ", Email: " + email + ", Enrollment Date: " + enrollDate);
        }
// Close resources
        rs.close();
        stmt.close();

    }
    public static void addStudent(String first_name, String last_name, String email, Date enrollment_date, Connection conn){
        String insertSQL = "INSERT INTO students (first_name,last_name,email,enrollment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, enrollment_date);
            pstmt.executeUpdate();
            System.out.println("Data inserted using PreparedStatement.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateStudentEmail(Integer student_id, String new_email, Connection conn){
        String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
        try( PreparedStatement pstmt = conn.prepareStatement(updateSQL);) {
            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);
            pstmt.executeUpdate();
            System.out.println("Email changed using PreparedStatement.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteStudent(Integer student_id, Connection conn){
        String DeleteSQL = "DELETE FROM students WHERE student_id = ?";
        try( PreparedStatement pstmt = conn.prepareStatement(DeleteSQL);) {
            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("Student deleted using PreparedStatement.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
// JDBC & Database credentials
        String url = "jdbc:postgresql://localhost:5432/Assignment3";
        String user = "postgres";
        String password = "password1";
        String fname = "Nick";
        String lname = "Well";
        String email = "NickWell@example.com";
        Date enrollment_date = Date.valueOf("2023-09-03");


        try { // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {

//                addStudent(fname, lname, email, enrollment_date, conn);
//                updateStudentEmail(3, "jim+beam@example.com", conn);
                deleteStudent(7, conn);
                getAllStudents(conn);
            } else {
                System.out.println("Failed to establish connection.");
            } // Close the connection (in a real scenario, do this in a finally
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}


