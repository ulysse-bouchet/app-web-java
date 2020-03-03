<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("user");
%>
<html lang='fr'>
<head>
    <meta charset='utf-8'>
    <title>Emprunter</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
</head>
<body>

<div id='header'>
    <h1>Espace abonnés</h1>
    <h1><%= u.name() %></h1>
</div>

<div id='page'>
    <h2>Cette fonctionnalité n'est pas encore disponible.</h2>
</div>

</body>
</html>
