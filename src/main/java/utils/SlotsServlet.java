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
import java.util.Random;

@WebServlet("/slots")
public class SlotsServlet extends HttpServlet {

    // Wyświetla stronę gry
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("view/slots.jsp").forward(request, response);
    }

    // Obsługuje kliknięcie przycisku "Zagraj"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userObj = (User) session.getAttribute("loggedUser");

        if (userObj == null) {
            response.sendRedirect("login");
            return;
        }

        int bet = 10; // Koszt gry

        // Sprawdzamy, czy gracz ma hajs
        if (userObj.GetBalance() < bet) {
            request.setAttribute("error", "Brak środków! Doładuj konto w profilu.");
            request.getRequestDispatcher("view/slots.jsp").forward(request, response);
            return;
        }

        // Losowanie liczb (od 1 do 7)
        Random random = new Random();
        int slot1 = random.nextInt(7) + 1;
        int slot2 = random.nextInt(7) + 1;
        int slot3 = random.nextInt(7) + 1;

        int winAmount = 0;
        String resultType = "lose";

        // Logika wygranych
        if (slot1 == slot2 && slot2 == slot3) {
            winAmount = 100;
            resultType = "jackpot";
        } else if (slot1 == slot2 || slot2 == slot3 || slot1 == slot3) {
            winAmount = 20;
            resultType = "win";
        }

        // Aktualizacja salda (+wygrana -koszt gry)
        int netChange = winAmount - bet;
        userObj.addBalance(netChange);

        // Zapis do bazy danych
        try {
            Connection con = Databaseconnection.getConnection();
            String query = "UPDATE users SET balance = ? WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userObj.GetBalance());
            ps.setString(2, userObj.GetUsername());
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Przekazanie wyników do widoku
        request.setAttribute("slot1", slot1);
        request.setAttribute("slot2", slot2);
        request.setAttribute("slot3", slot3);
        request.setAttribute("resultType", resultType);
        request.setAttribute("winAmount", winAmount);

        request.getRequestDispatcher("view/slots.jsp").forward(request, response);
    }
}