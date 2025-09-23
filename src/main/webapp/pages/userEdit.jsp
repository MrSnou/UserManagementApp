<%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 28.08.2025
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.usermanagement.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User u = (User) request.getAttribute("user");
%>

<html>
<head>
    <title>Edit User</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        .logout {
            position: absolute;
            top: 15px;
            right: 20px;
        }
        .logout button {
            background: #ff4d4d;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 6px;
            cursor: pointer;
        }
        .logout button:hover {
            background: #e60000;
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            width: 500px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            width: 100%;
        }
        button {
            background: #007BFF;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background: #0056b3;
        }
        .links {
            margin-top: 20px;
        }
        .links a {
            margin: 0 10px;
            text-decoration: none;
            color: #007BFF;
        }
        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<!-- Logout fragment -->
<div class="logout">
    <form action="<%=request.getContextPath()%>/logout" method="post">
        <button type="submit">Logout</button>
    </form>
</div>

<div class="container">
    <div class="card">
        <h2>Edit User</h2>
        <form action="<%= request.getContextPath() %>/userEdit" method="post">
            <input type="hidden" name="id" value="<%= u.getId() %>">

            <input type="text" name="username" value="<%= u.getUsername() %>" placeholder="Username" required>

            <input type="password" name="password" value="<%= u.getPassword() %>" placeholder="Password" required>

            <input type="email" name="email" value="<%= u.getEmail() %>" placeholder="Email Address" required>

            <button type="submit">Save changes</button>
        </form>

        <div class="links">
            <a href="<c:url value='/pages/home.jsp'/>">Home</a> |
            <a href="<c:url value='/users'/>">Show all users</a>
        </div>
    </div>
</div>

</body>
</html>

