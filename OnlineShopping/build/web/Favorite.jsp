<%-- 
    Document   : Favorite
    Created on : 27 พ.ย. 2561, 23:32:21
    Author     : lara_
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Favorite</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>
        <div class="container">
            <h1 class="products">Favorite</h1>
            <table class="table table-hover my-3">
                <thead>
                    <tr style="font-size: 125%">
                        <th scope="row">#</th>
                        <th scope="col" colspan="2">Product Detail</th>
                        <th scope="col">Price</th>
                        <th scope="row">Favorite</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="totalnum" value="${0}"/>
                    <c:forEach items="${favorite}" var="fav" varStatus="vs">
                        <tr>
                            <td>${vs.count}</td>
                            <td><img src="product-images/H0_${fav.productid.productid}.jpg" width="100"></td>
                            <td>
                                <a href="ProductDetail?productid=${fav.productid.productid}">
                                    ${fav.productid.productname}
                                </a>
                            </td>
                            <td>฿${fav.productid.price}</td>
                            <td>
                                <form class="form-inline" action="RemoveFavorite" method="POST">
                                    <input type="hidden" value="favpage" name="from">
                                    <input type="hidden" value="${fav.productid.productid}" name="productid">
                                    <input type="image" src="images/star.png" width="40" height="40" alt="Submit">
                                </form>
                            </td>
                        </tr>
                        <c:set var="totalnum" value="${totalnum + 1}"/>
                    </c:forEach>
                    <c:if test="${totalnum <= 0}">
                        <tr>
                            <td colspan="5" class="text-center">Nothing here.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            <div class="row my-5">
                <div class="col-sm-8 mt-3"><a href="ProductPage">Back to Shopping</a></div>
            </div>
        </div>
    </body>
</html>
