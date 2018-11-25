<%-- 
    Document   : PaymentSuccess
    Created on : 25 พ.ย. 2561, 21:00:06
    Author     : lara_
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Success!</title>

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
                    <h1 class="products">Payment Success!</h1>
                    <h4>Thanks for purchased.</h4>
                    <div class="mt-5 my-4"><a href="ProductPage">Back to Shopping</a></div>
                </div>
            </div>
        </div>
    </body>
</html>
