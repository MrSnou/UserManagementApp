<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
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
            height: 100vh;
        }
        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            width: 350px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
        }
        input {
            width: 90%;
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        button {
            background: #4CAF50;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 6px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }
        button:hover {
            background: #45a049;
        }
        .error {
            color: red;
            margin-top: 10px;
        }
        .links {
            margin-top: 15px;
        }
        .links a {
            margin: 0 10px;
            text-decoration: none;
            color: #007BFF;
        }
        .links a:hover {
            text-decoration: underline;
        }
        .subform {
            margin-top: 25px;
            padding-top: 15px;
            border-top: 1px solid #eee;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <h2>Register</h2>
        <form action="<%= request.getContextPath() %>/register" method="post">
            <input type="text" name="username" placeholder="Username" value="${username}" required>
            <c:if test="${not empty usernameError}">
                <div class="error">${usernameError}</div>
            </c:if>

            <input type="password" name="password" placeholder="Password" required>
            <c:if test="${not empty passwordError}">
                <div class="error">${passwordError}</div>
            </c:if>

            <input type="email" name="email" placeholder="Email Address" value="${email}" required>
            <c:if test="${not empty emailError}">
                <div class="error">${emailError}</div>
            </c:if>

            <button type="submit">Register</button>
        </form>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <div class="subform">
            <h3>Or add random TEST users</h3>
            <form action="<%= request.getContextPath() %>/addRandomUsers" method="post">
                <input type="number" name="count" value="3" min="1" />
                <button type="submit">Add random users</button>
            </form>
        </div>

        <div class="links">
            <a href="<c:url value='/'/>">Home</a> |
            <a href="<c:url value='/login'/>">Login</a>
        </div>
    </div>
</div>
</body>
</html>
