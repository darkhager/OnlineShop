<%-- 
    Document   : productDetail
    Created on : Nov 1, 2018, 3:34:30 PM
    Author     : 60130
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${product.productname} Page</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <div class="container">
            <div class="row">
                <div class="col-lg-6 my-lg-4">
                    <img class="card-img-top" src="product-images/H0_${product.productid}.jpg" width="800">
                </div>
                <div class="col-lg-6 my-lg-5">
                    <h1>${product.productname}</h1>
                    <div class="ml-4 my-3">
                        <c:choose>
                            <c:when test="${isfav == true}">
                                <form class="form-inline" action="RemoveFavorite" method="POST">
                                    <input type="hidden" value="detailpage" name="from">
                                    <input type="hidden" value="${product.productid}" name="productid">
                                    <input type="image" src="images/star.png" width="40" height="40" alt="Submit">
                                    <h3 class="ml-2 my-auto" style="color: #F56217">Unfavorite</h3>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form class="form-inline" action="AddFavorite" method="POST">
                                    <input type="hidden" value="detailpage" name="from">
                                    <input type="hidden" value="${product.productid}" name="productid">
                                    <input type="image" src="images/star-outline.png" width="40" height="40" alt="Submit">
                                    <h3 class="ml-2 my-auto" style="color: #F56217">Add Favorite</h3>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="ml-4 my-3">
                        <div>Product Brand : ${product.brand}</div>
                        <div>Product Type : ${product.producttype}</div>
                    </div>
                    <div class="ml-4 my-3 mx-3">
                        <div class="mb-2">Product Detail</div>
                        <p class="text-justify mx-4">
                            ${product.detail}
                        </p>
                    </div>
                    <div class="ml-4">
                        <h2>Price</h2>
                        <h3 class="mx-4">${product.price} Bath.</h3>
                    </div>
                    <div class="ml-4 my-5">
                        <form action="AddToCart" method="POST">
                            <input type="hidden" value="detailpage" name="from">
                            <input type="hidden" value="${product.productid}" name="productid">
                            <input class="btn btn-outline-secondary btn-lg" type="submit" value="Add to Cart" ${disabled}>
                            <span class="ml-4">In stock : ${product.amount}</span>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="include/Footer.jsp"/>
    </body>
</html>
