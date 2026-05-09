<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cafejka Internetowa</title>
    <link rel="stylesheet" href="css/rouletteStyle.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
    <div class="container">
        <div class="back-button">
            <a href="/"><</a>
        </div>
        <div class="content">
            <div class="header">
                <h1>Ruletka</h1>
            </div>
            <div class="roulette-container">
                <div class="roulette-wheel">
                    <img src="images/kolo.png" alt="Ruletka" class="wheel-image">
                </div>
                <div class="betting-area">
                    <form action="/roulette" method="post">
                        <% String choiceBet = (String) request.getAttribute("choice"); %>
                        <div class="bet-options">
                            <label for="betType">Wybierz rodzaj zakładu:</label>
                            <select id="betType" name="betType" required>
                                <option value="">--Wybierz--</option>
                                <option value="number" <% if(choiceBet!= null) {if(choiceBet.equals("number")) out.write("selected");} 
                                %>>Numer</option>
                                <option value="black" <% if(choiceBet!= null) {if(choiceBet.equals("black")) out.write("selected");}
                                %>>Black</option>
                                <option value="red" <% if(choiceBet!= null) {if(choiceBet.equals("red")) out.write("selected");}
                                %>>Red</option>
                                <option value="even" <% if(choiceBet!= null) {if(choiceBet.equals("even")) out.write("selected");}
                                %>>Parzyste</option>
                                <option value="Odd" <% if(choiceBet!= null) {if(choiceBet.equals("Odd")) out.write("selected");}
                                %>>Nieparzyste</option>
                            </select>
                        </div>
                        <div class="bet-options">
                            <label for="betValue">kwota zakładu:</label>
                            <% String bet = (String) request.getAttribute("bet"); %>
                            <% if(bet== null || bet.isBlank()){
                                bet="";
                            } %>
                            <input type="text" id="betValue" name="betValue" value="<%= bet %>" required>
                        </div>
                        <div class="bet-options">
                            <label for="Number">Wybierz liczbę:</label>
                            <input type="number" id="Number" name="Number" min="1" max="36" required disabled>
                        </div>
                        <button type="submit" class="place-bet-button">Postaw zakład</button>
                </div>
            </div>
            <div class="results-container">
            <% String result = (String) request.getAttribute("result");
                if (result != null) { %>
                    <h2>Wynik: <%= result %></h2>
            <% }%>
            </div>
        </div>
    </div>
</body>
</html>