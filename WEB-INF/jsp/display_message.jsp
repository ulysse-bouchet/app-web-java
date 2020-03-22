<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    //Gets the message and the user from the request
    String msg = (String) request.getAttribute("msg");
    Utilisateur u = (Utilisateur) request.getAttribute("user");
%>
<html lang='fr'>
<head>
    <meta charset='utf-8'>
    <title>BiblioBrette</title>

    <!-- CSS Stylesheets -->
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
</head>
<body>

<!-- Page header -->
<div id='header'>
    <h1>Espace abonnés</h1>
    <h1><%= u.name() %></h1>
</div>

<!-- Page content -->
<div id='page'>
    <!-- The message -->
    <h2><%= msg %></h2>

    <!-- A link to the personal space of the user -->
    <a href="${pageContext.request.contextPath}/home">Retourner à mon espace</a>
</div>

</body>
</html>
