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
        <c:set var="totalpriceall" value="${sessionScope.totalprice}"/>

        <div class="container">
            <h1 class="products">Payment</h1>
            <div class="row">
                <div class="col-sm-4">
                    <h3>Address for Sending</h3>
                </div>
                <div class="col">
                    <a class="btn btn-secondary" href="Account">Edit Account</a>
                </div>
            </div>
            <div>
                <div class="text-justify mx-4 my-3">${sessionScope.account.firstname} ${sessionScope.account.lastname}</div>
                <p class="text-justify mx-4">${sessionScope.account.address}</p>
                <div class="text-justify mx-4 my-3">${sessionScope.account.postcode}</div>
                <div class="text-justify mx-4 my-3">Phone Number: ${sessionScope.account.phonenumber}</div>
            </div>
            <div class="my-4">
                <h3>Order Summary</h3>
                <div class="mx-4">
                    <div class="row my-3">
                        <div class="col-2">
                            <strong>Result</strong>
                        </div>
                        <div class="col-7">
                            <span class="text-muted">Amount: ${sessionScope.numincart}</span>
                        </div>
                        <div class="col">
                            ฿${totalpriceall}
                        </div>
                    </div>
                    <div class="row my-3">
                        <div class="col-2">
                            <strong>Shipping Charge</strong>
                        </div>
                        <div class="col-7">
                            <span class="text-muted">Registered</span>
                        </div>
                        <div class="col">
                            ฿60
                            <c:set var="totalpriceall" value="${totalpriceall - 60}" />
                        </div>
                    </div>
                    <div class="row my-4" style="font-size: 125%">
                        <div class="col-9">
                            <strong>Total</strong>
                        </div>
                        <div class="col" style="color: #F56217">
                            ฿${totalpriceall}
                        </div>
                    </div>
                </div>
            </div>
            <div class="row my-5">
                <div class="col-sm-8 mt-3"><a href="ProductPage">Back to Shopping</a></div>
                <div class="col">
                    <form action="PaymentByCreditCard" method="POST">
                        <input type="hidden" value="${totalpriceall}" name="totalpriceall">
                        <input class="btn btn-outline-secondary btn-lg btn-block" type="submit" value="Confirm Your Result">
                    </form>
                </div>
            </div>
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
                    <c:set var="totalprice" value="${0}"/>
                    <c:forEach items="${cart}" var="ct" varStatus="vs">
                        <c:if test="${ct.amount != 0}">
                            <tr>
                                <td><img src="product-images/H0_${ct.productid.productid}.jpg" width="100"></td>
                                <td>${ct.productid.productname}</td>
                                <td>${ct.amount}</td>
                                <td>${ct.productid.price * ct.amount}</td>
                            </tr>
                            <c:set var="totalprice" value="${totalprice + (ct.productid.price * ct.amount)}" />
                        </c:if>
                    </c:forEach>
                    <tr class="font-weight-bold" style="font-size: 125%">
                        <th scope="row"></th>
                        <td>Total</td>
                        <td>${sessionScope.numincart}</td>
                        <td>${totalprice}</td>
                    </tr>
                </tbody>
            </table>
            <div class="row my-5">
                <div class="col-sm-8 mt-3"><a href="ProductPage">Back to Shopping</a></div>
                <div class="col">
                    <form action="PaymentByCreditCard" method="POST">
                        <input type="hidden" value="${totalpriceall}" name="totalpriceall">
                        <input class="btn btn-outline-secondary btn-lg btn-block" type="submit" value="Confirm Your Result">
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="include/Footer.jsp"/>
    </body>
</html>
