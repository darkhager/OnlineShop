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
        <title>Login Page</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <div class="container">
            <div class="col-lg-7 my-lg-5 mx-auto border border-dark rounded">
                <div class="mx-4">
                    <h1 class="products">Log in</h1>
                    <form class="mb-5" action="Login" method="POST">
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" class="form-control" name="username"
                                   placeholder="Enter your Username, E-mail or Phone Number">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" name="password"
                                   placeholder="Password">
                        </div>
                        <div class="form-group">
                            <a href="#">Forgot my password?</a>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-secondary">Submit</button>
                            <span class="ml-3" style="color: red">${message}</span>
                        </div>
                        <div>
                            <small class="float-right"><a href="Register.jsp">Don't have an account?</a></small>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
