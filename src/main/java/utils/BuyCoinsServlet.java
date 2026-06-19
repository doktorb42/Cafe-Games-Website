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

@WebServlet("/buy-coins")
public class BuyCoinsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userObj = (User) session.getAttribute("loggedUser");

        // Zabezpieczenie przed niezalogowanymi
        if (userObj == null) {
            response.sendRedirect("login");
            return;
        }

        // Kwota doładowania
        int amountToAdd = 500;
        int newBalance = userObj.GetBalance() + amountToAdd;

        // Aktualizacja salda w bazie danych
        try {
            Connection con = Databaseconnection.getConnection();
            String query = "UPDATE users SET balance = ? WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, newBalance);
            ps.setString(2, userObj.GetUsername());
            ps.executeUpdate();
            con.close();
            
            // Używamy gotowej metody Twojego kumpla!
            userObj.addBalance(amountToAdd); 

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Powrót do profilu po udanym doładowaniu
        response.sendRedirect("profil");
    }
}