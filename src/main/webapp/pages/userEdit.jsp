<%@ page import="com.example.usermanagement.model.User" %><%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 28.08.2025
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<p><a href="<c:url value='/'/>">Back to Home</a></p>
<p><a href="<c:url value='/users'/>">Show all users</a></p>
<p><a href="<c:url value='/login'/>">Login</a></p>
<a href="<c:url value='/register'/>">Register new user</a>

<h2>Edit user</h2>
<form action="<%= request.getContextPath() %>/userEdit" method="post">
    <input type="hidden" name="id" value="<%= ((User)request.getAttribute("user")).getId() %>">
    <input type="text" name="username" value="<%= ((User)request.getAttribute("user")).getUserName() %>" placeholder="Username">
    <input type="password" name="password" value="<%= ((User)request.getAttribute("user")).getPassword() %>" placeholder="Password">
    <input type="email" name="email" value="<%= ((User)request.getAttribute("user")).getEmail() %>" placeholder="Email Address">
    <button type="submit">Finish</button>
</form>
</html>
