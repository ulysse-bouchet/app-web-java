<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String msg = (String) request.getAttribute("msg");
    Utilisateur u = (Utilisateur) request.getAttribute("user");
%>
<html lang='fr'>
<head>
    <meta charset='utf-8'>
    <title>BiblioBrette</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
</head>
<body>

<div id='header'>
    <h1>Espace abonnés</h1>
    <h1><%= u.name() %></h1>
</div>

<div id='page'>
    <h2><%= msg %></h2>
    <a href="${pageContext.request.contextPath}/home">Retourner à mon espace</a>
</div>

</body>
</html>
