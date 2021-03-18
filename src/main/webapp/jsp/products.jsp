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
    <title><fmt:message key="products.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="container">
    <main role="main" class="container">
        <c:import url="fragment/navigation.jsp"/>

        <div class="py-1 mb-2" style="line-height: 1;border-bottom: 1px solid #e5e5e5;">
            <nav class="nav d-flex justify-content-start">
                <div style="padding-top: 10px;">Sorted by:</div>
                <form>
                    <button type="submit" class="btn btn-link text-secondary">Popular</button>
                    <button type="submit" class="btn btn-link text-secondary">Cheap</button>
                    <button type="submit" class="btn btn-link text-secondary">Expensive</button>
                </form>
            </nav>
        </div>

        <div class="row">
            <div class="col-9 px-1">
                <c:forEach var="product" items="${products}">
                    <div class="card mb-3" style="max-width: 100%;">
                        <div class="row no-gutters">
                            <div class="col-md-4">
                                <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${product.image}"/>"
                                     class="card-img" alt=""/>
                            </div>
                            <div class="col-md-8">
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="view_product">
                                        <input type="hidden" name="productId" value="<c:out value="${product.id}"/>">
                                        <button type="submit" class="btn btn-link"
                                                style="padding-left: 0;padding-right: 0">
                                            <h5 class="card-title"><c:out value="${product.name}"/></h5>
                                        </button>
                                    </form>
                                    <p class="card-text"><c:out value="${product.description}"/></p>
                                    <h5 class="card-title"><ctg:formatCurrency value="${product.price}"
                                                                               locale="${locale}"/></h5>
                                    <c:choose>
                                        <c:when test="${product.amount gt 0}">
                                            <p><span class="badge badge-success"><fmt:message
                                                    key="products.in_stock"/></span></p>
                                        </c:when>
                                        <c:otherwise>
                                            <p><span class="badge badge-danger"><fmt:message
                                                    key="products.not_available"/></span></p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="col-3 px-1">
                <form>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div>
                                <h5>Price:</h5>
                                <div class="form-row">
                                    <div class="col">
                                        <div style="display: flex">
                                            <label for="from" class="col">From</label>
                                            <input type="text" id="from" class="form-control">
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div style="display: flex">
                                            <label for="to" class="col">To</label>
                                            <input type="text" id="to" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1">
                                <label class="form-check-label" for="inlineCheckbox1">In stock</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="option2">
                                <label class="form-check-label" for="inlineCheckbox2">Not available</label>
                            </div>
                        </li>
                        <li class="list-group-item" style="text-align: center">
                            <button class="btn btn-outline-primary my-2" type="submit">
                                Show products
                            </button>
                        </li>
                    </ul>
                </form>
            </div>
        </div>

        <c:if test="${not empty products}">
            <form class="form-inline" action="${pageContext.request.contextPath}/controller"
                  id="paginationForm">
                <input type="hidden" name="command" value="get_products">
                <input type="hidden" name="categoryId" value="<c:out value="${selectedCategory}"/>">
                <div class="form-row">
                    <ctg:pagination amountItem="${amountProduct}"/>
                </div>
            </form>
        </c:if>
    </main>
</div>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/products.js"></script>
</body>
</html>
