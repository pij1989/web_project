<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom_tag" prefix="ctg" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title><fmt:message key="products.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <div class="row">
                <div class="col">
                    <div style="display: flex; flex-grow: inherit; justify-content: flex-start">
                        <form class="form-inline my-3 my-lg-3 mx-lg-5"
                              action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="search_product">
                            <input class="form-control mr-sm-2" type="search" name="searchProduct"
                                   placeholder="<fmt:message key="products.search.placeholder"/>"
                                   aria-label="Search" size="30">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><span><i
                                    class="fas fa-search"></i></span> <fmt:message key="navigation.button.search"/>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="col">
                    <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                        <form id="selectCategoryForm" class="form-inline my-3 my-lg-3"
                              action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="get_products">
                            <div class="form-group">
                                <select name="categoryId" class="form-control" id="category">
                                    <option value="0">All categories</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="<c:out value="${category.id}"/>"
                                                <c:if test="${selectedCategory eq category.id}">selected</c:if>>
                                            <c:out value="${category.name}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                        <form class="form-inline my-3 my-lg-3" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="to_create_product_page_command">
                            <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                                <span><i class="fas fa-plus"></i> <fmt:message key="admin.button.create"/></span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <c:forEach var="product" items="${products}">
                        <div class="col-md-4">
                            <div class="card mb-4 shadow-sm">
                                <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${product.image}"/>"
                                     class="card-img-top" alt=""/>
                                <div class="card-body">
                                    <h5 class="card-title"><c:out value="${product.name}"/></h5>
                                    <p class="card-text"><c:out value="${product.description}"/></p>
                                    <h5 class="card-title"><ctg:formatCurrency value="${product.price}"/></h5>
                                    <h5 class="card-title"><fmt:message key="products.amount"/> <c:out value="${product.amount}"/></h5>
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
                                    <p><small><fmt:message key="products.create_time"/> <ctg:formatLocalDateTime
                                            date="${product.creatingTime}" locale="${locale}"/></small></p>
                                    <form action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="edit_product">
                                        <input type="hidden" name="productId"
                                               value="<c:out value="${product.id}"/>">
                                        <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                                            <span><i class="fas fa-edit"></i> <fmt:message key="products.edit"/></span>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
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
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/products.js"></script>
</body>
</html>
