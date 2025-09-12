<%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 23.08.2025
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.usermanagement.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<p><a href="<c:url value='/'/>">Back to Home</a></p>
<a href="<c:url value='/register'/>">Register new user</a>
<p><a href="<c:url value='/login'/>">Login</a></p>
<h2>Registered Users</h2>
<table border="1">
    <tr><th> ID </th><th> Username </th><th> Email </th><th> Role </th></tr>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        if (users != null) {
            for (User u : users) {
    %>
    <tr>
        <td><%=u.getId()%></td>
        <td><%=u.getUsername()%></td>
        <td><%=u.getEmail()%></td>
        <td><%=u.getRole()%></td>
        <td>
            <a href="<%= request.getContextPath() %>/delete?id=<%=u.getId()%>">Delete</a>
        </td>
        <td>
            <a href="<%= request.getContextPath() %>/userEdit?id=<%=u.getId()%>">Edit</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
