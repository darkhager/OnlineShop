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
                        <th scope="row"></th>
                        <th scope="col">Product Detail</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cart}" var="ct" varStatus="vs">
                        <tr>
                            <td><img src="product-images/H0_${ct.productid.productid}.jpg" width="100"></td>
                            <td>${ct.productid.productname}</td>
                            <td>
                                <ul class="pagination">
                                    <li class="page-item">
                                        <form action="RemoveFromCart" method="POST">
                                            <input type="hidden" value="cartpage" name="from">
                                            <input type="hidden" value="${ct.productid.productid}" name="productid">
                                            <input class="page-link" type="submit" value="-">
                                        </form>
                                    </li>
                                    <li class="page-item disabled"><a class="page-link" href="#">${ct.amount}</a></li>
                                    <li class="page-item">
                                        <form action="AddToCart" method="POST">
                                            <input type="hidden" value="cartpage" name="from">
                                            <input type="hidden" value="${ct.productid.productid}" name="productid">
                                            <c:choose>
                                                <c:when test="${ct.amount < ct.productid.amount}">
                                                    <input class="page-link" type="submit" value="+">
                                                </c:when>
                                                <c:otherwise>
                                                    <input class="page-link" type="submit" value="+" disabled>
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </li>
                                </ul>
                                <small>In stock : ${ct.productid.amount}</small>
                            </td>
                            <td>฿${ct.productid.price * ct.amount}</td>
                        </tr>
                        <c:set var="totalprice" value="${totalprice + (ct.productid.price * ct.amount)}" />
                    </c:forEach>
                    <c:choose>
                        <c:when test="${sessionScope.numincart <= 0}">
                            <tr>
                                <td colspan="4" class="text-center">Nothing here.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr class="font-weight-bold" style="font-size: 125%">
                                <th scope="row"></th>
                                <td>Total</td>
                                <td>${sessionScope.numincart}</td>
                                <td>฿${totalprice}</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
            <div class="row my-5">
                <div class="col-sm-8 mt-3"><a href="ProductPage">Back to Shopping</a></div>
                <div class="col">
                    <form action="PaymentDetail" method="POST">
                        <input type="hidden" value="${totalprice}" name="totalprice">
                        <c:choose>
                            <c:when test="${sessionScope.numincart <= 0}">
                                <input class="btn btn-outline-secondary btn-lg btn-block" type="submit" value="Check Out" disabled="">
                            </c:when>
                            <c:otherwise>
                                <input class="btn btn-outline-secondary btn-lg btn-block" type="submit" value="Check Out">
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
