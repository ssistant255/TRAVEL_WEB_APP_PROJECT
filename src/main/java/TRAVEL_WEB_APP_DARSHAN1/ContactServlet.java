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
@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_web_app2";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "darshan3009";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Background Image</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body {");
        response.getWriter().println("    background-image:url('https://i.pinimg.com/originals/7d/4b/8a/7d4b8a130d67d33265156eac4fc3c232.png');");
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
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        String sql = "INSERT INTO contact_messages (name, email, subject, message) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, subject);
            preparedStatement.setString(4, message);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h1>Thank you, " + name + "! Your message has been received.</h1>");
            } else {
                out.println("<h1>Error submitting your message. Please try again.</h1>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>Error connecting to the database: " + e.getMessage() + "</h1>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h1>Database driver not found: " + e.getMessage() + "</h1>");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}