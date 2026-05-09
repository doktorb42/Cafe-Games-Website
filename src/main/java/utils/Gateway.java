package utils;

import java.io.IOException;

import com.example.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Gateway {
    public static boolean handleCheck(HttpServletRequest req, HttpServletResponse res,HttpSession session) throws ServletException, IOException {
        if(session == null){
            return false;
        }
        User user = (User) session.getAttribute("loggedUser");
        if(user== null){
            return false;
        }
        if (user.GetUsername() == null || user.GetUsername().isBlank()) {
            res.sendRedirect("username");
            return false;

        } 
        return true;
    }
}
