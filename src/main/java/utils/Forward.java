package utils;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Forward {
    public static void forward(HttpServletRequest req, HttpServletResponse res, String view) throws ServletException, IOException{
        try {
            req.getRequestDispatcher("/view/" + view).forward(req, res);
        } catch (ServletException e) {
            System.out.print(e);
            res.getWriter().print("error 404");

        }
    }
}
