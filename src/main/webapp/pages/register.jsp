<%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 23.08.2025
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<p><a href="<%= request.getContextPath() %>/">Back to Home</a></p>
<p><a href="<%= request.getContextPath() %>/users">Go to Users list</a></p>

<h2>Register</h2>
<form action="<%= request.getContextPath() %>/register" method="post">
    <input type="text" name="username" placeholder="Username">
    <input type="password" name="password" placeholder="Password">
    <input type="email" name="email" placeholder="Email Address">
    <button type="submit">Register</button>
</form>

<% if (request.getAttribute("error") != null) { %>
<p style="color:red"><%= request.getAttribute("error") %></p>
<% } %>

<h2>Or add random TEST users</h2>
<form action="<%= request.getContextPath() %>/addRandomUsers" method="post">
    <input type="number" name="count" value="3" min="1" />
    <button type="submit">Dodaj losowych użytkowników</button>
</form>
</body>
</html>
