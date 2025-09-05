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
<html>
<head>
    <title>Users</title>
</head>
<body>
<p><a href="<%= request.getContextPath() %>/">Back to Home</a></p>
<p><a href="<%= request.getContextPath() %>/pages/register.jsp">Register new user</a></p>
<p><a href="<%= request.getContextPath() %>/pages/login.jsp">Login</a></p>
<h2>Registered Users</h2>
<table border="1">
    <tr><th> ID </th><th> Username </th><th> Email </th></tr>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        if (users != null) {
            for (User u : users) {
    %>
    <tr>
        <td><%=u.getId()%></td>
        <td><%=u.getUserName()%></td>
        <td><%=u.getEmail()%></td>
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
