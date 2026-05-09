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

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("errorMessage", "Wprowadź login i hasło");
            forward(req, res, "login.jsp");
            return;
        }

        User user = database.findByUsername(username);

        if (user == null || !user.CheckPassword(password)) {
            req.setAttribute("errorMessage", "Błędna nazwa użytkownika lub hasło");
            forward(req, res, "login.jsp");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", user);

        if (user.GetNickname() == null || user.GetNickname().isBlank()) {
            res.sendRedirect("nickname");
            return;
        }

        res.sendRedirect("/");
    }
}
