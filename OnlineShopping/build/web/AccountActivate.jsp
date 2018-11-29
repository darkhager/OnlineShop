<%-- 
    Document   : AccountActivate
    Created on : Nov 29, 2018, 2:35:45 PM
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
        <h1>Account Activate</h1>
        <form action="AccountActivate" method="post">
            <div>${message}</div>
            <div><input type="text" name="activatecode"></div>
            <div><input type="submit"></div>
        </form>
    </body>
</html>
