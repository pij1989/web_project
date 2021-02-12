<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale scope="session" value="${locale}"/>
<fmt:setBundle basename="property.text" var="text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <fmt:message key="main.title" var="title" bundle="${text}"/>
    <title>${title}</title>
</head>
<body>
<c:import url="fragment/header.jsp"/>

<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>

        <main class="col-md-9 col-xl-10 bd-content" role="main">
            <div class="container" style="margin-bottom: 30px">
                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img
                                    src="${pageContext.request.contextPath}/img/laptops_notepad.jpg"
                                    class="d-block w-100"
                            />
                        </div>
                        <div class="carousel-item">
                            <img
                                    src="${pageContext.request.contextPath}/img/smartphone.jpg"
                                    class="d-block w-100"
                            />
                        </div>
                        <div class="carousel-item">
                            <img
                                    src="${pageContext.request.contextPath}/img/headphone.jpg"
                                    class="d-block w-100"
                            />
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <img src="${pageContext.request.contextPath}/img/asus_tuf_gaming.jpg" class="bd-placeholder-img " width="140" height="140">
                    <h4>Игровой ноутбук ASUS TUF Gaming F15 FX506LI-HN012</h4>
                    <p>15.6" 1920 x 1080 IPS, Intel Core i5 10300H 2500 МГц, 8 ГБ, SSD 512 ГБ, граф. адаптер: NVIDIA GeForce GTX 1650 Ti 4 ГБ, без ОС</p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
                </div>
                <div class="col-lg-4">
                    <img src="${pageContext.request.contextPath}/img/jet_gamer_5R2600D16SD24X166SL2W5.jpg" class="bd-placeholder-img " width="140" height="140">
                    <h4>Компьютер Jet Gamer 5R2600D16SD24X166SL2W5</h4>
                    <p>CPU AMD Ryzen 5 2600 3400 МГц, RAM DDR4 16 ГБ, SSD 240 ГБ, графика: NVIDIA GeForce GTX 1660 Super 6 ГБ, БП 500 Вт</p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
                </div><!-- /.col-lg-4 -->
                <div class="col-lg-4">
                    <img src="${pageContext.request.contextPath}/img/apple_iPad10.2_32GB%20MW742.jpg" class="bd-placeholder-img " width="140" height="140">
                    <h4>Планшет Apple iPad 10.2"</h4>
                    <h4>32GB MW742</h4>
                    <p>10.2" IPS (2160x1620), iPadOS, Apple A10, ОЗУ 3 ГБ, флэш-память   32 ГБ, цвет темно-серый</p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
                </div><!-- /.col-lg-4 -->
            </div><!-- /.row -->
        </main>

    </div>
</div>

<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
</body>
</html>
