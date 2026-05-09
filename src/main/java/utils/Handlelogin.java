package utils;

import com.example.Querysql;
import com.example.User;
import static utils.Forward.forward;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Handlelogin {
    public static void handleLogin(HttpServletRequest req, HttpServletResponse res, Querysql database)
            throws Exception {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("errorMessage", "Wprowadź login i hasło");
            forward(req, res, "login.jsp");
            return;
        }

        User user = database.findByLogin(login);

        if (user == null || !user.CheckPassword(password)) {
            req.setAttribute("errorMessage", "Błędny login lub hasło");
            forward(req, res, "login.jsp");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", user);

        if (user.GetUsername() == null || user.GetUsername().isBlank()) {
            res.sendRedirect("username");
            return;
        }

        res.sendRedirect("/");
    }
}
