package utils;

import com.example.Querysql;
import com.example.User;

import static utils.Forward.forward;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Handleusername {
    public static void handleUsername(HttpServletRequest req, HttpServletResponse res, Querysql database)
            throws Exception {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            res.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        String username = req.getParameter("username");

        if (username == null || username.isBlank()) {
            req.setAttribute("errorMessage", "Wprowadź nazwę użytkownika");
            forward(req, res, "username.jsp");
            return;
        }

        if (database.checkUsername(username)) {
            req.setAttribute("errorMessage", "Nazwa już zajęta");
            forward(req, res, "username.jsp");
            return;
        }

        database.updateUsersUsername(username, user.GetId());

        User updatedUser = database.findByLogin(user.GetLogin());

        session.setAttribute("loggedUser", updatedUser);

        res.sendRedirect("/");
    }
    
}
