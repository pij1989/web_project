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
    <title><fmt:message key="orders.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<main role="main" class="container" style="min-height: calc(100vh - 112px)">
    <c:import url="fragment/navigation.jsp"/>
    <c:choose>
        <c:when test="${not empty orders}">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="orders.table.head.id"/></th>
                    <th scope="col"><fmt:message key="orders.table.head.time"/></th>
                    <th scope="col"><fmt:message key="orders.table.head.cost"/></th>
                    <th scope="col" style="text-align: end"><fmt:message key="orders.table.head.status"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="view_order">
                                <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                <button type="submit" class="btn btn-link"><c:out value="${order.id}"/></button>
                            </form>
                        </td>
                        <td><ctg:formatLocalDateTime date="${order.creatingTime}" locale="${locale}"/></td>
                        <td><ctg:formatCurrency value="${order.cost}"/></td>
                        <td style="text-align: end"><c:out value="${order.statusType}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="card bg-light m-5" style="max-width: 100%;">
                <div class="card-body bg-light" style="text-align: center">
                    <h2><fmt:message key="orders.empty_message"/></h2>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/view_order.js"></script>
</body>
</html>
