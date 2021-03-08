<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom_tag" prefix="ctg" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <style>
        .checked {
            color: orange;
        }
    </style>
    <title><fmt:message key="products.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="container">
    <main role="main" class="container">
        <c:import url="fragment/navigation.jsp"/>

        <div class="card">
            <h4 class="card-header"><c:out value="${product.name}"/></h4>
            <div class="card-body">
                <div class="d-flex flex-row">
                    <div>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star"></span>
                        <span class="fa fa-star"></span>
                    </div>
                    <div class="px-4">
                        <a href="#review">Review</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="card mb-3" style="max-width: 100%;">
            <div class="row no-gutters">
                <div class="col-6">
                    <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${product.image}"/>" class="card-img"
                         alt=""/>
                </div>
                <div class="col-6">
                    <div class="card-body">
                        <h5 class="card-title"><ctg:formatCurrency value="${product.price}" locale="${locale}"/></h5>
                        <p class="card-text"><c:out value="${product.description}"/></p>
                        <c:choose>
                            <c:when test="${product.status}">
                                <p><span class="badge badge-success"><fmt:message
                                        key="products.active"/></span></p>
                            </c:when>
                            <c:otherwise>
                                <p><span class="badge badge-danger"><fmt:message
                                        key="products.not_active"/></span></p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <form>
                        <div class="create-item">
                            <button type="submit" id="" class="btn btn-primary">
                                Append to cart
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="card text-center" id="review">
            <div class="card-header">
                <ul class="nav nav-tabs card-header-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Review</a>
                    </li>
                </ul>
            </div>
            <div class="card-body">
                <form id="addReviewForm">
                    <div class="form-group">
                        <label for="comment">Comment</label>
                        <textarea class="form-control" name="comment" id="comment" rows="5" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="rating">Rating</label>
                        <select class="form-control" name="rating" id="rating">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>
                    <div class="create-item">
                        <button type="submit" id="submit" class="btn btn-primary">Add review</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/view_product.js"></script>
</body>
</html>
