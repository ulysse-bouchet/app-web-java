<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="./css/style.css">
    <link rel="stylesheet" href="./css/login.css">
</head>
<body>
    <h1>BiblioBrette</h1>
    <form method="get" action="./login">
        <label for="login" id="lbl-login">Login</label>
        <input type="text" id="login" name="login" placeholder="jfbrette">

        <label for="pwd" id="lbl-pwd">Password</label>
        <input type="password" id="pwd" name="pwd" placeholder="⏺⏺⏺⏺⏺⏺⏺⏺">

        <input type="submit" value="Login">
    </form>
</body>
<script
        src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="js/login.js"></script>
</html>