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
        <title>Register Page</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
        
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>
        
        <div class="container">
            <div class="col-lg-7 my-lg-5 mx-auto border border-dark rounded">
                <div class="mx-4">
                    <h1 class="products">Register</h1>
                    <form class="mb-5" id="registerform" action="Register" method="POST">
                        <div class="form-group">
                            <label>Username</label><small class="errormessage">${message}</small><small class="errormessage">${messageusername}</small>
                            <input type="text" class="form-control" name="username"
                                   placeholder="A-Z, a-z, 0-9 only and less than 15 characters.">
                        </div>
                        <div class="form-group">
                            <label>Email</label><small class="errormessage">${messageemail}</small>
                            <input type="email" class="form-control" name="email"
                                   placeholder="Email">
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Password</label>
                                <input type="password" class="form-control" name="password"
                                       placeholder="Password">
                                <small class="errormessage">${messagepassword}</small>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Confirm Password</label>
                                <input type="password" class="form-control" name="repassword"
                                       placeholder="Confirm Password">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>First Name</label>
                                <input type="text" class="form-control" name="firstname"
                                       placeholder="First Name">
                            </div>
                            <div class="form-group col-md-6">
                                <label>Last Name</label>
                                <input type="text" class="form-control" name="lastname"
                                       placeholder="Last Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Address</label>
                            <textarea class="form-control" form="registerform" name="address"
                                      placeholder="Address"></textarea>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Post Code</label>
                                <input type="text" class="form-control" name="postcode"
                                       placeholder="Post Code">
                            </div>
                            <div class="form-group col-md-6">
                                <label>Phone Number</label>
                                <input type="text" class="form-control" name="phonenumber"
                                       placeholder="Phone Number">
                                <small class="errormessage">${messagephonenumber}</small>
                            </div>
                        </div>
                        <div class="form-group mt-4">
                            <button type="submit" class="btn btn-secondary">Register</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
