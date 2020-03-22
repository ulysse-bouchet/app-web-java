<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    //Gets the user from the forwarded request
    Utilisateur u = (Utilisateur) request.getAttribute("user");
%>
<html lang='fr'>
<head>
    <meta charset='utf-8'>
    <title>Ajouter</title>

    <!-- CSS Stylesheets -->
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/add.css'>
</head>
<body>

    <!-- Page header -->
    <div id='header'>
        <h1>Espace bibliothécaire</h1>
        <h1><%= u.name() %></h1>
    </div>

    <!-- Page content -->
    <div id='page'>
        <h2>Ajouter un nouveau document</h2>
        <!-- Add a new document form -->
        <form method='post' action='${pageContext.request.contextPath}/add' id="form">
            <label for='title' id='lbl-title'>Titre</label>
            <input type='text' id='title' name='title' placeholder='Les Misérables'>
            <label for='author' id='lbl-author'>Auteur</label>
            <input type='text' id='author' name='author' placeholder='Victor Hugo'>
            <div id="radios">
                <label for='book'>Livre</label>
                <input type="radio" name="type" value="book" id='book' checked>
                <label for='dvd'>DVD</label>
                <input type="radio" name="type" value="dvd" id="dvd">
            </div>
            <input type='submit' value='Ajouter'>
        </form>
    </div>
</body>

<!-- JQuery -->
<script src='https://code.jquery.com/jquery-3.4.1.min.js'
        integrity='sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo='
        crossorigin='anonymous'></script>

<!-- JS Script -->
<script src='${pageContext.request.contextPath}/js/add.js'></script>
</html>