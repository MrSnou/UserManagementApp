<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 21.09.2025
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>System Logs</title>
</head>
<body>
<p><a href="<c:url value='/'/>">Back to Home</a></p>
<p><a href="<c:url value='/users'/>">Show all users</a></p>
<form action="<%=request.getContextPath()%>/logout" method="post">
    <button type="submit">Logout</button>
</form>
<h2>System Logs</h2>
<table border="1">
    <tr>
        <th>Time</th><th>Author</th><th>Action</th><th>Target</th>
    </tr>
    <c:forEach var="log" items="${logs}">
        <tr>
            <td>${log.time}</td>
            <td>${log.author}</td>
            <td>${log.action}</td>
            <td>${log.target}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
