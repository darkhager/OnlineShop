<%-- 
    Document   : MainPage
    Created on : 27 พ.ย. 2561, 0:04:28
    Author     : lara_
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome! | Hedgehog Headphone</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <div class="py-3 headerpic">
            <div class="container">
                <div class="text-center" style="margin-top: 170px;">
                    <h1 class="products display-3">Hedgehog Headphone</h1>
                    <div class="mx-5">
                        <div>If you looking for good headphone shop, here we are!</div>
                        <div>A review from <em>Best Shop Around You.</em>
                        </div>
                    </div>
                    <div class="mx-5 my-4">
                        <a href="ProductPage" class="btn btn-outline-light btn-lg">View All Products</a>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="include/Footer.jsp"/>
    </body>
</html>
