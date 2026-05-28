<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.User" %>

<%
    User user = (User) request.getAttribute("loggedUser");

    if (user == null) {
        user = (User) session.getAttribute("loggedUser");
    }

    String choiceBet = (String) request.getAttribute("choice");
    String bet = (String) request.getAttribute("bet");
    String number = (String) request.getAttribute("number");
    String result = (String) request.getAttribute("result");
    String rolledNumber = (String) request.getAttribute("rolledNumber");

    if (choiceBet == null) choiceBet = "";
    if (bet == null) bet = "";
    if (number == null) number = "";
    if (result == null) result = "0";
%>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ruletka</title>
    <link rel="stylesheet" href="css/rouletteStyle.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>

<body>
<div class="container">

    <div class="top-bar">
        <a href="/" class="back-button">&lt;</a>

        <% if (user != null) { %>
            <div class="balance-box">
                <span>Saldo</span>
                <strong><%= user.GetBalance() %> coins</strong>
            </div>
        <% } %>
    </div>

    <div class="header">
        <h1>Ruletka</h1>
        <p>Postaw zakład i sprawdź swoje szczęście</p>
    </div>

    <div class="roulette-layout">

        <section class="wheel-section">
            <div class="pointer">▼</div>

            <img id="wheel"
                 class="wheel-image"
                 src="images/kolo.png"
                 alt="Koło ruletki">

            <div class="result-box">
                <% if (result != null && !result.isBlank()) { %>
                    <% if (rolledNumber != null) { %>
                        <span>Wylosowano</span>
                        <strong><%= rolledNumber %></strong>
                    <% } %>

                    <p><%= result %></p>
                <% } else { %>
                    <span>Wynik</span>
                    <p>Wynik pojawi się po postawieniu zakładu.</p>
                <% } %>
            </div>
        </section>

        <section class="bet-section">

            <form id="rouletteForm" action="roulette" method="post" accept-charset="UTF-8">

                <label for="betType">Rodzaj zakładu</label>
                <select id="betType" name="betType" required>
                    <option value="">--Wybierz--</option>
                    <option value="number" <%= choiceBet.equals("number") ? "selected" : "" %>>Dokładny numer x35</option>
                    <option value="black" <%= choiceBet.equals("black") ? "selected" : "" %>>Czarne x2</option>
                    <option value="red" <%= choiceBet.equals("red") ? "selected" : "" %>>Czerwone x2</option>
                    <option value="even" <%= choiceBet.equals("even") ? "selected" : "" %>>Parzyste x2</option>
                    <option value="odd" <%= choiceBet.equals("odd") ? "selected" : "" %>>Nieparzyste x2</option>
                </select>

                <label for="betValue">Kwota zakładu</label>
                <input type="number"
                       id="betValue"
                       name="betValue"
                       min="1"
                       <% if (user != null) { %> max="<%= user.GetBalance() %>" <% } %>
                       value="<%= bet %>"
                       required>

                <label for="number">Wybrana liczba</label>
                <input type="number"
                       id="number"
                       name="number"
                       min="0"
                       max="36"
                       value="<%= number %>">

                <small>Wypełnij tylko przy zakładzie na dokładny numer.</small>

                <button type="button" onclick="spinAndSubmit()">
                    Postaw zakład i zakręć
                </button>

            </form>

            <div class="rules-box">
                <h3>Zasady i wypłaty</h3>

                <div class="rule-row">
                    <span>Czerwone / Czarne</span>
                    <strong>x2</strong>
                </div>

                <div class="rule-row">
                    <span>Parzyste / Nieparzyste</span>
                    <strong>x2</strong>
                </div>

                <div class="rule-row">
                    <span>Dokładny numer</span>
                    <strong>x35</strong>
                </div>

                <p>Zero nie wygrywa przy kolorach, parzystych i nieparzystych.</p>
            </div>

        </section>

    </div>
</div>

<script>
let rotation = localStorage.getItem("wheelRotation");

if(rotation === null){
    rotation = 0;
} else {
    rotation = parseInt(rotation);
}

const wheel = document.getElementById("wheel");

wheel.style.transform = "rotate(" + rotation + "deg)";

let isSpinning = false;

function spinAndSubmit() {

    if(isSpinning){
        return;
    }

    isSpinning = true;

    const extraRotation = 360 * 6 + Math.floor(Math.random() * 360);

    rotation += extraRotation;

    wheel.style.transform = "rotate(" + rotation + "deg)";

    localStorage.setItem("wheelRotation", rotation);

    setTimeout(() => {
        document.getElementById("rouletteForm").submit();
    }, 2500);
}
</script>

</body>
</html>