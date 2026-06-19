<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil Gracza - Kasyno</title>
    <style>
        body {
    background-color: #0c0c0c; color: #ffffff;
    font-family: 'Times New Roman', Times, serif;
    display: flex; justify-content: center; align-items: center;
    min-height: 100vh; margin: 0;
}
        .profile-container {
            background-color: #1a1a1a;
            border: 2px solid #ffcc00;
            border-radius: 15px;
            padding: 35px;
            width: 420px;
            box-shadow: 0 0 20px rgba(255, 204, 0, 0.2);
            text-align: center;
        }
        h1 { 
    color: #FFD700; 
    font-size: 52px;
    margin-top: 0;
    margin-bottom: 25px;
    text-shadow: 0 0 12px rgba(255,215,0,0.45);
    font-weight: normal; 
}
        .info-group {
            margin: 15px 0;
            padding: 12px;
            background-color: #262626;
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-left: 4px solid #444;
        }
        .label {
            color: #888888;
            font-size: 14px;
        }
        .value {
            font-weight: bold;
            font-size: 18px;
        }
        .status {
            color: #00e6e6;
            text-transform: uppercase;
            font-size: 16px;
        }
        .balance {
            color: #00ff66;
            font-size: 22px;
        }
        .buttons-container {
            margin-top: 30px;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }
        .action-row {
            display: flex;
            justify-content: space-between;
            gap: 10px;
        }
        .btn {
            flex: 1;
            padding: 12px;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            color: white;
            transition: 0.3s;
            font-size: 14px;
            text-align: center;
        }
        .btn-gold { background-color: #d4af37; color: black; }
        .btn-gold:hover { background-color: #f1c40f; }
        
        .btn-blue { background-color: #2980b9; }
        .btn-blue:hover { background-color: #3498db; }

        .btn-back {
            background-color: #8b0000;
            display: block;
            margin-top: 10px;
        }
        .btn-back:hover { background-color: #b30000; }
    </style>
</head>
<body>

<div class="profile-container">
    <h1>Profil Gracza</h1>
    
    <div class="info-group">
        <span class="label">Nazwa logowania:</span>
        <span class="value">${username}</span>
    </div>
    
    <div class="info-group">
        <span class="label">Status konta:</span>
        <span class="value status">⭐ ${accountStatus}</span>
    </div>
    
    <div class="info-group" style="border-left-color: #00ff66;">
        <span class="label">Stan konta:</span>
        <span class="value balance">${balance} żetonów</span>
    </div>

    <div class="buttons-container">
        <div class="action-row">
            <a href="change-password" class="btn btn-blue">Zmień hasło</a>
            <a href="buy-coins" class="btn btn-gold">Kup 500 żetonów</a>
        </div>
        <a href="/" class="btn btn-back">Powrót do kasyna</a>
    </div>
</div>

</body>
</html>