<%-- 
    Document   : cart
    Created on : Nov 1, 2018, 3:34:11 PM
    Author     : 60130
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Shopping Cart</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>
        <c:set var="totalprice" value="${0}"/>

        <div class="container">
            <h1 class="products">Shopping Cart</h1>
            <table class="table table-hover">
                <thead>
                    <tr style="font-size: 125%">
                        <th scope="col">#</th>
                        <th scope="col" colspan="2">Product Detail</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cart}" var="ct" varStatus="vs">
                        <tr>
                            <th scope="row">${vs.count}</th>
                            <td><img src="product-images/H0_${ct.productid.productid}.jpg" width="100"></td>
                            <td>${ct.productid.productname}</td>
                            <td>${ct.amount}</td>
                            <td>${ct.productid.price}</td>
                        </tr>
                        <c:set var="totalprice" value="${totalprice + ct.productid.price}" />
                    </c:forEach>
                    <tr class="font-weight-bold" style="font-size: 125%">
                        <th scope="row"></th>
                        <td colspan="2">Total</td>
                        <td>${sessionScope.numincart}</td>
                        <td>${totalprice}</td>
                    </tr>
                </tbody>
            </table>
            <div class="row my-5">
                <div class="col-sm-9 mt-3"><a href="ProductPage">Back to Shopping</a></div>
                <div class="col-sm-3"><a class="btn btn-outline-dark btn-lg btn-block" href="#">Check Out</a></div>
            </div>
        </div>
    </body>
</html>
