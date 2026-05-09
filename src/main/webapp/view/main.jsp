<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.User" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kasyno</title>
    <link rel="stylesheet" href="css/MainStyle.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
    <div class="container">
        <div class="header">
            <% User user = (User) request.getAttribute("loggedUser"); %>
            <h1>Witaj <%= user.GetNickname() %></h1>
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
                        <strong>Powodzenia przy stole 🎰</strong>
                    </div>
                </div>

                <div class="stats-boxes">
                    <div class="stat-card">
                        <span>Rozegrane gry</span>
                        <strong>21</strong>
                    </div>

                    <div class="stat-card">
                        <span>Wygrane</span>
                        <strong>9</strong>
                    </div>

                    <div class="stat-card">
                        <span>Przegrane</span>
                        <strong>12</strong>
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