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
    <title><fmt:message key="reviews.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <br/>
            <c:choose>
                <c:when test="${deleteOrderSuccess}">
                    <div class="alert alert-success" role="alert" id="successDeleteOrder">
                        Order is deleted
                    </div>
                    <c:remove var="deleteOrderSuccess" scope="session"/>
                </c:when>
                <c:when test="${deleteOrderError}">
                    <div class="alert alert-danger" role="alert" id="errorDeleteOrder">
                        Error is occurred
                    </div>
                    <c:remove var="deleteOrderError" scope="session"/>
                </c:when>
                <c:when test="${changeStatusSuccess}">
                    <div class="alert alert-success" role="alert" id="successChangeStatus">
                        Order status is changed
                    </div>
                    <c:remove var="changeStatusSuccess" scope="session"/>
                </c:when>
                <c:when test="${changeStatusError}">
                    <div class="alert alert-danger" role="alert" id="errorChangeStatus">
                        Error is occurred
                    </div>
                    <c:remove var="changeStatusError" scope="session"/>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${not empty orders}">
                    <div class="d-flex justify-content-center">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="orders.table.head.id"/></th>
                                <th scope="col"><fmt:message key="orders.table.head.time"/></th>
                                <th scope="col"><fmt:message key="orders.table.head.cost"/></th>
                                <th scope="col"><fmt:message key="orders.table.head.status"/></th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td><c:out value="${order.id}"/></td>
                                    <td><ctg:formatLocalDateTime date="${order.creatingTime}" locale="${locale}"/></td>
                                    <td><ctg:formatCurrency value="${order.cost}"/></td>
                                    <td>
                                        <form class="orderStatusSelect"
                                              action="${pageContext.request.contextPath}/controller"
                                              method="post">
                                            <div class="form-group">
                                                <input type="hidden" name="command" value="change_order_status">
                                                <input type="hidden" name="orderId"
                                                       value="<c:out value="${order.id}"/>">
                                                <select name="orderStatus" class="form-control"
                                                        style="width: 143px;">
                                                    <option
                                                            <c:if test="${order.statusType eq 'NEW'}">selected</c:if>
                                                            value="NEW">
                                                        NEW
                                                    </option>
                                                    <option
                                                            <c:if test="${order.statusType eq 'PROCESSING'}">selected</c:if>
                                                            value="PROCESSING">
                                                        PROCESSING
                                                    </option>
                                                    <option
                                                            <c:if test="${order.statusType eq 'DELIVERY'}">selected</c:if>
                                                            value="DELIVERY">
                                                        DELIVERY
                                                    </option>
                                                </select>
                                            </div>
                                        </form>
                                    </td>

                                    <td>
                                        <div class="d-flex justify-content-end">
                                            <form action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="command" value="view_order">
                                                <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                <button class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                                                    <span><i class="far fa-eye"></i> View</span>
                                                </button>
                                            </form>
                                        </div>
                                    </td>

                                    <td>
                                        <div class="d-flex justify-content-end">
                                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="command" value="delete_order">
                                                <input type="hidden" name="orderId" value="<c:out value="${order.id}"/>">
                                                <button class="btn btn-outline-danger mx-2 my-2 my-sm-0" type="submit">
                                                    <span><i class="fas fa-trash"></i> Delete</span>
                                                </button>
                                            </form>
                                        </div>
                                    </td>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
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
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/orders.js"></script>
</body>
</html>