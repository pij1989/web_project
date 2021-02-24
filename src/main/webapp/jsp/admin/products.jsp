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
            <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                <form class="form-inline my-3 my-lg-3" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="to_create_product_page_command">
                    <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                        <span><i class="fas fa-plus"></i> <fmt:message key="admin.button.create"/></span>
                    </button>
                </form>
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
                                    <c:choose>
                                        <c:when test="${product.status}">
                                            <span class="badge badge-success">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">Not active</span>
                                        </c:otherwise>
                                    </c:choose>
                                    <h5 class="card-title"><ctg:formatCurrency value="${product.price}" locale="${locale}"/></h5>
                                    <p><small><fmt:message key="products.createtime"/> <ctg:formatLocalDateTime
                                            date="${product.creatingTime}" locale="${locale}"/></small></p>
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="edit_product">
                                        <input type="hidden" name="categoryId"
                                               value="<c:out value="${product.id}"/>">
                                        <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                                            <span><i class="fas fa-edit"></i> Edit</span>
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
<script src="${pageContext.request.contextPath}/js/admin/products.js"></script>
</body>
</html>
