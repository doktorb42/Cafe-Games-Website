<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.User" %>
<%
    User currentUser = (User) session.getAttribute("loggedUser");
    int currentBalance = (currentUser != null) ? currentUser.GetBalance() : 0;
    
    // Sprawdzanie czy było kręcenie
    boolean hasSpun = request.getAttribute("slot1") != null;
    
    // Wyciągamy wylosowane liczby (1 do 7)
    String slot1 = hasSpun ? request.getAttribute("slot1").toString() : "7";
    String slot2 = hasSpun ? request.getAttribute("slot2").toString() : "7";
    String slot3 = hasSpun ? request.getAttribute("slot3").toString() : "7";
%>
<html>
<head>
    <title>Jednoręki Bandyta GOLD - Kasyno</title>
    <style>
        body {
    background-color: #0c0c0c; color: #ffffff;
    font-family: 'Times New Roman', Times, serif;
    display: flex; justify-content: center; align-items: center;
    height: 100vh; margin: 0; overflow: hidden;
}

        /* GŁÓWNA OBUDOWA MASZYNY (w 100% z CSS) */
        .machine-casing {
            background: linear-gradient(180deg, #1f1f1f, #111 70%, #1f1f1f 100%);
            border: 4px solid #d4af37; border-radius: 20px;
            padding: 30px; width: 480px; text-align: center;
            box-shadow: 0 20px 50px rgba(0,0,0,0.8), inset 0 0 15px rgba(212, 175, 55, 0.1);
            position: relative; /* Potrzebne dla pozycjonowania wajchy */
        }
        
        /* Złote ozdobne listwy */
        .machine-casing::after {
            content: ''; position: absolute; top: 15px; left: 15px; right: 15px; bottom: 15px;
            border: 1px solid rgba(212, 175, 55, 0.3); border-radius: 15px; pointer-events: none;
        }

        h1 { 
            color: #FFD700; 
            font-size: 52px;
            margin-top: 0;
            margin-bottom: 25px;
            text-shadow: 0 0 12px rgba(255,215,0,0.45);
            font-weight: normal; 
        }
            background: #000; padding: 10px 15px; border-radius: 8px; margin-bottom: 25px;
            border-left: 4px solid #00ff66; display: inline-block;
        }
        .balance span { color: #00ff66; font-weight: bold; font-size: 20px; margin-left: 10px; font-family: monospace; }
        
        /* Wnęka na bębny */
        .reels-window {
            background-color: #050505;
            border: 8px solid #222; border-radius: 10px;
            display: flex; justify-content: center; gap: 15px;
            padding: 15px; margin: 30px 0;
            box-shadow: inset 0 10px 20px rgba(0,0,0,0.9), 0 5px 15px rgba(0,0,0,0.5);
        }
        
        /* Pojedynczy bęben */
        .reel {
            width: 120px; /* Nowa szerokość zgeneratora symbole.png */
            height: 120px; /* Nowa wysokość z generatora symbole.png */
            background-color: white; 
            background-image: url('images/symbole.png'); 
            background-size: 120px 840px; /* Całkowity rozmiar sprite-sheet'a */
            background-position-y: 0px; 
            border: 3px solid #555;
            border-radius: 8px;
            box-shadow: inset 0 20px 20px -10px rgba(0,0,0,0.8), inset 0 -20px 20px -10px rgba(0,0,0,0.8);
            transition: all 0.2s cubic-bezier(0.18, 0.89, 0.32, 1.28); /* gładkie, lekko sprężyste zatrzymanie */
        }

        /* Animacja kręcenia w CSS */
        .spinning {
            animation: spinBg 0.1s linear infinite;
            filter: blur(2px); /* Mniejsze rozmycie, żeby nie wyglądało "brzydko" */
        }

        @keyframes spinBg {
            0% { background-position-y: 0px; }
            100% { background-position-y: -840px; } /* Przesuwamy o pełną wysokość sprite'a */
        }

        /* Komunikaty */
        .msg-box { min-height: 35px; margin-bottom: 20px; font-weight: bold; font-size: 18px;}
        .msg-box.win { color: #00ff66; }
        .msg-box.jackpot { color: #d4af37; font-size: 22px; text-transform: uppercase; animation: pulse 1s infinite;}
        .msg-box.lose { color: #ff4c4c; }
        @keyframes pulse { 0% { transform: scale(1); } 50% { transform: scale(1.05); } 100% { transform: scale(1); } }

        /* NOWA WAJCHA (mechaniczna, animowana) */
        .handle-mechanism {
            position: absolute; right: -40px; top: 120px; width: 30px; height: 180px;
        }
        
        .handle-rod {
            width: 10px; height: 150px; background: linear-gradient(180deg, #999, #555, #999);
            border: 1px solid #d4af37; position: absolute; left: 10px; top: 0;
            border-radius: 5px; z-index: -1; transform-origin: top center; transition: 0.2s;
        }
        
        .handle-ball {
            width: 35px; height: 35px; background: radial-gradient(circle at 10px 10px, #ff4c4c, #8b0000);
            border: 2px solid #555; border-radius: 50%;
            position: absolute; left: 0px; top: 135px; z-index: -1;
            box-shadow: 0 4px 8px rgba(0,0,0,0.5); transition: 0.2s;
        }

        /* Animacja ciągnięcia za wajchę po kliknięciu zakręć */
        .pulling .handle-rod, .pulling .handle-ball { transform: rotateX(70deg); }
        
        .btn-play {
            width: 100%; padding: 15px; 
            background: linear-gradient(180deg, #d4af37 0%, #b8962e 100%);
            border: none; border-radius: 8px; font-weight: 900; font-size: 16px;
            color: black; cursor: pointer; text-transform: uppercase; transition: 0.2s;
        }
        .btn-play:hover { filter: brightness(1.1); transform: translateY(-2px); }
        .btn-play:disabled { background: #444; color: #888; cursor: not-allowed; transform: none;}
        
        .btn-back { display: block; margin-top: 15px; color: #8b0000; text-decoration: none;}
        .btn-back:hover { color: #ff4c4c; }
        
    </style>
   
</head>
<body id="slotMachine">

<div class="machine-casing" id="machineCasing">
    <h1>Jednoręki Bandyta</h1>
    
    <div class="handle-mechanism">
        <div class="handle-rod"></div>
        <div class="handle-ball"></div>
    </div>

    <div class="balance">Żetony: <span><%= currentBalance %></span></div>

    <div id="resultMsg" class="msg-box">
        <%-- Błędy, np. brak kasy --%>
        <% if (request.getAttribute("error") != null) { %>
            <span style="color: red;"><%= request.getAttribute("error") %></span>
        <% } %>
    </div>

    <div class="reels-window">
        <div id="reel1" class="reel"></div>
        <div id="reel2" class="reel"></div>
        <div id="reel3" class="reel"></div>
    </div>

    <form action="slots" method="post" id="slotsForm" style="margin:0;">
        <button type="submit" id="spinBtn" class="btn-play">Pociągnij za wajchę (10 żetonów)</button>
    </form>
    <a href="/" class="btn-back">Anuluj i wróć do kasyna</a>
</div>

<script>
    const hasSpun = <%= hasSpun %>;
    
    // Zmienne z Javy (1 do 7)
    const val1 = parseInt("<%= slot1 %>");
    const val2 = parseInt("<%= slot2 %>");
    const val3 = parseInt("<%= slot3 %>");
    
    const resultType = "<%= request.getAttribute("resultType") %>";
    const winAmount = "<%= request.getAttribute("winAmount") %>";

    // Wyliczanie przesunięcia tła sprite'a.
    // Sprite ma 7 symboli, każdy ma wysokość 120px.
    function getBackgroundPosition(value) {
        // value jest od 1 do 7. Odejmujemy 1, żeby indeksować od 0.
        return "-" + ((value - 1) * 120) + "px";
    }

    if(hasSpun) {
        const btn = document.getElementById('spinBtn');
        const msgBox = document.getElementById('resultMsg');
        const casing = document.getElementById('machineCasing');
        const r1 = document.getElementById('reel1');
        const r2 = document.getElementById('reel2');
        const r3 = document.getElementById('reel3');

        btn.disabled = true;

        // Sekwencja "pociągnięcia za wajchę":
        
        // 1. Uruchom animację pociągnięcia wajchy
        casing.classList.add('pulling');
        
        // 2. Po 0.2s (czas trwania transition) zwolnij wajchę i zacznij kręcić bębnami
        setTimeout(() => {
            casing.classList.remove('pulling');
            
            // Uruchamiamy "kręcenie" tłem
            r1.classList.add('spinning');
            r2.classList.add('spinning');
            r3.classList.add('spinning');

            // 3. Zatrzymujemy bębny po kolei od lewej do prawej
            
            // Bęben 1 stop (po 1s od startu kręcenia)
            setTimeout(() => {
                r1.classList.remove('spinning');
                r1.style.backgroundPositionY = getBackgroundPosition(val1);
            }, 1000);

            // Bęben 2 stop
            setTimeout(() => {
                r2.classList.remove('spinning');
                r2.style.backgroundPositionY = getBackgroundPosition(val2);
            }, 1800);

            // Bęben 3 stop
            setTimeout(() => {
                r3.classList.remove('spinning');
                r3.style.backgroundPositionY = getBackgroundPosition(val3);
                
                // Pokaż komunikat o wygranej po zatrzymaniu ostatniego bębna
                if(resultType === "jackpot") {
                    msgBox.className = "msg-box jackpot";
                    msgBox.innerText = "🏆 JACKPOT! Wygrywasz " + winAmount + "! 🏆";
                } else if(resultType === "win") {
                    msgBox.className = "msg-box win";
                    msgBox.innerText = "✅ Trafienie! Wygrywasz " + winAmount + "!";
                } else if(resultType === "lose") {
                    msgBox.className = "msg-box lose";
                    msgBox.innerText = "❌ Brak wygranej. Spróbuj ponownie!";
                }

                btn.disabled = false;
            }, 2600);
            
        }, 200); // 0.2s to czas trwania pociągnięcia wajchy
        
    } else {
        // Przed pierwszym kliknięciem wyświetla siódemkę (7️⃣ na obrazku to ostatni symbol)
        document.getElementById('reel1').style.backgroundPositionY = "-720px";
        document.getElementById('reel2').style.backgroundPositionY = "-720px";
        document.getElementById('reel3').style.backgroundPositionY = "-720px";
    }
</script>

</body>
</html>