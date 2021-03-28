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
            <div style="display: flex; flex-grow: inherit; justify-content: flex-start">
                <form class="form-inline my-3 my-lg-3 mx-lg-5"
                      action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="search_product">
                    <input class="form-control mr-sm-2" type="search" name="searchProduct"
                           placeholder="<fmt:message key="products.search.placeholder"/>"
                           aria-label="Search" size="30" value="<c:out value="${searchProduct}"/>">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><span><i
                            class="fas fa-search"></i></span> <fmt:message key="navigation.button.search"/>
                    </button>
                </form>
            </div>
            <div class="container">
                <c:choose>
                    <c:when test="${not empty products}">
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
                                            <h5 class="card-title"><fmt:message key="products.amount"/> <c:out
                                                    value="${product.amount}"/></h5>
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
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center;padding-top: 15px">
                            <h1><fmt:message key="products.search.not_found_message"/></h1>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/products.js"></script>
</body>
</html>
