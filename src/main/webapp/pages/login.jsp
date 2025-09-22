<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background: white;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0px 4px 15px rgba(0,0,0,0.15);
            text-align: center;
            width: 300px;
        }
        .login-container h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .login-container label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
            text-align: left;
        }
        .login-container input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        .login-container button {
            margin-top: 20px;
            width: 100%;
            padding: 10px;
            background: #007BFF;
            border: none;
            border-radius: 6px;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }
        .login-container button:hover {
            background: #0056b3;
        }
        .error {
            color: red;
            margin-top: 10px;
        }
        .links {
            margin-top: 15px;
            font-size: 14px;
        }
        .links a {
            color: #007BFF;
            text-decoration: none;
        }
        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>Login</h2>

    <form action="<c:url value='/login'/>" method="post">
        <label for="username">Username</label>
        <input type="text" name="username" id="username" required>

        <label for="password">Password</label>
        <input type="password" name="password" id="password" required>

        <button type="submit">Login</button>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </form>

    <div class="links">
        <p><a href="<c:url value='/register'/>">Register new user</a></p>
        <p><a href="<c:url value='/'/>">Back to Home</a></p>
    </div>
</div>

</body>
</html>
