<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="custom_tag" prefix="ctg" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/view_product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation.css">
    <title><fmt:message key="product.view.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<main role="main" class="container" style="min-height: calc(100vh - 112px)">
    <c:import url="fragment/navigation.jsp"/>
    <c:choose>
        <c:when test="${addReviewSuccess}">
            <div class="alert alert-success" role="alert" id="successAddReview">
                <fmt:message key="product.view.review_add_message"/>
            </div>
            <c:remove var="addReviewSuccess" scope="session"/>
        </c:when>
        <c:when test="${addReviewError}">
            <div class="alert alert-danger" role="alert" id="errorAddReview">
                <fmt:message key="product.view.error_message"/>
            </div>
            <c:remove var="addReviewError" scope="session"/>
        </c:when>
    </c:choose>
    <div class="card">
        <h4 class="card-header"><c:out value="${product.name}"/></h4>
        <div class="card-body">
            <div class="d-flex flex-row">
                <ctg:averageRating/>
                <div class="px-4">
                    <a href="#review">
                        <c:if test="${not empty reviews}">${fn:length(reviews)}</c:if> <fmt:message key="product.view.review"/>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="card mb-3" style="max-width: 100%;">
        <div class="row no-gutters">
            <div class="col-6">
                <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${product.image}"/>" class="card-img"/>
            </div>
            <div class="col-6">
                <div class="card-body">
                    <h5 class="card-title"><ctg:formatCurrency value="${product.price}"/></h5>
                    <p class="card-text"><c:out value="${product.description}"/></p>
                    <c:choose>
                        <c:when test="${product.amount gt 0}">
                            <p><span class="badge badge-success"><fmt:message key="products.in_stock"/></span></p>
                        </c:when>
                        <c:otherwise>
                            <p><span class="badge badge-danger"><fmt:message key="products.not_available"/></span></p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="add_product_to_order">
                    <input type="hidden" name="amountProduct" value="1">
                    <div class="view-product-btn">
                        <c:set var="flag" value="true"/>
                        <c:forEach var="orderProduct" items="${orderProducts}">
                            <c:if test="${orderProduct.product.id eq product.id}">
                                <button type="submit" class="btn btn-outline-primary" disabled>
                                    <c:if test="${order.statusType eq 'NEW'}">
                                        <a href="${pageContext.request.contextPath}/controller?command=view_cart">
                                            <fmt:message key="product.view.button.in_the_cart"/>
                                        </a>
                                    </c:if>
                                    <c:if test="${order.statusType eq 'PROCESSING'}">
                                        <a href="${pageContext.request.contextPath}/controller?command=arrange_order">
                                            <fmt:message key="product.view.button.in_the_cart"/>
                                        </a>
                                    </c:if>
                                </button>
                                <c:set var="flag" value="false"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${flag}">
                            <button type="submit" class="btn btn-primary">
                                <fmt:message key="product.view.button.add_to_cart"/>
                            </button>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="card text-center" id="review">
        <div class="card-header">
            <ul class="nav nav-tabs card-header-tabs">
                <li class="nav-item">
                    <a class="nav-link active" href="#"><fmt:message key="product.view.review"/> </a>
                </li>
            </ul>
        </div>
        <div class="card-body">
            <form id="addReviewForm" method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="add_review">
                <div class="form-group">
                    <label for="comment"><fmt:message key="product.view.label.comment"/></label>
                    <textarea class="form-control" name="comment" id="comment" rows="5" required></textarea>
                </div>
                <div class="form-group">
                    <label for="rating"><fmt:message key="product.view.label.rating"/></label>
                    <select class="form-control" name="rating" id="rating">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                </div>
                <div class="view-product-btn">
                    <button type="submit" id="submit" class="btn btn-primary">
                        <fmt:message key="product.view.button.add_review"/>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <c:forEach var="review" items="${reviews}">
        <div class="card">
            <div class="card-body">
                <div>
                    <c:forEach var="i" begin="1" end="${review.rating}">
                        <span class="fa fa-star checked-rate"></span>
                    </c:forEach>
                </div>
                <h5 class="card-title"><c:out value="${review.username}"/></h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    <ctg:formatLocalDateTime date="${review.creatingTime}" locale="${locale}"/>
                </h6>
                <p class="card-text"><c:out value="${review.comment}"/></p>
            </div>
        </div>
    </c:forEach>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/view_product.js"></script>
</body>
</html>
