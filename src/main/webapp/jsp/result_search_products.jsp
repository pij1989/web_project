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
            <div class="row">
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
        </c:when>
        <c:otherwise>
            <div class="card bg-light m-5" style="max-width: 100%;">
                <div class="card-body bg-light" style="text-align: center">
                    <h1><fmt:message key="products.search.not_found_message"/></h1>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/products.js"></script>
</body>
</html>
