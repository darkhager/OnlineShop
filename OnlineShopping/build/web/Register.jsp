<%-- 
    Document   : register
    Created on : Nov 1, 2018, 3:35:01 PM
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
        <h1>Register</h1>
        <form method="post" action="register">
            <div>FirstName : <input type="text" name="firstName" required></div>
            <div>LastName : <input type="text" name="lastName" required></div>
            <div>UserName : <input type="text" name="userName" required></div>
            <div>Password : <input type="text" name="password" required></div>
            <div>RePassword : <input type="text" name="repassword" required></div>
            <div>Email : <input type="email" name="email" required></div>
            <div>Date Of Birth : <input type="text" name="repassword" required></div>
            <div>Address : <input type="text" name="address" required></div>
            <div>Country : <input type="text" name="country" required></div>
            <div>Address : <input type="number" name="postcode" required></div>
            <input type="submit">
        </form>
    </body>
</html>
