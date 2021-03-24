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
    <div class="row" style="line-height: 1;border-bottom: 1px solid #e5e5e5;">
        <div class="col-6">
            <h4 class="mb-3">Delivery</h4>
            <form>
                <div class="row">
                    <div class="col-6 mb-3">
                        <label for="firstName">First name</label>
                        <input type="text" class="form-control" id="firstName" placeholder="" value="" required>
                    </div>
                    <div class="col-6 mb-3">
                        <label for="lastName">Last name</label>
                        <input type="text" class="form-control" id="lastName" placeholder="" value="" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="telephone">Telephone</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-phone"></i></span>
                        </div>
                        <input type="text" class="form-control" id="telephone" placeholder="+375 (29) 0000000" required>
                    </div>
                </div>

                <div class="d-block my-3">
                    <div class="custom-control custom-radio">
                        <input id="courier" name="delivery" value="courier" type="radio" class="custom-control-input"
                               checked required>
                        <label class="custom-control-label" for="courier">Courier - 6 Br</label>
                    </div>
                    <div class="custom-control custom-radio">
                        <input id="self" name="delivery" value="self-delivery" type="radio" class="custom-control-input"
                               required>
                        <label class="custom-control-label" for="self">Self-delivery - 0 Br</label>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="address">Address</label>
                    <input type="text" class="form-control" id="address" placeholder="г. Минск, ул. Тростенецкая, 17"
                           required>
                </div>
            </form>

            <h4 class="mb-3">Payment</h4>
            <div class="mb-3">
                You can do payment only when you will receive your order
            </div>
        </div>
        <div class="col-6">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
                <span class="text-muted">Cart</span>
                <%--                <span class="badge badge-secondary badge-pill">3</span>--%>
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
                            <ctg:formatCurrency value="${orderProduct.totalPrice}" locale="${locale}"/>
                        </div>
                    </li>
                </c:forEach>
                <li class="list-group-item d-flex justify-content-between">
                    <span>Total cost</span>
                    <strong><ctg:formatCurrency value="${order.cost}" locale="${locale}"/></strong>
                </li>
            </ul>
        </div>
    </div>
    <div class="mt-3 mb-3 d-flex justify-content-end">
        <span class="pr-1">Total cost with delivery:</span>
        <strong> <ctg:formatCurrency value="${order.cost}" locale="${locale}"/></strong>
    </div>
    <div class="mb-3 d-flex justify-content-end">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value=" ">
            <button type="submit" id="confirmOrder" class="btn btn-primary">Confirm order</button>
        </form>
    </div>
    <div class="mb-3 d-flex justify-content-end">
        <form action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="cancel_arrange_order">
            <button type="submit" id="cancelOrder" class="btn btn-primary">Cancel order</button>
        </form>
    </div>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/view_order.js"></script>
</body>
</html>
