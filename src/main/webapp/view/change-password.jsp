<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Zmień hasło - Kasyno</title>
    <style>
        body {
            background-color: #0f0f0f;
            color: #ffffff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .form-container {
            background-color: #1a1a1a;
            border: 2px solid #ffcc00;
            border-radius: 15px;
            padding: 35px;
            width: 380px;
            box-shadow: 0 0 20px rgba(255, 204, 0, 0.2);
            text-align: center;
        }
        h2 {
            color: #ffcc00;
            margin-bottom: 25px;
            text-transform: uppercase;
            letter-spacing: 2px;
        }
        .input-group {
            margin-bottom: 20px;
            text-align: left;
        }
        label {
            display: block;
            color: #888888;
            font-size: 14px;
            margin-bottom: 8px;
        }
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border-radius: 5px;
            border: 1px solid #444;
            background-color: #262626;
            color: white;
            box-sizing: border-box;
            font-size: 14px;
            transition: 0.3s;
        }
        input[type="password"]:focus {
            border-color: #ffcc00;
            outline: none;
            box-shadow: 0 0 8px rgba(255, 204, 0, 0.3);
        }
        .error-msg {
            color: #ff4c4c;
            font-size: 14px;
            margin-bottom: 20px;
            font-weight: bold;
            background-color: rgba(255, 76, 76, 0.1);
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ff4c4c;
        }
        .btn {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            transition: 0.3s;
            font-size: 14px;
            display: block;
            box-sizing: border-box;
        }
        .btn-gold {
            background-color: #d4af37;
            color: black;
            margin-top: 10px;
        }
        .btn-gold:hover {
            background-color: #f1c40f;
        }
        .btn-back {
            background-color: #8b0000;
            color: white;
            margin-top: 15px;
        }
        .btn-back:hover {
            background-color: #b30000;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Zmiana hasła</h2>
    
    <% if (request.getAttribute("error") != null) { %>
        <div class="error-msg"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="change-password" method="post">
        <div class="input-group">
            <label>Aktualne hasło:</label>
            <input type="password" name="oldPassword" required>
        </div>
        <div class="input-group">
            <label>Nowe hasło:</label>
            <input type="password" name="newPassword" required>
        </div>
        <div class="input-group">
            <label>Powtórz nowe hasło:</label>
            <input type="password" name="confirmPassword" required>
        </div>
        <button type="submit" class="btn btn-gold">Zatwierdź zmianę</button>
        <a href="profil" class="btn btn-back">Anuluj i wróć do profilu</a>
    </form>
</div>

</body>
</html>