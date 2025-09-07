<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<p><a href="<c:url value='/'/>">Back to Home</a></p>
<a href="<c:url value='/register'/>">Register new user</a>
<p><a href="<c:url value='/users'/>">Show all users</a></p>

<h2>Login</h2>

<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

<form action="<c:url value='/login'/>" method="post">
    <label for="username">Username:</label>
    <input type="text" name="username" id="username" required><br>

    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required><br>

    <button type="submit">Login</button>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
</form>

</body>
</html>
