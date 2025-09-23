<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management App</title>
    <jsp:include page="WEB-INF/jsp/fragments/header.jspf"/>
</head>
<body>
<div class="container">
    <h1>Welcome to UserManagementApp!</h1>

    <a href="<c:url value='/login'/>" class="btn">Login</a>
    <a href="<c:url value='/register'/>" class="btn">Register new user</a>
    <a href="<c:url value='/hello'/>" class="btn">Go to HelloServlet</a>
</div>
</body>
</html>
