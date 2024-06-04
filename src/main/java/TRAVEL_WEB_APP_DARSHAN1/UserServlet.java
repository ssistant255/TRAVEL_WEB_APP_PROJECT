package TRAVEL_WEB_APP_DARSHAN1;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_web_app2";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "darshan3009";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Background Image</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body {");
        response.getWriter().println("    background-image:url('https://cdn.dribbble.com/users/3725981/screenshots/17388263/travel_booking_signup_form_4x.png');");
        response.getWriter().println("    background-size: cover;");
        response.getWriter().println("}");
        response.getWriter().println("form {");
        response.getWriter().println("background-color: green");
        response.getWriter().println("text-align: center;");
        response.getWriter().println("}");
        response.getWriter().println("</style>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String contact = request.getParameter("contact");
        String destination = request.getParameter("destination");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                String sql = "INSERT INTO users1 (name, email, address, password, contact, destination) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setString(2, email);
                    statement.setString(3, address);
                    statement.setString(4,password);
                    statement.setString(5, contact);
                    statement.setString(6, destination);

                    int rows = statement.executeUpdate();
                    if (rows > 0) {
                        out.println("<h1>User added successfully!</h1>");
                    } else {
                        out.println("<h1>Error adding user.</h1>");
                    }
                }
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>Database error: " + e.getMessage() + "</h1>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h1>JDBC Driver not found: " + e.getMessage() + "</h1>");
        }
    }
}