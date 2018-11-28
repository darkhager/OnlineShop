<%-- 
    Document   : profile
    Created on : Nov 1, 2018, 3:33:35 PM
    Author     : 60130
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Account Page</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <div class="container">
            <h1 class="products">Your Account</h1>
            <h5 class="mb-4" style="color: green">${savemessage}</h5>
            <div class="row">
                <div class="col-lg-3">
                    <h3>Username</h3>
                </div>
                <div class="col">
                    <div class="my-1">
                        ${sessionScope.account.username}
                    </div>
                    <div class="my-3">
                        <form action="EditAccount" method="POST">
                            <div class="form-row">
                                <div class="col-8">
                                    <input type="text" class="form-control" name="newusername"
                                           placeholder="Enter New Username">
                                </div>
                                <div class="col">
                                    <button type="submit" class="btn btn-secondary">Save Changed</button>
                                </div>
                            </div>
                        </form>
                        <div style="color: red"><small>${invalidusername}</small></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3 my-lg-4">
                    <h3>Password</h3>
                </div>
                <div class="col mt-lg-3 my-lg-4">
                    <form action="EditAccount" method="POST">
                        <div class="form-row mb-2">
                            <div class="col-4">
                                <input type="password" class="form-control" name="oldpassword"
                                       placeholder="Old Password">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="col">
                                <input type="password" class="form-control" name="newpassword"
                                       placeholder="New Password">
                            </div>
                            <div class="col">
                                <input type="password" class="form-control" name="newrepassword"
                                       placeholder="Confirm New Password">
                            </div>
                            <div class="col">
                                <button type="submit" class="btn btn-secondary">Save Changed</button>
                            </div>
                        </div>
                        <div style="color: red"><small>${invalidpassword}</small></div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3 my-lg-4">
                    <h3>Email</h3>
                </div>
                <div class="col my-lg-4">
                    <div class="my-1">
                        ${sessionScope.account.email}
                    </div>
                    <div class="my-3">
                        <form action="EditAccount" method="POST">
                            <div class="form-row">
                                <div class="col-8">
                                    <input type="text" class="form-control" name="newemail"
                                           placeholder="Enter New Email">
                                </div>
                                <div class="col">
                                    <button type="submit" class="btn btn-secondary">Save Changed</button>
                                </div>
                            </div>
                        </form>
                        <div style="color: red"><small>${invalidemail}</small></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3 my-lg-4">
                    <h3>Name</h3>
                </div>
                <div class="col my-lg-4">
                    <div class="my-1">
                        ${sessionScope.account.firstname} ${sessionScope.account.lastname}
                    </div>
                    <div class="my-3">
                        <form action="EditAccount" method="POST">
                            <div class="form-row">
                                <div class="col">
                                    <input type="text" class="form-control" name="newfname"
                                           placeholder="New First Name">
                                </div>
                                <div class="col">
                                    <input type="text" class="form-control" name="newlname"
                                           placeholder="New Last Name">
                                </div>
                                <div class="col">
                                    <button type="submit" class="btn btn-secondary">Save Changed</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3 my-lg-4">
                    <h3>Address</h3>
                </div>
                <div class="col my-lg-4">
                    <div>
                        <div class="my-1">
                            ${sessionScope.account.address}
                        </div>
                        <div class="my-3">
                            <form id="accountform" action="EditAccount" method="POST">
                                <div class="form-row">
                                    <div class="col-8">
                                        <textarea class="form-control" form="accountform" name="newaddress"
                                                  placeholder="New Address"></textarea>
                                    </div>
                                    <div class="col">
                                        <button type="submit" class="btn btn-secondary">Save Changed</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div>
                        <div class="my-1">
                            ${sessionScope.account.postcode}
                        </div>
                        <div class="my-3">
                            <form action="EditAccount" method="POST">
                                <div class="form-row">
                                    <div class="col-8">
                                        <input type="number" class="form-control" name="newpostcode"
                                               placeholder="New Post Code">
                                    </div>
                                    <div class="col">
                                        <button type="submit" class="btn btn-secondary">Save Changed</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3 my-lg-4">
                    <h3>Phone Number</h3>
                </div>
                <div class="col my-lg-4">
                    <div class="my-1">
                        ${sessionScope.account.phonenumber}
                    </div>
                    <div class="my-3">
                        <form action="EditAccount" method="POST">
                            <div class="form-row">
                                <div class="col-8">
                                    <input type="text" class="form-control" name="newphonenum"
                                           placeholder="New Phone Number">
                                </div>
                                <div class="col">
                                    <button type="submit" class="btn btn-secondary">Save Changed</button>
                                </div>
                            </div>
                        </form>
                        <div style="color: red"><small>${invalidphonenum}</small></div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="include/Footer.jsp"/>
    </body>
</html>
