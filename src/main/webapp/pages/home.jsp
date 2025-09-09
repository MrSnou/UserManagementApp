<%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 06.09.2025
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.usermanagement.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Home</title>
</head>
<body>
<p><a href="<c:url value='/'/>">Back to Home</a></p>
<p><a href="<c:url value='/users'/>">Show all users</a></p>

<h1>Welcome, <%= user.getUsername() %>!</h1>

<p>Email: <%= user.getEmail() %></p>

<form action="<%=request.getContextPath()%>/logout" method="post">
    <button type="submit">Logout</button>
</form>
</body>
</html>