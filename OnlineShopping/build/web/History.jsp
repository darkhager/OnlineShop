<%-- 
    Document   : cart
    Created on : Nov 1, 2018, 3:34:11 PM
    Author     : 60130
--%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your History</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <div class="container">
            <h1 class="products">History</h1>

            <table class="table table-hover my-3">
                <thead>
                    <tr style="font-size: 125%">
                        <th scope="col">#</th>
                        <th scope="col" colspan="2">Product Detail</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Price</th>
                        <th scope="col">Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="ordernum" value="${0}"/>
                    <c:set var="orderamount" value="${0}"/>
                    <c:set var="orderprice" value="${0}"/>

                    <c:forEach items="${history}" var="htr">
                        <c:if test="${ordernum != htr.ordernumber && ordernum != 0}">
                            <tr class="font-weight-bold" style="font-size: 125%">
                                <td></td>
                                <td colspan="2">Total</td>
                                <td>${orderamount}</td>
                                <td colspan="2">฿${orderprice}</td>
                            </tr>
                            <c:set var="orderamount" value="${0}"/>
                            <c:set var="orderprice" value="${0}"/>
                        </c:if>
                        <c:choose>
                            <c:when test="${ordernum != htr.ordernumber}">
                                <tr>
                                    <td>${htr.ordernumber}</td>
                                    <td><img src="product-images/H0_${htr.productid.productid}.jpg" width="100"></td>
                                    <td>${htr.productid.productname}</td>
                                    <td>${htr.amount}</td>
                                    <c:set var="orderamount" value="${orderamount + htr.amount}"/>
                                    <td>฿${htr.price}</td>
                                    <c:set var="orderprice" value="${orderprice + htr.price}"/>
                                    <c:set var = "date" value = "${htr.date}"/>
                                    <c:set var = "datecut" value = "${fn:substring(date, 4, 20)}"/>
                                    <td>${datecut}</td>
                                </tr>
                                <c:set var="ordernum" value="${htr.ordernumber}"/>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td></td>
                                    <td><img src="product-images/H0_${htr.productid.productid}.jpg" width="100"></td>
                                    <td>${htr.productid.productname}</td>
                                    <td>${htr.amount}</td>
                                    <c:set var="orderamount" value="${orderamount + htr.amount}"/>
                                    <td>฿${htr.price}</td>
                                    <c:set var="orderprice" value="${orderprice + htr.price}"/>
                                    <c:set var = "date" value = "${htr.date}"/>
                                    <c:set var = "datecut" value = "${fn:substring(date, 4, 20)}"/>
                                    <td>${datecut}</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <tr class="font-weight-bold" style="font-size: 125%">
                        <td></td>
                        <td colspan="2">Total</td>
                        <td>${orderamount}</td>
                        <td>฿${orderprice}</td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
            <div class="row my-5">
                <div class="col-sm-8 mt-3"><a href="ProductPage">Back to Shopping</a></div>
            </div>
        </div>
    </body>
</html>
