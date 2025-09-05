<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<p><a href="<%= request.getContextPath() %>/">Back to Home</a></p>
<p><a href="<%= request.getContextPath() %>/users">Go to Users list</a></p>
<p><a href="<%= request.getContextPath() %>/pages/register.jsp">Register new user</a></p>

<h2>Login</h2>

<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

<form action="<%= request.getContextPath() %>/login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <br><br>

    <button type="submit">Login</button>
</form>

</body>
</html>
