<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page import="mediatheque.items.Document" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    //Gets the user and documents from the forwarded request
    Utilisateur u = (Utilisateur) request.getAttribute("user");
    List<Document> docs = (List<Document>) request.getAttribute("docs");
%>
<html lang='fr'>
<head>
    <meta charset='utf-8'>
    <title>Emprunter</title>

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
    <h2>Sélectionnez le document à emprunter : </h2>
    <!-- For each document -->
        <c:forEach items="${docs}" var="doc">
            <!-- Displays it as a link to borrow it -->
            <a class="list" href='${pageContext.request.contextPath}/borrow?id=${doc.data()[0]}'>
                ${doc.toString()}
            </a>
        </c:forEach>
</div>

</body>
</html>
