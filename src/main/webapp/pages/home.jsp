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
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center; /* wyśrodkowanie treści */
            margin: 0;
            padding: 0;
        }
        .top-bar {
            position: relative;
            background: #f2f2f2;
            padding: 10px;
            text-align: left;
        }
        .logout-btn {
            position: absolute;
            top: 10px;
            right: 20px;
        }
        .main-content {
            margin-top: 50px;
        }
        .nav-links {
            margin: 20px 0;
        }
        .nav-links a {
            margin: 0 10px;
            text-decoration: none;
            font-weight: bold;
            color: #333;
        }
        .nav-links a:hover {
            color: #007BFF;
        }
    </style>
</head>
<body>

<div class="top-bar">
    <form class="logout-btn" action="<%=request.getContextPath()%>/logout" method="post">
        <button type="submit">Logout</button>
    </form>
</div>

<div class="main-content">
    <h1>Welcome, <%= user.getUsername() %>!</h1>
    <p>Email: <%= user.getEmail() %></p>

    <div class="nav-links">
        <a href="<c:url value='/users'/>">Show all users</a>

        <c:if test="${user.role.name eq 'ROLE_ADMINDEVELOPER'}">
            <a href="<c:url value='/logs'/>">View Logs</a>
        </c:if>
    </div>
</div>

</body>
</html>
