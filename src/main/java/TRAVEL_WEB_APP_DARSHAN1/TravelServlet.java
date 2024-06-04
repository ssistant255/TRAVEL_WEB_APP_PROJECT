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

@WebServlet("/TravelServlet")
public class TravelServlet extends HttpServlet {
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
        response.getWriter().println("    background-image:url('https://cdn.wallpapersafari.com/78/25/JU9bGD.jpg');");
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

        String destination = request.getParameter("destination");
        String departureDate = request.getParameter("departure_date");
        String returnDate = request.getParameter("return_date");
        String duration = request.getParameter("duration");

        String sql = "INSERT INTO travel_details1 (destination, departure_date, return_date, duration) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, destination);
            preparedStatement.setString(2, departureDate);
            preparedStatement.setString(3, returnDate);
            preparedStatement.setString(4, duration);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h1>Travel details submitted successfully!</h1>");
            } else {
                out.println("<h1>Error submitting travel details.</h1>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>Error connecting to the database. Please try again later.</h1>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h1>Database driver not found. Please contact the administrator.</h1>");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
        }
    }
}