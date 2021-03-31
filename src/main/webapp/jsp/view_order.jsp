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
    <title><fmt:message key="order.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<main role="main" class="container" style="min-height: calc(100vh - 112px)">
    <c:import url="fragment/navigation.jsp"/>
    <c:choose>
        <c:when test="${not empty orderProducts}">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="order.table.head.product"/></th>
                    <th scope="col" style="text-align: end"><fmt:message key="order.table.head.amount"/></th>
                    <th scope="col" style="text-align: end"><fmt:message key="order.table.head.cost"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderProduct" items="${orderProducts}">
                    <tr>
                        <td style="width: 340px">
                            <div class="d-flex">
                                <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${orderProduct.product.image}"/>"
                                     style="width: 50%"/>
                                <form action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="view_product">
                                    <input type="hidden" name="productId"
                                           value="<c:out value="${orderProduct.product.id}"/>">
                                    <button type="submit" class="btn btn-link px-0 ml-1">
                                        <p><c:out value="${orderProduct.product.name}"/></p>
                                    </button>
                                </form>
                            </div>
                        </td>

                        <td style="text-align: end"><c:out value="${orderProduct.amount}"/></td>

                        <td style="text-align: end"><ctg:formatCurrency value="${orderProduct.totalPrice}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4">
                        <div class="d-flex justify-content-end">
                            <h5><fmt:message key="order.button.total_cost"/> <ctg:formatCurrency value="${viewOrder.cost}"/></h5>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="card bg-light m-5" style="max-width: 100%;">
                <div class="card-body bg-light" style="text-align: center">
                    <h2><fmt:message key="order.empty_message"/></h2>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
</body>
</html>
