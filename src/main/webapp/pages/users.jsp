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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<p><a href="<c:url value='/'/>">Back to Home</a></p>
<a href="<c:url value='/register'/>">Register new user</a>
<p><a href="<c:url value='/login'/>">Login</a></p>
<h2>Registered Users</h2>
<table border="1">
    <tr><th> ID </th><th> Username </th><th> Email </th><th> Role </th></tr>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        if (users != null) {
            for (User u : users) {
    %>
    <tr>
        <td><%=u.getId()%></td>
        <td><%=u.getUsername()%></td>
        <td><%=u.getEmail()%></td>
        <td><%=u.getRole()%></td>
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
                        <option value="ROLE_ADMINDEVELOPER">Admin</option>
                    </select>
                    <button type="submit">Change</button>
                </form>
            </c:if>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
