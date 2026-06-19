package utils;

import com.example.Databaseconnection;
import com.example.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("view/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userObj = (User) session.getAttribute("loggedUser");

        if (userObj == null) {
            response.sendRedirect("login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!userObj.CheckPassword(oldPassword)) {
            request.setAttribute("error", "Aktualne hasło jest niepoprawne!");
            request.getRequestDispatcher("view/change-password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Nowe hasła nie są identyczne!");
            request.getRequestDispatcher("view/change-password.jsp").forward(request, response);
            return;
        }

        userObj.SetNewPassword(newPassword);

        try {
            Connection con = Databaseconnection.getConnection();
            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setString(1, userObj.GetPasswordHash()); 
            ps.setString(2, userObj.GetUsername());
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("profil");
    }
}