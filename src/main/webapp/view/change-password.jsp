<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Zmiana hasła - Kasyno</title>
    <link rel="stylesheet" href="css/changepassword.css">
</head>

<body>

<div class="container password-container">

    <div class="back-button">
        <a href="profil">&lt;</a>
    </div>

    <div class="casino-icon">
        ♦ ♠ ♥ ♣
    </div>

    <div class="header">
        <h1>Zmiana hasła</h1>
    </div>

    <div class="content password-layout">

        <div class="panel-card password-card">

            <p class="page-subtitle">
                Wpisz aktualne hasło i ustaw nowe hasło do konta.
            </p>

            <% if (error != null) { %>
                <div class="alert-box alert-error">
                    <%= error %>
                </div>
            <% } %>

            <% if (success != null) { %>
                <div class="alert-box alert-success">
                    <%= success %>
                </div>
            <% } %>

            <form action="change-password" method="post" accept-charset="UTF-8">

                <div class="form-group">
                    <label for="oldPassword">Aktualne hasło</label>
                    <input type="password"
                           id="oldPassword"
                           name="oldPassword"
                           required>
                </div>

                <div class="form-group">
                    <label for="newPassword">Nowe hasło</label>
                    <input type="password"
                           id="newPassword"
                           name="newPassword"
                           minlength="6"
                           required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Powtórz nowe hasło</label>
                    <input type="password"
                           id="confirmPassword"
                           name="confirmPassword"
                           minlength="6"
                           required>
                </div>

                <div class="password-actions">
                    <button type="submit" class="btn btn-gold full-btn">
                        Zatwierdź zmianę
                    </button>

                    <a href="profil" class="btn btn-red full-btn">
                        Anuluj i wróć do profilu
                    </a>
                </div>

            </form>

        </div>

    </div>

</div>

</body>
</html>