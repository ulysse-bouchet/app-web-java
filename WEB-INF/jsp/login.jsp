<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang='fr'>
    <head>
        <meta charset='utf-8'>
        <title>Login</title>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/css/login.css'>
    </head>
    <body>
        <h1>BiblioBrette</h1>
        <form method='post' action='./login'>
            <label for='login' id='lbl-login'>Identifiant</label>
            <input type='text' id='login' name='login' placeholder='jfbrette'>
            <label for='pwd' id='lbl-pwd'>Mot de passe</label>
            <input type='password' id='pwd' name='pwd' placeholder='⏺⏺⏺⏺⏺⏺⏺⏺'>
            <input type='submit' value='Connexion'>
        </form>
    </body>
    <script src='https://code.jquery.com/jquery-3.4.1.min.js'
            integrity='sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo='
            crossorigin='anonymous'></script>
    <script src='${pageContext.request.contextPath}/js/login.js'></script>
</html>