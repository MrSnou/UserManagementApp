<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management App</title>
</head>
<body>
<h1>Welcome to UserManagementApp!</h1>

<p><a href="<c:url value='/login'/>">Login</a></p>
<a href="<c:url value='/register'/>">Register new user</a>
<p><a href="<c:url value='/hello'/>">Go to HelloServlet</a></p>
<p><a href="<c:url value='/users'/>">Show all users</a></p>

</body>
</html>
