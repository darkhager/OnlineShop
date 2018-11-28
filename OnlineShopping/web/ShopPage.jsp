<%-- 
    Document   : shopPage
    Created on : Nov 1, 2018, 3:36:39 PM
    Author     : 60130
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products Page</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <div class="container">
            <div class="row">
                <jsp:include page="include/Leftmenu.jsp"/>

                <div class="col-lg-9">
                    <div class="navbar navbar-light my-lg-4">
                        <a class="navbar-brand">All Headphone</a>
                        <form action="Search" method="post" class="form-inline">
                            <input class="form-control mr-sm-2" type="text" name="search" placeholder="Search">
                            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit">Search</button>
                        </form>
                    </div>
                    <div class="row" id="ProductListing">
                        <c:forEach items="${products}" var="p" varStatus="vs">
                            <div class="col-lg-3 col-md-6 mb-4">
                                <div class="card h-100">
                                    <a href="ProductDetail?productid=${p.productid}">
                                        <img class="card-img-top" src="product-images/H0_${p.productid}.jpg" width="500">
                                    </a>
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <a href="ProductDetail?productid=${p.productid}">${p.productname}</a>
                                        </h5>
                                    </div>
                                    <div class="card-text mx-auto mb-2">
                                        <small>Type : ${p.producttype}</small>
                                    </div>
                                    <div class="card-text mx-auto mb-2">
                                        <h6>${p.price} Bath</h6>
                                    </div>
                                    <div class="card-footer text-center">
                                        <form action="AddToCart" method="POST">
                                            <input type="hidden" value="shoppage" name="from">
                                            <input type="hidden" value="${p.productid}" name="productid">
                                            <input class="btn btn-outline-secondary btn-sm" type="submit" value="Add to Cart">
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="include/Footer.jsp"/>
    </body>
</html>
