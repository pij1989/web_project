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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation.css">
    <title><fmt:message key="products.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<main role="main" class="container" style="min-height: calc(100vh - 112px)">
    <c:import url="fragment/navigation.jsp"/>
    <c:choose>
        <c:when test="${not empty products}">
            <div class="py-1 mb-2" style="line-height: 1;border-bottom: 1px solid #e5e5e5;">
                <nav class="nav d-flex justify-content-start pb-2">
                    <div class="px-2"><fmt:message key="products.sort"/></div>
                    <form id="sortForm" action="${pageContext.request.contextPath}/controller">
                        <c:choose>
                            <c:when test="${withFilter}">
                                <input type="hidden" name="command" value="filter_product">
                                <input type="hidden" name="priceFrom" value="${filterProductForm['priceFrom']}">
                                <input type="hidden" name="priceTo" value="${filterProductForm['priceTo']}">
                                <input type="hidden" name="inStock" value="${filterProductForm['inStock']}">
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="command" value="get_products">
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="categoryId" value="<c:out value="${selectedCategory}"/>">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="sort" id="sortNew" value="new"
                                   <c:if test="${sortType eq 'new'}">checked</c:if>>
                            <label class="form-check-label" for="sortNew"><fmt:message key="products.sort_type.new"/></label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="sort" id="sortCheap" value="cheap"
                                   <c:if test="${sortType eq 'cheap'}">checked</c:if>>
                            <label class="form-check-label" for="sortCheap"><fmt:message key="products.sort_type.cheap"/></label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="sort" id="sortExpensive"
                                   value="expensive"
                                   <c:if test="${sortType eq 'expensive'}">checked</c:if>>
                            <label class="form-check-label" for="sortExpensive"><fmt:message key="products.sort_type.expensive"/></label>
                        </div>
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
                                            <input type="hidden" name="productId"
                                                   value="<c:out value="${product.id}"/>">
                                            <button type="submit" class="btn btn-link"
                                                    style="padding-left: 0;padding-right: 0">
                                                <h5 class="card-title"><c:out value="${product.name}"/></h5>
                                            </button>
                                        </form>
                                        <p class="card-text"><c:out value="${product.description}"/></p>
                                        <h5 class="card-title"><ctg:formatCurrency value="${product.price}"/></h5>
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
                    <form id="filterForm" class="needs-validation <c:if test="${not empty filterProductForm}"/>"
                          action="${pageContext.request.contextPath}/controller" novalidate>
                        <input type="hidden" name="command" value="filter_product">
                        <input type="hidden" name="categoryId" value="<c:out value="${selectedCategory}"/>">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <div>
                                    <h5><fmt:message key="products.filter.price"/></h5>
                                    <div class="form-row">
                                        <div class="col">
                                            <label for="from"><fmt:message key="products.filter.price.label.from"/></label>
                                            <input type="text" name="priceFrom"
                                                   value="${filterProductForm['priceFrom']}" id="from"
                                                   class="form-control"
                                                   placeholder="0000.00"
                                                   pattern="^[0-9]{1,4}\.[0-9]{2}$">
                                        </div>
                                        <div class="col">
                                            <label for="to"><fmt:message key="products.filter.price.label.to"/></label>
                                            <input type="text" name="priceTo" value="${filterProductForm['priceTo']}"
                                                   id="to" class="form-control"
                                                   placeholder="0000.00"
                                                   pattern="^[0-9]{1,4}\.[0-9]{2}$">
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" name="inStock" type="checkbox" id="inStock"
                                           value="true"
                                           <c:if test="${filterProductForm['inStock']}">checked</c:if> />
                                    <label class="form-check-label" for="inStock"><fmt:message key="products.filter.in_stock"/></label>
                                </div>
                            </li>
                            <li class="list-group-item" style="text-align: center">
                                <button class="btn btn-outline-primary my-2" type="submit">
                                    <fmt:message key="products.filter.button"/>
                                </button>
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card bg-light m-5" style="max-width: 100%;">
                <div class="card-body bg-light" style="text-align: center">
                    <h2><fmt:message key="products.not_found"/></h2>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <c:if test="${not empty products}">
        <c:if test="${not withFilter}">
            <form class="form-inline" action="${pageContext.request.contextPath}/controller"
                  id="paginationForm">
                <input type="hidden" name="command" value="get_products">
                <input type="hidden" name="categoryId" value="<c:out value="${selectedCategory}"/>">
                <input type="hidden" name="sort" value="<c:out value="${sortType}"/>">
                <div class="form-row">
                    <ctg:pagination amountItem="${amountProduct}"/>
                </div>
            </form>
        </c:if>
    </c:if>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/products.js"></script>
</body>
</html>
