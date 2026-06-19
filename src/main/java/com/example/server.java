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
import java.util.Random;

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
        User user;
        switch (path) {
            case "/":
                if(!handleCheck(req,res,session)){
                    res.sendRedirect("login");
                    return;
                }
                user = (User) session.getAttribute("loggedUser");
                req.setAttribute("loggedUser", user);
                forward(req, res, "main.jsp");
                return;

            case "/roulette":
                if(!handleCheck(req,res,session)){
                    res.sendRedirect("login");
                    return;
                }else{

                user = (User) session.getAttribute("loggedUser");
                }
                req.setAttribute("loggedUser", user);
                forward(req, res, "roulette.jsp");
                return;
            case "/register":
                forward(req, res, "register.jsp");
                return;
            case "/login":
                forward(req, res, "login.jsp");
                return;
            case "/username":
                if (session == null || session.getAttribute("loggedUser") == null) {
                    res.sendRedirect("login");
                    return;
                }

                forward(req, res, "username.jsp");
                return;
            case "/logout":
                if (session != null) {
                    session.invalidate();
                }

                res.sendRedirect("login");
                return;
            case "/profil":
                if(!handleCheck(req,res,session)){
                    res.sendRedirect("login");
                    return;
                }else {
                    User userObj = (User) session.getAttribute("loggedUser");
                    req.setAttribute("username", userObj.GetUsername());
                    req.setAttribute("balance", userObj.GetBalance());
                }

                forward(req, res, "profile.jsp");
                return;
            case "/slots":
                if(!handleCheck(req,res,session)){
                    res.sendRedirect("login");
                    return;
                }else {
                    User userObj = (User) session.getAttribute("loggedUser");
                }

                forward(req, res, "slots.jsp");
                return;
            default:
                RequestDispatcher defaultDispatcher = getServletContext().getNamedDispatcher("default");
                
                if (defaultDispatcher != null) {
                    defaultDispatcher.forward(req, res);
                    return;
                }
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

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
        User user = (User) session.getAttribute("loggedUser");
        try (Connection conn = Databaseconnection.getConnection()) {
            
            Querysql database = new Querysql(conn);
            switch (path) {
                case "/register":
                    handleRegister(req, res, database);
                    return;
                case "/login":
                    handleLogin(req, res, database);
                    return;
                case "/username":
                    handleUsername(req, res, database);
                    return;
                case "/roulette":

                    String numberStr = req.getParameter("number");
                    String betType = req.getParameter("betType");
                    String betValue = req.getParameter("betValue");

                    if (user == null) {
                        res.sendRedirect("login");
                        return;
                    }
                    
                    if (betType == null || betType.isBlank() || betValue == null || betValue.isBlank()) {
                        req.setAttribute("result", "Uzupełnij wszystkie wymagane pola.");
                        req.setAttribute("loggedUser", user);
                        forward(req, res, "roulette.jsp");
                        return;
                    }
 
                    int bet = Integer.parseInt(betValue);
                    if (bet <= 0) {
                        req.setAttribute("result", "Zakład musi być większy od 0.");
                        req.setAttribute("loggedUser", user);
                        forward(req, res, "roulette.jsp");
                        return;
                    }

                    if (bet > user.GetBalance()) {
                        req.setAttribute("result", "Nie masz tyle coinsów.");
                        req.setAttribute("loggedUser", user);
                        forward(req, res, "roulette.jsp");
                        return;
                    }

                    int chosenNumber = 0;

                    if (betType.equals("number")) {
                        if (numberStr == null || numberStr.isBlank()) {
                            req.setAttribute("result", "Podaj liczbę od 0 do 36.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "roulette.jsp");
                            return;
                        }

                        chosenNumber = Integer.parseInt(numberStr);

                        if (chosenNumber < 0 || chosenNumber > 36) {
                            req.setAttribute("result", "Liczba musi być od 0 do 36.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "roulette.jsp");
                            return;
                        }
                    }

                    int randomnum = (int)(Math.random() * 37);
                    int multiplier = roulette(betType, chosenNumber, randomnum);

                    int balanceChange;
                    String resultMessage;

                    if (multiplier > 0) {
                        balanceChange = bet * multiplier;
                        resultMessage = "Wygrałeś " + balanceChange + " coins!";
                    } else {
                        balanceChange = -bet;
                        resultMessage = "Przegrałeś " + bet + " coins.";
                    }

                    user.addBalance(balanceChange);
                    database.updateUsersBalance(user.GetBalance(), user.GetId());

                    req.setAttribute("loggedUser", user);
                    req.setAttribute("choice", betType);
                    req.setAttribute("bet", String.valueOf(bet));
                    req.setAttribute("number", numberStr);
                    req.setAttribute("rolledNumber", String.valueOf(randomnum));
                    req.setAttribute("result", resultMessage);

                    session.setAttribute("loggedUser", user);
                    session.setAttribute("lastRolledNumber", randomnum);

                    forward(req, res, "roulette.jsp");
                    return;
                case "/slots":
                    if (user == null) {
                        res.sendRedirect("login");
                        return;
                    }
                    int betSlot = 10;

                    if (user.GetBalance() < betSlot) {
                        req.setAttribute("error", "Brak środków! Doładuj konto w profilu.");
                        forward(req, res, "slots.jsp");
                        return;
                    }
                    String resultSlots="lose";
                    int winAmountSlots=0;
                    Random random = new Random();
                    int slot1 = random.nextInt(7) + 1;
                    int slot2 = random.nextInt(7) + 1;
                    int slot3 = random.nextInt(7) + 1;
                    if (slot1 == slot2 && slot2 == slot3) {
                        resultSlots = "jackpot";
                    } else if (slot1 == slot2 || slot2 == slot3 || slot1 == slot3) {
                        resultSlots = "win";
                    }
                    if (resultSlots.equals("lose")){
                        winAmountSlots=-betSlot;

                    }else if (resultSlots.equals("win")){
                    
                        winAmountSlots=betSlot;
                    } else {
                    
                        winAmountSlots=betSlot*10;
                    }
                    user.addBalance(winAmountSlots);
                    database.updateUsersBalance(user.GetBalance(), user.GetId());
                    req.setAttribute("slot1", slot1);
                    req.setAttribute("slot2", slot2);
                    req.setAttribute("slot3", slot3);
                    req.setAttribute("resultType", resultSlots);
                    req.setAttribute("winAmount", winAmountSlots);
                    req.setAttribute("newBalance", user.GetBalance());
                    forward(req, res, "slots.jsp");
                    return;
                case "/logout":

                    if (session != null) {
                        session.invalidate();
                    }

                    res.sendRedirect("login");
                    return;
                default:
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Wystąpił błąd serwera");
            forward(req, res, "login.jsp");
        }
 

    }

    

    

    
    

    
    
    

    public void destroy(){}
}
