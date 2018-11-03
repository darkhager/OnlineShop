<%-- 
    Document   : login
    Created on : Nov 1, 2018, 3:34:53 PM
    Author     : 60130
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="post" action="Login">
            <div style="color: red">${message}</div>
            <div>username:<input type="text" name="userName" required></div>
            <div>password:<input type="password" name="password" required></div>
            <input type="submit">
        </form>
    </body>
</html>
