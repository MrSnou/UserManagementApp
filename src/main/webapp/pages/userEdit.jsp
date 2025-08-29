<%@ page import="com.example.usermanagement.model.User" %><%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 28.08.2025
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<p><a href="<%= request.getContextPath() %>/">Back to Home</a></p>
<p><a href="<%= request.getContextPath() %>/users">Go to Users list</a></p>

<h2>Edit user</h2>
<form action="<%= request.getContextPath() %>/userEdit" method="post">
    <input type="hidden" name="id" value="<%= ((User)request.getAttribute("user")).getId() %>">
    <input type="text" name="username" value="<%= ((User)request.getAttribute("user")).getUserName() %>" placeholder="Username">
    <input type="password" name="password" value="<%= ((User)request.getAttribute("user")).getPassword() %>" placeholder="Password">
    <input type="email" name="email" value="<%= ((User)request.getAttribute("user")).getEmail() %>" placeholder="Email Address">
    <button type="submit">Finish</button>
</form>
</html>
