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

    <!-- Jeśli user NIE jest zalogowany -->
    <c:if test="${empty sessionScope.user}">
        <div class="links">
        <a href="<c:url value='/login'/>" class="btn">Login</a> |
        <a href="<c:url value='/register'/>" class="btn">Register new user</a>
        </div>
    </c:if>

    <!-- Jeśli user JEST zalogowany -->
    <c:if test="${not empty sessionScope.user}">
        <%@ include file="/WEB-INF/jsp/fragments/logout.jspf" %>
        <div class="links">
            <a href="<c:url value='/pages/home.jsp'/>" class="btn">🏠 Home 🏠</a> |
            <a href="<c:url value='/users'/>" class="btn">👥 Show all users 👥</a>
        </div>
    </c:if>

    <a href="<c:url value='/hello'/>" class="btn">Go to HelloServlet</a>
</div>
</body>
</html>