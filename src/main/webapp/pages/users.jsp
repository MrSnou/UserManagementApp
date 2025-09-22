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
<body>
<div class="logout">
    <form action="<%=request.getContextPath()%>/logout" method="post">
        <button type="submit">Logout</button>
    </form>
</div>

<div class="container">
    <div class="card">
        <h2>Registered Users</h2>
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
                    <td>${u.username}</td>
                    <td>${u.email}</td>
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

        <div class="links">
            <a href="<c:url value='/'/>">Home</a> |
            <a href="<c:url value='/register'/>">Register</a> |
            <a href="<c:url value='/login'/>">Login</a>
        </div>
    </div>
</div>
</body>
</html>
