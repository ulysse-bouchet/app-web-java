<%@ page import="mediatheque.items.Utilisateur" %>
<%@ page import="mediatheque.items.Document" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("user");
    List<Document> docs = (List<Document>) request.getAttribute("docs");
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
    <h2>Sélectionnez le document à emprunter : </h2>
        <c:forEach items="${docs}" var="doc">
            <c:if test="${doc.data()[3] == null}">
                <a class="list" href='${pageContext.request.contextPath}/borrow?id=${doc.data()[0]}'>
                    ${doc.toString()}
                </a>
            </c:if>
        </c:forEach>
</div>

</body>
</html>
