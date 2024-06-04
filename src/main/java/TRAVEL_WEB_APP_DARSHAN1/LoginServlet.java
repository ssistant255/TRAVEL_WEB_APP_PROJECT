package TRAVEL_WEB_APP_DARSHAN1;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Connection conn = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_web_app2", "root", "darshan3009");
            String query = "SELECT password FROM users1 WHERE email = ?";
            p = conn.prepareStatement(query);
            p.setString(1, email);
            rs = p.executeQuery();

            if (rs.next()) 
            {
                String nPassword = rs.getString("password");
                if (password.equals(nPassword)) 
                {
                    HttpSession session = req.getSession();
                    session.setAttribute("isLoggedIn", true);
                    RequestDispatcher rd = req.getRequestDispatcher("/index.html");
                    rd.forward(req, resp);
                }
                else
                {
                    out.println("<html><body>");
                    out.println("<h3>Incorrect Password</h3>");
                    RequestDispatcher rd = req.getRequestDispatcher("/index1.html");
                    rd.include(req, resp);
                    out.println("</body></html>");
                }
            }
            else
            {
                out.println("<html><body>");
                out.println("<h3>User not found</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/index1.html");
                rd.include(req, resp);
                out.println("</body></html>");
            }

        } 
        catch (Exception e)
        {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            out.println("</body></html>");
        }
        finally
        {
            try 
            {
                if (rs != null) rs.close();
                if (p != null) p.close();
                if (conn != null) conn.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
}