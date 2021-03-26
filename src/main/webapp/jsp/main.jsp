<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navigation.css">
    <title><fmt:message key="main.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<main role="main" class="container" style="min-height: calc(100vh - 112px)">
    <c:import url="fragment/navigation.jsp"/>
    <h4 class="mb-3">Last add products</h4>
    <div class="row">
        <c:forEach var="product" items="${lastProducts}">
            <div class="col-md-4">
                <div class="card mb-4 shadow-sm">
                    <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${product.image}"/>"
                         class="card-img-top" alt=""/>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="view_product">
                            <input type="hidden" name="productId" value="<c:out value="${product.id}"/>">
                            <button type="submit" class="btn btn-link"
                                    style="padding-left: 0;padding-right: 0">
                                <h5 class="card-title"><c:out value="${product.name}"/></h5>
                            </button>
                        </form>
<%--                        <h5 class="card-title"><c:out value="${product.name}"/></h5>--%>
                        <p class="card-text"><c:out value="${product.description}"/></p>
                        <h5 class="card-title"><ctg:formatCurrency value="${product.price}"
                                                                   locale="${locale}"/></h5>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
</body>
</html>
