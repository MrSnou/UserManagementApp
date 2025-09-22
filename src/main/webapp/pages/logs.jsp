<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>System Logs</title>
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
            width: 800px;
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
        <h2>System Logs</h2>
        <table>
            <tr>
                <th>Time</th>
                <th>Author</th>
                <th>Action</th>
                <th>Target</th>
            </tr>
            <c:forEach var="log" items="${logs}">
                <tr>
                    <td>${log.formattedTime}</td>
                    <td>${log.author}</td>
                    <td>${log.action}</td>
                    <td>${log.target}</td>
                </tr>
            </c:forEach>
        </table>

        <div class="links">
            <a href="<c:url value='/'/>">Home</a> |
            <a href="<c:url value='/users'/>">All users</a>
        </div>
    </div>
</div>
</body>
</html>
