<%-- 
    Document   : header
    Created on : 13 พ.ย. 2561, 21:28:49
    Author     : lara_
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="navbar navbar-expand-lg navbar-dark bg-gradient fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#">
            <img src="images/headphones-white.png" width="30" height="30" class="d-inline-block align-top" alt="">
            Headphone Shop
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="ProductPage">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ProductPage">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contact</a>
                </li>
            </ul>
            <c:choose>
                <c:when test="${sessionScope.account != null}">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item dropdown active">
                            <a class="nav-link" href="#" id="navbarDropdown" role="button">
                                ${sessionScope.account.username}'s Account
                            </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="#">My Account</a>
                                <a class="dropdown-item" href="#">Favorite</a>
                                <a class="dropdown-item" href="#">History</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="Logout">Log Out</a>
                            </div>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="Login">Log in</a>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>
            <ul class="navbar-nav ml-3">
                <form class="form-inline">
                    <button class="btn btn-light btn-lg" type="button">
                        <img src="images/shopping-cart.png" width="30" height="30" class="d-inline-block" alt="">
                        <%--<span class="badge badge-pill badge-danger">1</span>--%>
                    </button>
                </form>
            </ul>
        </div>
    </div>
</nav>
