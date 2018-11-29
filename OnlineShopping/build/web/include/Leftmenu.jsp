<%-- 
    Document   : leftmenu
    Created on : 15 พ.ย. 2561, 22:57:57
    Author     : lara_
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="col-lg-3 mb-5">
    <h1 class="products">Products</h1>
    <div class="accordion" id="accordionExample">
        <div class="card">
            <div class="card-header bg-gradient" id="headingOne">
                <h5 class="mb-0 producthead">
                    Headphone Type
                </h5>
            </div>
            <div id="m1" class="multi-collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div class="card-body ml-4">
                    <div><a href="Search?search=full size">Full Size</a></div>
                    <div><a href="Search?search=in ear">In Ear</a></div>
                    <div><a href="Search?search=clip ear">Clip Ear</a></div>
                    <div><a href="Search?search=ciem">Ciem (Custom in-ear monitor)</a></div>
                    <div><a href="Search?search=wireless">Wireless</a></div>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header bg-gradient" id="headingTwo">
                <h5 class="mb-0 producthead">
                    Price
                </h5>
            </div>
            <div id="m2" class="multi-collapse show" aria-labelledby="headingTwo" data-parent="#accordionExample">
                <div class="card-body ml-4">
                    <div><a href="Filter?price=1">Less than 1,000</a></div>
                    <div><a href="Filter?price=2">1,001 - 2,000</a></div>
                    <div><a href="Filter?price=3">2,001 - 5,000</a></div>
                    <div><a href="Filter?price=4">5,001 - 10,000</a></div>
                    <div><a href="Filter?price=5">10,001 - 30,000</a></div>
                    <div><a href="Filter?price=6">More than 30,000</a></div>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header bg-gradient" id="headingThree">
                <h5 class="mb-0">
                    <h5 class="mb-0 producthead">
                        Brand
                    </h5>
                </h5>
            </div>
            <div id="m3" class="multi-collapse show" aria-labelledby="headingThree" data-parent="#accordionExample">
                <div class="card-body ml-4">
                    <div><a href="Search?search=sony">Sony</a></div>
                    <div><a href="Search?search=kz">KZ</a></div>
                    <div><a href="Search?search=bose">Bose</a></div>
                    <div><a href="Search?search=jbl">JBL</a></div>
                    <div><a href="Search?search=bando">B&O</a></div>
                </div>
            </div>
        </div>
    </div>
</div>