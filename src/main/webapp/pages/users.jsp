<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f9;
            margin: 0;
            padding: 0;
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
            width: 900px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
        }
        th {
            background: #f2f2f2;
        }
        tr:nth-child(even) {
            background: #fafafa;
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
        .actions form, .actions a {
            margin: 0 5px;
        }
    </style>
</head>
<%@ include file="/WEB-INF/jsp/fragments/logout.jspf" %>




<div class="container">
    <div class="card">
        <h2>Registered Users</h2>
        <c:if test="${not empty errorMessage}">
            <div style="color:red; font-weight:bold; margin:10px 0; text-align:center;">
                    ${errorMessage}
            </div>
        </c:if>
        <table>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.id}</td>
                    <td title="${u.username}" style="max-width: 150px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                            ${u.username}
                    </td>
                    <td title="${u.email}" style="max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                            ${u.email}
                    </td>
                    <td>${u.role.displayName}</td>
                    <td class="actions">
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
                                    <option value="ROLE_USER" ${u.role.displayName == 'ROLE_USER' ? 'selected' : ''}>User</option>
                                    <option value="ROLE_ADMIN" ${u.role.displayName == 'ROLE_ADMIN' ? 'selected' : ''}>Admin</option>
                                    <option value="ROLE_ADMINDEVELOPER" ${u.role.displayName == 'ROLE_ADMINDEVELOPER' ? 'selected' : ''}>AdminDev</option>
                                </select>
                                <button type="submit">Change</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>


        <c:set var="start" value="${currentPage - 2}" />
        <c:set var="end" value="${currentPage + 2}" />
        <c:if test="${start < 1}">
            <c:set var="start" value="1" />
        </c:if>
        <c:if test="${end > totalPages}">
            <c:set var="end" value="${totalPages}" />
        </c:if>

        <c:forEach begin="${start}" end="${end}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span style="font-weight:bold; margin:0 5px;">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/users?page=${i}" style="margin:0 5px;">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <div style="margin-top:20px;">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/users?page=${currentPage - 1}">Previous</a>
            </c:if>

            <span style="margin:0 15px;">
        Page ${currentPage} of ${totalPages}
    </span>

            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/users?page=${currentPage + 1}">Next</a>
            </c:if>
        </div>


        <div class="links">
            <a href="<c:url value='/pages/home.jsp'/>" class="btn">Home</a>
        </div>
    </div>
</div>

</body>
</html>
