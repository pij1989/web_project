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
    <title><fmt:message key="products.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<main role="main" class="container" style="min-height: calc(100vh - 112px)">
    <c:import url="fragment/navigation.jsp"/>
    <c:if test="${confirmOrderError}">
        <div class="alert alert-danger" role="alert" id="errorConfirmOrder">
            Error is occurred
        </div>
        <c:remove var="confirmOrderError" scope="session"/>
    </c:if>
    <div class="row" style="line-height: 1;border-bottom: 1px solid #e5e5e5;">
        <div class="col-6">
            <h4 class="mb-3">Delivery</h4>
            <form id="deliveryForm" class="needs-validation <c:if test="${not empty deliveryForm}">was-validated</c:if>" action="${pageContext.request.contextPath}/controller" method="post" novalidate>
                <input type="hidden" name="command" value="confirm_order">
                <div class="row">
                    <div class="col-6 mb-3">
                        <label for="firstName">First name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="<c:out value="${deliveryForm['firstName']}"/>"
                               placeholder="Enter first name" required pattern="[a-zA-Zа-яА-Я]+">
                    </div>
                    <div class="col-6 mb-3">
                        <label for="lastName">Last name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="<c:out value="${deliveryForm['lastName']}"/>"
                               placeholder="Enter last name" required pattern="[a-zA-Zа-яА-Я]+">
                    </div>
                </div>

                <div class="mb-3">
                    <div class="row">
                        <div class="col-4 mb-3">
                            <label for="city">City</label>
                            <input type="text" class="form-control" id="city" name="city" value="<c:out value="${deliveryForm['city']}"/>"
                                   placeholder="Minsk" required pattern="^[A-ZА-Я](?:(-?|\s?)[a-zA-Zа-яА-Я]+)*$">
                        </div>
                        <div class="col-4 mb-3">
                            <label for="street">Street</label>
                            <input type="text" class="form-control" id="street" name="street" value="<c:out value="${deliveryForm['street']}"/>"
                                   placeholder="Kalinovskogo" required pattern="^([0-9]+|[A-ZА-Я])(?:(-?|\s?)[a-zA-Zа-яА-Я0-9]+)*$">
                        </div>
                        <div class="col-4 mb-3">
                            <label for="homeNumber">Home number</label>
                            <input type="text" class="form-control" id="homeNumber" name="homeNumber" value="<c:out value="${deliveryForm['homeNumber']}"/>"
                                   placeholder="45" required pattern="^(?!0)[0-9]{1,3}(\/(?!0)[0-9]){0,1}$">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="phone">Telephone</label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-phone"></i></span>
                            </div>
                            <input type="text" class="form-control" id="phone" name="phone" value="<c:out value="${deliveryForm['phone']}"/>"
                                   placeholder="+375(00)0000000" required
                                   pattern="^[+]{1}([375]){3}[(]{1}[0-9]{2}[)]{1}[0-9]{7}$">
                        </div>
                        <small id="phoneHelp" class="form-text text-muted">Phone number should have next format:
                            +375(00)0000000</small>
                    </div>
                </div>
                <c:remove var="deliveryForm" scope="session"/>
            </form>
            <h4 class="mb-3">Payment</h4>
            <div class="mb-3">
                You can do payment only when you will receive your order
            </div>
        </div>
        <div class="col-6">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
                <span>Cart</span>
            </h4>
            <ul class="list-group mb-3">
                <c:forEach var="orderProduct" items="${orderProducts}">
                    <li class="list-group-item d-flex justify-content-between">
                        <div class="row">
                            <div class="col-3">
                                <img src="data:image/jpg;base64,<ctg:encodeBytes bytes="${orderProduct.product.image}"/>"
                                     style="width: 100%"/>
                            </div>
                            <div class="col-7">
                                <h6><c:out value="${orderProduct.product.name}"/></h6>
                            </div>
                            <div class="col-2 text-muted">
                                <c:out value="${orderProduct.amount}"/>
                            </div>
                        </div>
                        <div class="text-muted">
                            <ctg:formatCurrency value="${orderProduct.totalPrice}"/>
                        </div>
                    </li>
                </c:forEach>
                <li class="list-group-item d-flex justify-content-between">
                    <span>Total cost</span>
                    <strong><ctg:formatCurrency value="${order.cost}"/></strong>
                </li>
            </ul>
        </div>
    </div>
    <div class="mt-3 mb-3 d-flex justify-content-end">
        <button type="button" id="confirmOrder" class="btn btn-primary">Confirm order</button>
    </div>
    <div class="mb-3 d-flex justify-content-end">
        <form action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="cancel_arrange_order">
            <button type="submit" id="cancelOrder" class="btn btn-danger">Cancel order</button>
        </form>
    </div>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/arrange_order.js"></script>
</body>
</html>
