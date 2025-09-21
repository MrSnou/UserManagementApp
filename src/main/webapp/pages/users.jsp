<%--
  Created by IntelliJ IDEA.
  User: jakub
  Date: 23.08.2025
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<p><a href="<c:url value='/'/>">Back to Home</a></p>
<a href="<c:url value='/register'/>">Register new user</a>
<p><a href="<c:url value='/login'/>">Login</a></p>
<form action="<%=request.getContextPath()%>/logout" method="post">
    <button type="submit">Logout</button>
</form>
<h2>Registered Users</h2>
<table border="1">
    <tr><th> ID </th><th> Username </th><th> Email </th><th> Role </th><th> Actions </th></tr>

    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.role.displayName}</td>
            <td>
                <c:if test="${canEdit}">
                    <a href="${pageContext.request.contextPath}/userEdit?id=${u.id}">Edit</a>
                </c:if>

                <c:if test="${canDelete}">
                    <a href="${pageContext.request.contextPath}/delete?id=${u.id}">Delete</a>
                </c:if>

                <c:if test="${canChangeRole}">
                    <form action="${pageContext.request.contextPath}/changeRole" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${u.id}" />
                        <select name="role">
                            <option value="ROLE_USER">User</option>
                            <option value="ROLE_ADMIN">Admin</option>
                            <option value="ROLE_ADMINDEVELOPER">AdminDev</option>
                        </select>
                        <button type="submit">Change</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
