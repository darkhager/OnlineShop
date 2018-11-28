<%-- 
    Document   : PaymentDetail
    Created on : 25 พ.ย. 2561, 14:28:37
    Author     : lara_
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Detail</title>

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
                    <h1 class="products">Payment Method</h1>
                    <h4>Total: <span class="ml-5" style="color: #F56217">฿${sessionScope.totalpriceall}</span></h4>
                    <form class="mt-5" action="PaymentByCreditCard" method="POST">
                        <div>Enter Your credit card :</div>
                        <div class="row my-4">
                            <div class="col"><input type="text" maxlength="4" pattern="[0-9]{4}"
                                                    class="form-control" name="creditone"></div>
                            -
                            <div class="col"><input type="text" maxlength="4" pattern="[0-9]{4}"
                                                    class="form-control" name="creditonetwo"></div>
                            -
                            <div class="col"><input type="text" maxlength="4" pattern="[0-9]{4}"
                                                    class="form-control" name="creditthree"></div>
                            -
                            <div class="col"><input type="text" maxlength="4" pattern="[0-9]{4}"
                                                    class="form-control" name="creditfour"></div>
                        </div>
                        <div class="row my-4">
                            <div class="col-6">
                                CVC
                            </div>
                            <div class="col"><input type="text" maxlength="3" pattern="[0-9]{3}"
                                                    class="form-control" name="creditcvc"></div>
                        </div>
                        <div class="row my-4">
                            <div class="col-6">
                                Expired Date
                            </div>
                            <div class="col"><input type="text" maxlength="2" pattern="[0-9]{2}"
                                                    class="form-control" name="creditexdone"></div>
                            -
                            <div class="col"><input type="text" maxlength="2" pattern="[0-9]{2}"
                                                    class="form-control" name="creditexdtwo"></div>
                        </div>
                        <div class="form-group my-4">
                            <input type="hidden" value="${totalpriceall}" name="totalpriceall">
                            <input type="submit" class="btn btn-secondary" value="Confirm Your Payment">
                            <span class="ml-3" style="color: red">${invalidcredit}</span>
                        </div>
                    </form>
                    <div class="mt-5 mb-4">
                        <a href="Cart">Back to Cart</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
