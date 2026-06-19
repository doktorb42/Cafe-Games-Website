<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String username = (String) request.getAttribute("username");
    Integer balance = (Integer) request.getAttribute("balance");

    if (username == null) username = "Brak danych";
    if (balance == null) balance = 0;
%>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Profil Gracza - Kasyno</title>
    <link rel="stylesheet" href="css/profil.css">
</head>

<body>
<div class="container">

    <div class="back-button">
        <a href="/">&lt;</a>
    </div>

    <div class="header">
        <h1>Profil Gracza</h1>
    </div>

    <div class="content">
        <div class="panel-card">

            <p class="page-subtitle">Informacje o Twoim koncie w kasynie</p>

            <div class="profile-grid">

                <div class="info-box">
                    <span class="info-label">Nazwa logowania</span>
                    <span class="info-value"><%= username %></span>
                </div>

                <div class="info-box">
                    <span class="info-label">Stan konta</span>
                    <span class="info-value gold"><%= balance %> żetonów</span>
                </div>

                <div class="info-box">
                    <span class="info-label">Status konta</span>
                    <span class="info-value">Aktywne</span>
                </div>

                <div class="info-box">
                    <span class="info-label">Typ konta</span>
                    <span class="info-value">Gracz</span>
                </div>

            </div>

            <div class="profile-actions">
                <a href="change-password" class="btn">Zmień hasło</a>
                <a href="buy-coins" class="btn btn-gold">Kup 500 żetonów</a>
                <a href="/" class="btn btn-red">Powrót</a>
            </div>

        </div>
    </div>

</div>
</body>
</html>