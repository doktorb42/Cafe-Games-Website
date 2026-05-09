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
            <h1>Zaloguj się</h1>
        </div>
        <div class="casino-icon">♠ ♥ ♦ ♣</div>
        <form action="nickname" method="post" accept-charset="UTF-8">
            <div class="inputs">
                <input type="text" placeholder="Twoja własna nazwa" name="Nickname">
            </div>
            <div class="button">
                <input type="submit" value="Ustaw nazwę">
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