package com.example;


import static utils.Forward.*;
import static utils.Handlelogin.*;
import static utils.Handleregister.*;
import static utils.Handleusername.handleUsername;
import static utils.Gateway.handleCheck;
import static com.example.RouletteMethod.*;
import static com.example.BlackjackMethod.*;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.servlet.http.*;
import utils.BlackjackCard;
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
                try(Connection conn = Databaseconnection.getConnection()){
                    user = (User) session.getAttribute("loggedUser");
                    Querysql database = new Querysql(conn);
                    String GameName = database.getLastGames(user.GetId());
                    Integer numGames = database.getNumGames(user.GetId());
                    Integer numWinGames = database.getNumWinGames(user.GetId());
                    Integer numLoseGames = database.getNumLoseGames(user.GetId());
                    req.setAttribute("loggedUser", user);
                    req.setAttribute("GameName", GameName);
                    req.setAttribute("numGames", numGames);
                    req.setAttribute("numWinGames", numWinGames);
                    req.setAttribute("numLoseGames", numLoseGames);
                    forward(req, res, "main.jsp");
                    return;
                } catch (Exception e){
                    e.printStackTrace();
                    req.setAttribute("errorMessage", "Wystąpił błąd serwera");
                    forward(req, res, "login.jsp");
                }

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
            case "/buy-coins":
                if(!handleCheck(req,res,session)){
                    res.sendRedirect("login");
                    return;
                }else{

                    user = (User) session.getAttribute("loggedUser");
                }
                int amountToAdd = 500;
                int newBalance = user.GetBalance() + amountToAdd;
                
                try {
                    Connection conn = Databaseconnection.getConnection();
                    Querysql database = new Querysql(conn);
                    user.addBalance(amountToAdd);
                    database.updateUsersBalance(newBalance, user.GetId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                res.sendRedirect("profil");
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
                    user = (User) session.getAttribute("loggedUser");
                    req.setAttribute("username", user.GetUsername());
                    req.setAttribute("balance", user.GetBalance());
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
            case "/blackjack":
                if (!handleCheck(req, res, session)) {
                    res.sendRedirect("login");
                    return;
                }
                user = (User) session.getAttribute("loggedUser");
                req.setAttribute("loggedUser", user);

                forward(req, res, "blackjack.jsp");
                return;
            case "/history":
                if (!handleCheck(req, res, session)) {
                    res.sendRedirect("login");
                    return;
                }
                user = (User) session.getAttribute("loggedUser");
                req.setAttribute("loggedUser", user);
                List<Gamehistory> gamehistory;
                try{
                    Connection conn = Databaseconnection.getConnection();
                    Querysql database = new Querysql(conn);
                    gamehistory = database.getUsersGameHistory(user.GetId());
                    req.setAttribute("history", gamehistory);

                } catch (Exception e){
                    e.printStackTrace();
                }
                forward(req, res, "history.jsp");
                return;

            case "/change-password":
                if(!handleCheck(req,res,session)){
                    res.sendRedirect("login");
                    return;
                }
                req.getSession().getAttribute("loggedUser");
                forward(req, res, "change-password.jsp");
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
            String resultMessageDatabase;
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
                    Random randomC = new Random();
                    int randomnum = randomC.nextInt(37);
                    int multiplier = roulette(betType, chosenNumber, randomnum);

                    int balanceChange;
                    String resultMessage;

                    if (multiplier > 0) {
                        balanceChange = bet * multiplier;
                        resultMessage = "Wygrałeś " + balanceChange + " coins!";
                        resultMessageDatabase="Wygrałeś!";
                    } else {
                        balanceChange = -bet;
                        resultMessage = "Przegrałeś " + bet + " coins.";
                        resultMessageDatabase="Przegrałeś...";
                    }

                    user.addBalance(balanceChange);
                    database.updateUsersBalance(user.GetBalance(), user.GetId());

                    req.setAttribute("loggedUser", user);
                    req.setAttribute("choice", betType);
                    req.setAttribute("bet", bet);
                    req.setAttribute("number", numberStr);
                    req.setAttribute("rolledNumber", String.valueOf(randomnum));
                    req.setAttribute("result", resultMessage);

                    session.setAttribute("loggedUser", user);
                    session.setAttribute("lastRolledNumber", randomnum);
                    req.setAttribute("changeBalance", balanceChange);
                    req.setAttribute("newBalance", user.GetBalance());
                    database.saveGameHistory(user,"Ruletka",bet,resultMessageDatabase,balanceChange);
                    forward(req, res, "roulette.jsp");
                    return;

                case "/blackjack":
                    if (user == null) {
                        res.sendRedirect("login");
                        return;
                    }

                    String action = req.getParameter("action");
                    
                    if (action == null) {
                        req.setAttribute("error", "Nieprawidłowa akcja.");
                        req.setAttribute("loggedUser", user);
                        forward(req, res, "blackjack.jsp");
                        return;
                    }
                    if (action.equals("start")) {
                        String betValueBlackjack = req.getParameter("bet");

                        if (betValueBlackjack == null || betValueBlackjack.isBlank()) {
                            req.setAttribute("error", "Podaj zakład.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "blackjack.jsp");
                            return;
                        }

                        int betBlackjack = Integer.parseInt(betValueBlackjack);

                        if (betBlackjack <= 0) {
                            req.setAttribute("error", "Zakład musi być większy od 0.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "blackjack.jsp");
                            return;
                        }

                        if (betBlackjack > user.GetBalance()) {
                            req.setAttribute("error", "Nie masz tylu coinów.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "blackjack.jsp");
                            return;
                        }

                        List<BlackjackCard> deck = createDeck();
                        List<BlackjackCard> playerHand = new ArrayList<>();
                        List<BlackjackCard> dealerHand = new ArrayList<>();

                        playerHand.add(drawCard(deck));
                        dealerHand.add(drawCard(deck));
                        playerHand.add(drawCard(deck));
                        dealerHand.add(drawCard(deck));

                        session.setAttribute("blackjackDeck", deck);
                        session.setAttribute("playerHand", playerHand);
                        session.setAttribute("dealerHand", dealerHand);
                        session.setAttribute("blackjackBet", betBlackjack);
                        session.setAttribute("blackjackFinished", false);

                        req.setAttribute("loggedUser", user);
                        forward(req, res, "blackjack.jsp");
                        return;
                    }
                    
                    @SuppressWarnings("unchecked") List<BlackjackCard> deck = (List<BlackjackCard>) session.getAttribute("blackjackDeck");
                    @SuppressWarnings("unchecked") List<BlackjackCard> playerHand = (List<BlackjackCard>) session.getAttribute("playerHand");
                    @SuppressWarnings("unchecked") List<BlackjackCard> dealerHand = (List<BlackjackCard>) session.getAttribute("dealerHand");
                    Integer betBlackjack = (Integer) session.getAttribute("blackjackBet");

                    if (deck == null || playerHand == null || dealerHand == null || betBlackjack == null) {
                        req.setAttribute("error", "Najpierw rozpocznij grę.");
                        req.setAttribute("loggedUser", user);
                        forward(req, res, "blackjack.jsp");
                        return;
                    }
                    if (action.equals("hit")) {
                        playerHand.add(drawCard(deck));

                        int playerScore = calculateScore(playerHand);

                        if (playerScore > 21) {
                            int change = -betBlackjack;

                            user.addBalance(change);
                            database.updateUsersBalance(user.GetBalance(), user.GetId());
                            session.setAttribute("loggedUser", user);

                            req.setAttribute("message", "Przegrałeś! Masz ponad 21.");
                            req.setAttribute("gameResult", "lose");
                            session.setAttribute("blackjackFinished", true);
                        }

                        req.setAttribute("loggedUser", user);
                        forward(req, res, "blackjack.jsp");
                        return;
                    }
                    if (action.equals("stand")) {
                        int dealerScore = calculateScore(dealerHand);

                        while (dealerScore < 17) {
                            dealerHand.add(drawCard(deck));
                            dealerScore = calculateScore(dealerHand);
                        }

                        int playerScore = calculateScore(playerHand);

                        int change = 0;
                        String message;
                        String gameResult;

                        if (dealerScore > 21) {
                            change = betBlackjack;
                            message = "Wygrałeś! Krupier przekroczył 21.";
                            gameResult = "win";
                            resultMessageDatabase="Wygrałeś!";
                        } else if (playerScore > dealerScore) {
                            change = betBlackjack;
                            message = "Wygrałeś!";
                            gameResult = "win";
                            resultMessageDatabase="Wygrałeś!";
                        } else if (playerScore < dealerScore) {
                            change = -betBlackjack;
                            message = "Przegrałeś.";
                            gameResult = "lose";
                            resultMessageDatabase="Przegrałeś...";
                        } else {
                            change = 0;
                            message = "Remis. Zakład zwrócony.";
                            gameResult = "draw";
                            resultMessageDatabase="Remis";
                        }

                        user.addBalance(change);
                        database.updateUsersBalance(user.GetBalance(), user.GetId());
                        session.setAttribute("loggedUser", user);

                        req.setAttribute("message", message);
                        req.setAttribute("gameResult", gameResult);
                        req.setAttribute("loggedUser", user);
                        session.setAttribute("blackjackFinished", true);
                        database.saveGameHistory(user, "Blackjack", betBlackjack, resultMessageDatabase, change);
                        forward(req, res, "blackjack.jsp");
                        return;
                    }

                    if (action.equals("reset")) {
                        session.removeAttribute("blackjackDeck");
                        session.removeAttribute("playerHand");
                        session.removeAttribute("dealerHand");
                        session.removeAttribute("blackjackBet");
                        session.removeAttribute("blackjackFinished");

                        res.sendRedirect("blackjack");
                        return;
                    }

                    req.setAttribute("error", "Nieznana akcja.");
                    req.setAttribute("loggedUser", user);
                    forward(req, res, "blackjack.jsp");
                    return;


                case "/slots":
                    if (user == null) {
                        res.sendRedirect("login");
                        return;
                    }
                    String betValueSlot = req.getParameter("betValue");
                    
                    if (betValueSlot == null || betValueSlot.isBlank()) {
                            req.setAttribute("error", "Podaj zakład.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "slots.jsp");
                            return;
                        }

                        int betSlot = Integer.parseInt(betValueSlot);

                        if (betSlot <= 0) {
                            req.setAttribute("error", "Zakład musi być większy od 0.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "slots.jsp");
                            return;
                        }

                        if (betSlot > user.GetBalance()) {
                            req.setAttribute("error", "Nie masz tylu coinów.");
                            req.setAttribute("loggedUser", user);
                            forward(req, res, "slots.jsp");
                            return;
                        }

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
                        resultMessageDatabase="Przegrałeś...";
                        winAmountSlots=-betSlot;

                    }else if (resultSlots.equals("win")){
                        resultMessageDatabase="Wygrałeś!";
                        winAmountSlots=betSlot;
                    } else {
                        resultMessageDatabase="Jackpot!!!";
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
                    req.setAttribute("bet", betSlot);
                    database.saveGameHistory(user, "Jednoręki Bandyta", betSlot, resultMessageDatabase, winAmountSlots);
                    forward(req, res, "slots.jsp");
                    return;
                case "/change-password":
                    if (user == null) {
                        res.sendRedirect("login");
                        return;
                    }
                
                    String oldPassword = req.getParameter("oldPassword");
                    String newPassword = req.getParameter("newPassword");
                    String confirmPassword = req.getParameter("confirmPassword");
                    if (!user.CheckPassword(oldPassword)) {
                        req.setAttribute("error", "Aktualne hasło jest niepoprawne!");
                        forward(req, res, "change-password.jsp");
                        return;
                    }
                    if (!newPassword.equals(confirmPassword)) {
                        req.setAttribute("error", "Nowe hasła nie są identyczne!");
                        forward(req, res, "change-password.jsp");
                        return;
                    }
                    user.SetNewPassword(newPassword);
                    database.changePassword(user);
                    res.sendRedirect("profil");
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
