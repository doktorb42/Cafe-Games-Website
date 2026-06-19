<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.User" %>

<%
    User currentUser = (User) session.getAttribute("loggedUser");

    int currentBalance = (currentUser != null) ? currentUser.GetBalance() : 0;

    boolean hasSpun = request.getAttribute("slot1") != null;

    int finalSlot1 = hasSpun ? Integer.parseInt(request.getAttribute("slot1").toString()) : 7;
    int finalSlot2 = hasSpun ? Integer.parseInt(request.getAttribute("slot2").toString()) : 7;
    int finalSlot3 = hasSpun ? Integer.parseInt(request.getAttribute("slot3").toString()) : 7;

    String resultType = (String) request.getAttribute("resultType");
    if (resultType == null) resultType = "";

    Integer winAmountObj = (Integer) request.getAttribute("winAmount");
    int winAmount = (winAmountObj != null) ? winAmountObj : 0;

    Integer newBalanceObj = (Integer) request.getAttribute("newBalance");
    int newBalance = (newBalanceObj != null) ? newBalanceObj : currentBalance;

    int oldBalance = hasSpun ? currentBalance - winAmount : currentBalance;

    String message = "Postaw zakład i zakręć!";

    if (hasSpun) {
        if ("jackpot".equals(resultType)) {
            message = "JACKPOT! Wygrałeś " + winAmount + " coins!";
        } else if ("win".equals(resultType)) {
            message = "Wygrałeś " + winAmount + " coins!";
        } else {
            message = "Przegrałeś " + Math.abs(winAmount) + " coins.";
        }
    }
%>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Jednoręki Bandyta - Kasyno</title>
    <link rel="stylesheet" href="css/slots.css">
</head>

<body>
<div class="container">

    <div class="back-button">
        <a href="/">&lt;</a>
    </div>

    <div class="header">
        <h1>Jednoręki Bandyta</h1>
    </div>

    <div class="content slots-layout">

        <div class="machine-card">

            <div class="slot-handle" id="slotHandle">
                <div class="handle-rod"></div>
                <div class="handle-ball"></div>
            </div>

            <div class="balance-box">
                Saldo:
                <strong id="balanceText"><%= oldBalance %></strong>
                <strong> coins</strong>
            </div>

            <div class="reels-window">
                <div class="reel" id="reel1"></div>
                <div class="reel" id="reel2"></div>
                <div class="reel" id="reel3"></div>
            </div>

            <div class="msg-box <%= resultType %>" id="messageBox">
                <%= hasSpun ? "Kręcenie..." : message %>
            </div>

            <form action="slots" method="post" id="slotsForm">
                <button type="button" class="spin-button" id="spinButton" onclick="pullHandleAndSpin()">
                    Zakręć za 10 coins
                </button>
            </form>

        </div>

    </div>

</div>

<script>
    const hasSpun = <%= hasSpun %>;

    const finalSlots = [
        <%= finalSlot1 %>,
        <%= finalSlot2 %>,
        <%= finalSlot3 %>
    ];

    const newBalance = <%= newBalance %>;
    const finalMessage = "<%= message %>";
    const resultType = "<%= resultType %>";

    const symbolHeight = 120;
    const symbolsCount = 7;

    const reel1 = document.getElementById("reel1");
    const reel2 = document.getElementById("reel2");
    const reel3 = document.getElementById("reel3");

    const messageBox = document.getElementById("messageBox");
    const balanceText = document.getElementById("balanceText");
    const spinButton = document.getElementById("spinButton");
    const slotsForm = document.getElementById("slotsForm");
    const slotHandle = document.getElementById("slotHandle");

    function pullHandleAndSpin() {
        if (spinButton.disabled) {
            return;
        }

        spinButton.disabled = true;

        slotHandle.classList.add("pulled");

        setTimeout(() => {
            slotHandle.classList.remove("pulled");
        }, 250);

        setTimeout(() => {
            slotsForm.submit();
        }, 500);
    }
    function finalPosition(slotNumber) {
        return -((slotNumber - 1) * symbolHeight);
    }

    function animateSpriteReel(reel, finalNumber, spins, duration) {
        return new Promise((resolve) => {
            const finalY = finalPosition(finalNumber);
            const spinDistance = spins * symbolsCount * symbolHeight;
            const targetY = -(spinDistance + ((finalNumber - 1) * symbolHeight));

            reel.classList.add("spinning");
            reel.style.transition = "none";
            reel.style.backgroundPositionY = "0px";

            reel.offsetHeight;

            reel.style.transition = "background-position-y " + duration + "ms cubic-bezier(0.12, 0.75, 0.15, 1)";
            reel.style.backgroundPositionY = targetY + "px";

            setTimeout(() => {
                reel.style.transition = "none";
                reel.style.backgroundPositionY = finalY + "px";
                reel.classList.remove("spinning");
                reel.classList.add("stopped");
                resolve();
            }, duration);
        });
    }

    async function startSlotAnimation() {
        spinButton.disabled = true;

        messageBox.textContent = "Kręcenie...";
        messageBox.className = "msg-box";

        reel1.classList.remove("stopped");
        reel2.classList.remove("stopped");
        reel3.classList.remove("stopped");

        await Promise.all([
            animateSpriteReel(reel1, finalSlots[0], 8, 1800),
            animateSpriteReel(reel2, finalSlots[1], 10, 2400),
            animateSpriteReel(reel3, finalSlots[2], 12, 3000)
        ]);

        messageBox.textContent = finalMessage;
        messageBox.className = "msg-box " + resultType;

        balanceText.textContent = newBalance;

        spinButton.disabled = false;
    }

    if (hasSpun) {
        startSlotAnimation();
    } else {
        reel1.style.backgroundPositionY = finalPosition(7) + "px";
        reel2.style.backgroundPositionY = finalPosition(7) + "px";
        reel3.style.backgroundPositionY = finalPosition(7) + "px";
    }
</script>

</body>
</html>