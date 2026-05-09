package utils;

import com.example.Querysql;
import com.example.User;
import static utils.Forward.forward;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Handleregister {
    public static void handleRegister(HttpServletRequest req, HttpServletResponse res, Querysql database)
            throws Exception {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String apassword = req.getParameter("apassword");
        String terms = req.getParameter("terms");
        String age = req.getParameter("age");

        if (username == null || username.isBlank()) {
            req.setAttribute("errorMessage", "Wprowadź nazwę użytkownika");
            forward(req, res, "register.jsp");
            return;
        }

        if (password == null || password.isBlank()) {
            req.setAttribute("errorMessage", "Wprowadź hasło");
            forward(req, res, "register.jsp");
            return;
        }

        if (apassword == null || !password.equals(apassword)) {
            req.setAttribute("errorMessage", "Hasła nie pasują do siebie");
            forward(req, res, "register.jsp");
            return;
        }

        if (password.length() < 4) {
            req.setAttribute("errorMessage", "Hasło musi mieć minimum 4 znaki");
            forward(req, res, "register.jsp");
            return;
        }

        if (!password.matches(".*[A-Z].*")) {
            req.setAttribute("errorMessage", "Hasło musi zawierać dużą literę");
            forward(req, res, "register.jsp");
            return;
        }

        if (!password.matches(".*[0-9].*")) {
            req.setAttribute("errorMessage", "Hasło musi zawierać liczbę");
            forward(req, res, "register.jsp");
            return;
        }

        if (terms==null || terms.isBlank()) {
            req.setAttribute("errorMessage", "Zaakceptuj regulamin");
            forward(req, res, "register.jsp");
            return;
        }
        if (age==null || age.isBlank()) {
            req.setAttribute("errorMessage", "Potwierdź swoją pełnoletność");
            forward(req, res, "register.jsp");
            return;
        }

        if (database.checkUsername(username)) {
            req.setAttribute("errorMessage", "Nazwa użytkownika już istnieje");
            forward(req, res, "register.jsp");
            return;
        }

        User tempUser = new User(username, password, "");

        database.saveUser(tempUser);

        User savedUser = database.findByUsername(username);

        HttpSession session = req.getSession();
        session.setAttribute("loggedUser", savedUser);

        res.sendRedirect("nickname");
    }
}
