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

        <c:forEach var="product" items="${products}">
            <div class="card mb-3" style="max-width: 100%;">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${product.image}"/>"
                             class="card-img" alt=""/>
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <a href="#"><h5 class="card-title"><c:out value="${product.name}"/></h5></a>
                            <p class="card-text"><c:out value="${product.description}"/></p>
                            <h5 class="card-title"><ctg:formatCurrency value="${product.price}"
                                                                       locale="${locale}"/></h5>
                            <c:choose>
                                <c:when test="${product.status}">
                                    <p><span class="badge badge-success"><fmt:message
                                            key="products.active"/></span></p>
                                </c:when>
                                <c:otherwise>
                                    <p><span class="badge badge-danger"><fmt:message
                                            key="products.notactive"/></span></p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

        </c:forEach>

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
