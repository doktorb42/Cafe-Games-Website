<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kasyno</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Wprowadź nazwę użytkownika</h1>
        </div>
        <div class="casino-icon">♠ ♥ ♦ ♣</div>
        <form action="login" method="post" accept-charset="UTF-8">
            <div class="inputs">
                <input type="text" placeholder="Nazwa użytkownika" name="username">
                <input type="password" placeholder="Hasło" name="password">
            </div>
            <div class="login-link">
                <span>Nie masz konta? </span><a href="register">Zarejestruj się</a>
            </div>
            <div class="button">
                <input type="submit" value="Zaloguj się">
            </div>
            <div class="error-message">
                <% String errorMessage = (String) request.getAttribute("errorMessage");
                   if (errorMessage != null) { %>
                    <p><%= errorMessage %></p>
                <% } %>

            </div>
        </form>
    </div>
</body>
</html>