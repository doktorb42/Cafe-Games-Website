<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Gamehistory" %>
<%@ page import="com.example.User" %>

<%
    User user = (User) request.getAttribute("loggedUser");
    List<Gamehistory> history = (List<Gamehistory>) request.getAttribute("history");
%>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Historia gier - Kasyno</title>

    <link rel="stylesheet" href="css/history.css">
</head>

<body>

<div class="container history-container">

    <div class="back-button">
        <a href="/">&lt;</a>
    </div>

    <div class="casino-icon">
        ♦ ♠ ♥ ♣
    </div>

    <div class="header">
        <h1>Historia gier</h1>
    </div>

    <div class="history-card">

        <div class="history-top">
            <div class="balance-box">
                Gracz:
                <strong>
                    <%= user != null ? user.GetUsername() : "Brak danych" %>
                </strong>
            </div>

            <div class="balance-box">
                Saldo:
                <strong>
                    <%= user != null ? user.GetBalance() : 0 %> coinów
                </strong>
            </div>
        </div>

        <% if (history == null || history.isEmpty()) { %>

            <div class="empty-history">
                <h2>Brak historii</h2>
                <p>Nie masz jeszcze zapisanych gier.</p>
            </div>

        <% } else { %>

            <div class="table-wrapper">
                <table class="history-table">
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Gra</th>
                            <th>Zakład</th>
                            <th>Wynik</th>
                            <th>Zmiana salda</th>
                        </tr>
                    </thead>

                    <tbody>
                    <% for (Gamehistory game : history) { %>

                        <%
                            int change = game.getBalanceChange();
                            String changeClass;

                            if (change > 0) {
                                changeClass = "positive";
                            } else if (change < 0) {
                                changeClass = "negative";
                            } else {
                                changeClass = "neutral";
                            }
                        %>

                        <tr>
                            <td class="history-date"><%= game.getCreatedAt() %></td>
                            <td><%= game.getGameName() %></td>
                            <td><%= game.getBet() %> coinów</td>
                            <td><%= game.getResult() %></td>
                            <td class="<%= changeClass %>">
                                <% if (change > 0) { %>
                                    +<%= change %>
                                <% } else { %>
                                    <%= change %>
                                <% } %>
                            </td>
                        </tr>

                    <% } %>
                    </tbody>
                </table>
            </div>

        <% } %>

        <div class="history-actions">
            <a href="/" class="btn btn-gold">
                Wróć do kasyna
            </a>

            <a href="/profil" class="btn btn-red">
                Wróć do profilu
            </a>
        </div>

    </div>

</div>

</body>
</html>