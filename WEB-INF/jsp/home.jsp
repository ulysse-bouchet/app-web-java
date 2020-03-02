<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("user");
%>
<html lang='fr'>
    <head>
        <meta charset='utf-8'>
        <title>Mon espace</title>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
    </head>
<body>

    <div id='header'>
        <h1><%= u.isBibliothecaire() ? "Espace bibliothécaire" : "Espace abonnés" %></h1>
        <h1><%= u.name() %></h1>
    </div>

    <div id='page'>
        <h2>Que souhaitez-vous faire ?</h2>
        <% if (u.isBibliothecaire()) { %>
                <a href='${pageContext.request.contextPath}/add'>Ajouter un document</a>
        <% } else { %>
                <a href='${pageContext.request.contextPath}/borrow'>Emprunter un document</a>
                <a href='${pageContext.request.contextPath}/return'>Retourner un document</a>
                <a href='${pageContext.request.contextPath}/book'>Réserver un document</a>
        <% } %>
    </div>

</body>
</html>
