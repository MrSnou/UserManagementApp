<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 0;
            padding: 0;
        }
        .top-bar {
            position: relative;
            background: #f2f2f2;
            padding: 10px;
            text-align: left;
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

<%@ include file="/WEB-INF/jsp/fragments/logout.jspf" %>

<div class="main-content">
    <h1>Welcome, ${user.username}!</h1>
    <p>Email: ${user.email}</p>

    <div class="nav-links">
        <a href="<c:url value='/users'/>">Show all users</a>

        <c:if test="${user.role.name eq 'ROLE_ADMINDEVELOPER'}">
            <a href="<c:url value='/logs'/>">View Logs</a>
        </c:if>
    </div>

    <c:if test="${user.role.name eq 'ROLE_ADMINDEVELOPER'}">
        <div class="subform">
            <h3>Or add random TEST users</h3>
            <form action="<c:url value='/addRandomUsers'/>" method="post">
                <input type="number" name="count" value="1" min="1" />
                <button type="submit">Add random users</button>
            </form>
        </div>
    </c:if>
</div>

</body>
</html>
