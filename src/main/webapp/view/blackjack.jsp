<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.User" %>
<%@ page import="utils.BlackjackCard" %>
<%@ page import="com.example.BlackjackMethod" %>
<%@ page import="java.util.List" %>

<%
    User user = (User) request.getAttribute("loggedUser");

    List<BlackjackCard> playerHand = (List<BlackjackCard>) session.getAttribute("playerHand");
    List<BlackjackCard> dealerHand = (List<BlackjackCard>) session.getAttribute("dealerHand");

    Integer bet = (Integer) session.getAttribute("blackjackBet");
    Boolean finishedObj = (Boolean) session.getAttribute("blackjackFinished");
    boolean finished = finishedObj != null && finishedObj;

    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    String gameResult = (String) request.getAttribute("gameResult");

    int playerScore = playerHand != null ? BlackjackMethod.calculateScore(playerHand) : 0;
    int dealerScore = dealerHand != null ? BlackjackMethod.calculateScore(dealerHand) : 0;
%>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Blackjack - Kasyno</title>
    <link rel="stylesheet" href="css/blackjack.css">
</head>

<body>

<div class="container blackjack-container">

    <div class="back-button">
        <a href="/">&lt;</a>
    </div>

    <div class="casino-icon">
        ♦ ♠ ♥ ♣
    </div>

    <div class="header">
        <h1>Blackjack</h1>
    </div>

    <div class="blackjack-card">

        <div class="blackjack-top">
            <div class="balance-box">
                Saldo: <strong><%= user != null ? user.GetBalance() : 0 %> coinów</strong>
            </div>

            <% if (bet != null) { %>
                <div class="balance-box">
                    Zakład: <strong><%= bet %> coinów</strong>
                </div>
            <% } %>
        </div>

        <% if (error != null) { %>
            <div class="alert-box alert-error"><%= error %></div>
        <% } %>

        <% if (message != null) { %>
            <div class="blackjack-message <%= gameResult != null ? gameResult : "" %>">
                <%= message %>
            </div>
        <% } %>

        <% if (playerHand == null || dealerHand == null) { %>

            <form action="blackjack" method="post" class="blackjack-start-form">
                <input type="hidden" name="action" value="start">

                <label for="bet">Kwota zakładu</label>
                <input type="number"
                       id="bet"
                       name="bet"
                       min="1"
                       max="<%= user != null ? user.GetBalance() : 0 %>"
                       required>

                <button type="submit" class="btn btn-gold full-btn">
                    Rozpocznij grę
                </button>
            </form>

        <% } else { %>

            <div class="table-area">

                <div class="hand-section">
                    <h2>Krupier</h2>
                    <p>Punkty: <strong><%= finished ? dealerScore : "?" %></strong></p>

                    <div class="cards-row">
                        <% for (int i = 0; i < dealerHand.size(); i++) { %>
                            <% BlackjackCard card = dealerHand.get(i); %>

                            <% if (!finished && i == 1) { %>
                                <div class="playing-card card-back">?</div>
                            <% } else { %>
                                <div class="playing-card">
                                    <%= card.getDisplay() %>
                                </div>
                            <% } %>
                        <% } %>
                    </div>
                </div>

                <div class="hand-section">
                    <h2>Gracz</h2>
                    <p>Punkty: <strong><%= playerScore %></strong></p>

                    <div class="cards-row">
                        <% for (BlackjackCard card : playerHand) { %>
                            <div class="playing-card">
                                <%= card.getDisplay() %>
                            </div>
                        <% } %>
                    </div>
                </div>

            </div>
            <div class="blackjack-actions">

                <% if (!finished) { %>
                    <form action="blackjack" method="post">
                        <input type="hidden" name="action" value="hit">
                        <button type="submit" class="btn btn-gold">Dobierz kartę</button>
                    </form>

                    <form action="blackjack" method="post">
                        <input type="hidden" name="action" value="stand">
                        <button type="submit" class="btn btn-red">Zostań</button>
                    </form>
                <% } %>

                <% if (finished) { %>
                    <form action="blackjack" method="post">
                        <input type="hidden" name="action" value="reset">
                        <button type="submit" class="btn btn-gold">Nowa gra</button>
                    </form>
                <% } %>

            </div>

        <% } %>

    </div>

</div>

</body>
</html>