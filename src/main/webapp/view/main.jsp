<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.User" %>


<%
    Integer numGames = (Integer) request.getAttribute("numGames");
    Integer numWinGames = (Integer) request.getAttribute("numWinGames");
    Integer numLoseGames = (Integer) request.getAttribute("numLoseGames");
    String GameName = (String) request.getAttribute("GameName");
%>

<!DOCTYPE html>
<html lang="pl">
<head>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cafejka Internetowa</title>
    <link rel="stylesheet" href="css/MainStyle.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
    <div class="container">
        <div class="header">
            <% User user = (User) request.getAttribute("loggedUser"); %>
            <h1>Witaj <%= user.GetUsername() %></h1>
        </div>

        <div class="content">

            <div class="sidebar">
                <div class="casino-icon">♠ ♥ ♦ ♣</div>

                <ul>
                    <li><a href="profil">Profil</a></li>
                    <li><a href="history">Historia gier</a></li>
                </ul>

                <form action="logout" method="post" accept-charset="UTF-8">
                    <input type="submit" value="Wyloguj">
                </form>
            </div>

            <div class="main-content">

                <div class="top-info">
                    <div class="balance-box">
                        <span>Stan konta</span>
                        <strong><%= user.GetBalance() %> coins</strong>
                    </div>

                    <div class="last-game-box">
                        <span>Ostatnia aktywność</span>
                        <%
                            String activityMessage;

                            if ("Blackjack".equals(GameName)) {
                                activityMessage = "Blackjack 🃏";
                            } else if ("Ruletka".equals(GameName)) {
                                activityMessage = "Ruletka 🎡";
                            } else if ("Jednoręki Bandyta".equals(GameName)) {
                                activityMessage = "Jednoręki Bandyta 🎰";
                            } else {
                                activityMessage = "Brak aktywności";
                            }
                        %>
                        <strong><%= activityMessage %></strong></br>
                        <small class="activity-hint">Ostatnio uruchomiona gra</small>
                    </div>
                </div>

                <div class="stats-boxes">
                    <div class="stat-card">
                        <span>Rozegrane gry</span>
                        <strong><%= numGames %></strong>
                    </div>

                    <div class="stat-card">
                        <span>Wygrane</span>
                        <strong><%= numWinGames %></strong>
                    </div>

                    <div class="stat-card">
                        <span>Przegrane</span>
                        <strong><%= numLoseGames %></strong>
                    </div>
                </div>

                <h2>Wybierz grę</h2>

                <ul class="games-list">
                    <li><a href="roulette">🎡 Ruletka</a></li>
                    <li><a href="blackjack">🃏 Blackjack</a></li>
                    <li><a href="slots">🎰 Jednoręki bandyta</a></li>
                </ul>

            </div>
        </div>
    </div>
</body>
</html>
