package com.example;


import static utils.Forward.*;
import static utils.Handlelogin.*;
import static utils.Handleregister.*;
import static utils.Handleusername.handleUsername;
import static utils.Gateway.handleCheck;
import static com.example.RouletteMethod.*;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;



@WebServlet(name = "server", value = "/")
public class server  extends HttpServlet{
    public void init(){}

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String context = req.getContextPath();
        String path = uri.substring(context.length());
        HttpSession session = req.getSession(false);
        if (path.equals("/") || path.isEmpty()){
            if(!handleCheck(req,res,session)){
                res.sendRedirect("login");
                return;
            }
            User user = (User) session.getAttribute("loggedUser");
            req.setAttribute("loggedUser", user);
            forward(req, res, "main.jsp");
            return;

            
            /// register
            /// 
        }
        if (path.equals("/roulette")){
            if(!handleCheck(req,res,session)){
                res.sendRedirect("login");
                return;
            }
            User user = (User) session.getAttribute("loggedUser");
            req.setAttribute("loggedUser", user);
            forward(req, res, "roulette.jsp");
            return;

        }
        if (path.equals("/register")){   
            forward(req, res, "register.jsp");
            return;

            /// login
        }
        if (path.equals("/login")){
            forward(req, res, "login.jsp");
            return;

        }
        if (path.equals("/nickname")){
             if (session == null || session.getAttribute("loggedUser") == null) {
                res.sendRedirect("login");
                return;
            }

            forward(req, res, "nickname.jsp");
            return;
        }

        if (path.equals("/logout")) {
            if (session != null) {
                session.invalidate();
            }

            res.sendRedirect("login");
            return;
        }

        
        // Css dispatcher
        RequestDispatcher defaultDispatcher = getServletContext().getNamedDispatcher("default");
        
        if (defaultDispatcher != null) {
            defaultDispatcher.forward(req, res);
            return;
        }
        res.sendError(HttpServletResponse.SC_NOT_FOUND);

    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String context = req.getContextPath();
        String path = uri.substring(context.length());
        HttpSession session = req.getSession();
        PrintWriter out = res.getWriter();
        try (Connection conn = Databaseconnection.getConnection()) {
            
            Querysql database = new Querysql(conn);

            if (path.equals("/register")) {
                handleRegister(req, res, database);
                return;
            }

            if (path.equals("/login")) {
                handleLogin(req, res, database);
                return;
            }

            if (path.equals("/nickname")) {
                handleUsername(req, res, database);
                return;
            }

            if (path.equals("/roulette")) {
                User user = (User) session.getAttribute("loggedUser");
                String numberStr = req.getParameter("number");
                String choice = req.getParameter("betType");
                String betStr = req.getParameter("betValue");
                int newBalance=0;
                int multiplier;
                if(betStr != null && !betStr.isBlank()){
                    int bet = Integer.parseInt(betStr);
                    if(numberStr != null && !numberStr.isBlank()){
                        multiplier = roulette(choice, Integer.parseInt(numberStr));
                    }else{
                        multiplier = roulette(choice, 0);

                    }
                    newBalance=bet*multiplier;
                    user.addBalance(newBalance);
                    database.updateUsersBalance(user.GetBalance(), user.GetId());
                    req.setAttribute("choice", choice);
                    req.setAttribute("bet", String.valueOf(bet));
                    req.setAttribute("result", String.valueOf(newBalance));
                }
                forward(req, res, "roulette.jsp");
                return;
            }

            if (path.equals("/logout")) {

                if (session != null) {
                    session.invalidate();
                }

                res.sendRedirect("login");
                return;
            }

            res.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Wystąpił błąd serwera");
            forward(req, res, "login.jsp");
        }
 

    }

    

    

    
    

    
    
    

    public void destroy(){}
}