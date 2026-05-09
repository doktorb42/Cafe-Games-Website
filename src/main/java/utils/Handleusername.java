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

        String nickname = req.getParameter("Nickname");

        if (nickname == null || nickname.isBlank()) {
            req.setAttribute("errorMessage", "Wprowadź nickname");
            forward(req, res, "nickname.jsp");
            return;
        }

        if (database.checkNickname(nickname)) {
            req.setAttribute("errorMessage", "Nazwa już zajęta");
            forward(req, res, "nickname.jsp");
            return;
        }

        database.updateUsersNickname(nickname, user.GetId());

        User updatedUser = database.findByUsername(user.GetUsername());

        session.setAttribute("loggedUser", updatedUser);

        res.sendRedirect("/");
    }
    
}
