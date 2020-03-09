<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("user");
%>
<html lang='fr'>
<head>
    <meta charset='utf-8'>
    <title>Ajouter</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/add.css'>
</head>
<body>

    <div id='header'>
        <h1><%= u.isBibliothecaire() ? "Espace bibliothécaire" : "Espace abonnés" %></h1>
        <h1><%= u.name() %></h1>
    </div>

    <div id='page'>
        <h2>Ajouter un nouveau document</h2>
        <form method='post' action='${pageContext.request.contextPath}/add' id="form">
            <label for='title' id='lbl-login'>Titre</label>
            <input type='text' id='title' name='title' placeholder='Les Misérables'>
            <label for='author' id='lbl-pwd'>Auteur</label>
            <input type='text' id='author' name='author' placeholder='Victor Hugo'>
            <div id="radios">
                <label for='dvd'>DVD</label>
                <input type="radio" name="type" value="dvd" id="dvd">
                <label for='book'>Livre</label>
                <input type="radio" name="type" value="book" id='book'>
            </div>
            <input type='submit' value='Ajouter'>
        </form>
    </div>
</body>
<script src='https://code.jquery.com/jquery-3.4.1.min.js'
        integrity='sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo='
        crossorigin='anonymous'></script>
<script src='${pageContext.request.contextPath}/js/add.js'></script>
</html>