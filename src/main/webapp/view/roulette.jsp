<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.User" %>

<%
    User user = (User) request.getAttribute("loggedUser");

    String choiceBet = (String) request.getAttribute("choice");

    Object betObj = request.getAttribute("bet");
    String bet = betObj != null ? betObj.toString() : "0";

    String number = (String) request.getAttribute("number");
    String result = (String) request.getAttribute("result");
    String rolledNumber = (String) request.getAttribute("rolledNumber");

    Integer changeBalance = (Integer) request.getAttribute("changeBalance");
    Integer newBalance = (Integer) request.getAttribute("newBalance");

    boolean hasSpun = rolledNumber != null && !rolledNumber.isBlank();

    int currentBalance = user != null ? user.GetBalance() : 0;
    int finalBalance = newBalance != null ? newBalance : currentBalance;
    int oldBalance = hasSpun && changeBalance != null ? finalBalance - changeBalance : currentBalance;

    if (choiceBet == null) choiceBet = "";
    if (number == null) number = "";
    if (result == null) result = "";
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
                <strong id="balanceText"><%= oldBalance %> coins</strong>
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
                <% if (hasSpun) { %>
                    <span id="rolledLabel">Kręcenie...</span>
                    <strong id="rolledNumberText">?</strong>
                    <p id="resultMessage">Koło się kręci...</p>
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
                       value="<%= bet %>"
                       required>
                <div class="number-field" id="numberField">
                    <label for="number">Wybrana liczba</label>
                    <input type="number"
                        id="number"
                        name="number"
                        min="0"
                        max="36"
                        value="<%= number %>">

                    <small>Wypełnij tylko przy zakładzie na dokładny numer.</small>
                </div>
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

if (rotation === null) {
    rotation = 0;
} else {
    rotation = parseFloat(rotation);
}

const wheel = document.getElementById("wheel");
const betTypeSelect = document.getElementById("betType");
const numberField = document.getElementById("numberField");
const numberInput = document.getElementById("number");
wheel.style.transform = "rotate(" + rotation + "deg)";

let isSpinning = false;

// Kolejność liczb na kole europejskim, zaczynając od 0 na górze i idąc zgodnie z ruchem wskazówek zegara
const wheelNumbers = [
    0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23,
    10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26
];

const sectorAngle = 360 / 37;

function getRotationForNumber(number) {
    const index = wheelNumbers.indexOf(number);

    if (index === -1) {
        return rotation;
    }

    // Liczba ma trafić pod wskaźnik na górze.
    // Dlatego obracamy koło przeciwnie o pozycję tej liczby.
    let targetAngle = -index * sectorAngle;

    // Dodajemy pełne obroty, żeby animacja wyglądała naturalnie.
    let targetRotation = Math.floor(rotation / 360) * 360 + 360 * 6 + targetAngle;

    // Gdyby wynik był za blisko aktualnej pozycji, dodajemy kolejne obroty.
    while (targetRotation <= rotation + 720) {
        targetRotation += 360;
    }

    return targetRotation;
}
function updateNumberField() {
    if (betTypeSelect.value === "number") {
        numberField.classList.add("show");
        numberInput.required = true;
    } else {
        numberField.classList.remove("show");
        numberInput.required = false;
        numberInput.value = "";
    }
}

betTypeSelect.addEventListener("change", updateNumberField);

updateNumberField();


function spinAndSubmit() {
    const form = document.getElementById("rouletteForm");

    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const betType = document.getElementById("betType").value;
    const numberInput = document.getElementById("number").value;
    
    if (betType === "number") {
        if (numberInput === "" || numberInput < 0 || numberInput > 36) {
            alert("Przy zakładzie na dokładny numer podaj liczbę od 0 do 36.");
            return;
        }
    }

    if (isSpinning) {
        return;
    }

    form.submit();
}

// Ta wartość przychodzi z Servletu po POST
const rolledNumberFromServer = "<%= rolledNumber != null ? rolledNumber : "" %>";
const finalBalanceFromServer = <%= finalBalance %>;
const resultMessageFromServer = "<%= result.replace("\"", "\\\"") %>";

if (rolledNumberFromServer !== "") {
    const rolledNumber = parseInt(rolledNumberFromServer);
    const targetRotation = getRotationForNumber(rolledNumber);

    setTimeout(() => {
        wheel.style.transform = "rotate(" + targetRotation + "deg)";
    }, 200);

    wheel.addEventListener("transitionend", function finishSpin() {
        localStorage.setItem("wheelRotation", targetRotation);
        rotation = targetRotation;

        document.getElementById("balanceText").textContent = finalBalanceFromServer + " coins";

        document.getElementById("rolledLabel").textContent = "Wylosowano";
        document.getElementById("rolledNumberText").textContent = rolledNumberFromServer;
        document.getElementById("resultMessage").textContent = resultMessageFromServer;

        wheel.removeEventListener("transitionend", finishSpin);
    });
}
</script>

</body>
</html>