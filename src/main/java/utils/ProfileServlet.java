package utils;

import com.example.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profil")
public class ProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Pobieramy zalogowanego gracza z sesji
        User userObj = (User) session.getAttribute("loggedUser");

        // Jeśli niezalogowany - wyrzucamy do logowania
        if (userObj == null) {
            response.sendRedirect("login");
            return;
        }

        // Przekazujemy dane do strony HTML (bierzemy żetony prosto z obiektu, więc nie będzie już 0!)
        request.setAttribute("username", userObj.GetUsername());
        request.setAttribute("balance", userObj.GetBalance());
        
        // Dodajemy fikcyjny status konta jako bajer
        request.setAttribute("accountStatus", "Gracz VIP");

        // Idziemy do widoku (pamiętaj, że masz to w folderze view!)
        request.getRequestDispatcher("view/profile.jsp").forward(request, response);
    }
}