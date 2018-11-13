<%-- 
    Document   : shopPage
    Created on : Nov 1, 2018, 3:36:39 PM
    Author     : 60130
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shop Homepage</title>

        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <div class="container">
            <div class="row">
                <div class="col-lg-3">
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
                                    <div><a href="#">Full Size</a></div>
                                    <div><a href="#">On Ear</a></div>
                                    <div><a href="#">In Ear</a></div>
                                    <div><a href="#">Ear Bud</a></div>
                                    <div><a href="#">Clip Ear</a></div>
                                    <div><a href="#">Ciem</a></div>
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
                                    <div><a href="#">Less than 1,000</a></div>
                                    <div><a href="#">1,001 - 2,000</a></div>
                                    <div><a href="#">2,001 - 5,000</a></div>
                                    <div><a href="#">5,001 - 10,000</a></div>
                                    <div><a href="#">10,001 - 30,000</a></div>
                                    <div><a href="#"></a>More than 30,000</div>
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
                                    <div><a href="#">Sony</a></div>
                                    <div><a href="#">KZ</a></div>
                                    <div><a href="#">Bose</a></div>
                                    <div><a href="#">JBL</a></div>
                                    <div><a href="#">B&O</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-9">
                    <div class="navbar navbar-light my-lg-4">
                        <a class="navbar-brand">All Headphone</a>
                        <form class="form-inline">
                            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit">Search</button>
                        </form>
                    </div>
                    <div id="carouselExampleIndicators" class="carousel slide my-4 carousel-fade" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                        </ol>
                        <div class="carousel-inner" role="listbox">
                            <div class="carousel-item active">
                                <img class="d-block img-fluid" src="http://placehold.it/900x300" alt="First slide">
                                <div class="carousel-caption d-none d-md-block">
                                    <h5>Items name</h5>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img class="d-block img-fluid" src="http://placehold.it/900x300" alt="Second slide">
                                <div class="carousel-caption d-none d-md-block">
                                    <h5>Items name</h5>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img class="d-block img-fluid" src="http://placehold.it/900x300" alt="Third slide">
                                <div class="carousel-caption d-none d-md-block">
                                    <h5>Items name</h5>
                                </div>
                            </div>
                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                    <nav>
                        <ul class="pagination justify-content-end">
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                            <li class="page-item"><a class="page-link" href="#">4</a></li>
                            <li class="page-item"><a class="page-link" href="#">5</a></li>
                            <li class="page-item">
                                <a class="page-link" href="#">Next</a>
                            </li>
                        </ul>
                    </nav>
                    <div class="row">
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card h-100">
                                <a href="#"><img class="card-img-top" src="http://placehold.it/500x500" width="500"></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="#">Item One</a>
                                    </h4>
                                    <h5>$24.99</h5>
                                </div>
                                <div class="card-footer text-center">
                                    <a class="btn btn-outline-secondary btn-sm" href="#" role="button">Add to Cart</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card h-100">
                                <a href="#"><img class="card-img-top" src="http://placehold.it/500x500" width="500"></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="#">Item Two</a>
                                    </h4>
                                    <h5>$24.99</h5>
                                </div>
                                <div class="card-footer text-center">
                                    <a class="btn btn-outline-secondary btn-sm" href="#" role="button">Add to Cart</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card h-100">
                                <a href="#"><img class="card-img-top" src="http://placehold.it/500x500" width="500"></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="#">Item Three</a>
                                    </h4>
                                    <h5>$24.99</h5>
                                </div>
                                <div class="card-footer text-center">
                                    <a class="btn btn-outline-secondary btn-sm" href="#" role="button">Add to Cart</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 mb-4">
                            <div class="card h-100">
                                <a href="#"><img class="card-img-top" src="http://placehold.it/500x500" width="500"></a>
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <a href="#">Item Four</a>
                                    </h4>
                                    <h5>$24.99</h5>
                                </div>
                                <div class="card-footer text-center">
                                    <a class="btn btn-outline-secondary btn-sm" href="#" role="button">Add to Cart</a>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <nav>
                <ul class="pagination justify-content-end">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">Previous</a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">4</a></li>
                    <li class="page-item"><a class="page-link" href="#">5</a></li>
                    <li class="page-item">
                        <a class="page-link" href="#">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
    </body>
</html>
