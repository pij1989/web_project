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
    <title><fmt:message key="cart.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<main role="main" class="container" style="min-height: calc(100vh - 112px)">
    <c:import url="fragment/navigation.jsp"/>
    <c:choose>
        <c:when test="${deleteProductFromOrderSuccess}">
            <div class="alert alert-success" role="alert" id="successDeleteProductFromOrder">
                <fmt:message key="cart.delete_product_message"/>
            </div>
            <c:remove var="deleteProductFromOrderSuccess" scope="session"/>
        </c:when>
        <c:when test="${deleteProductFromOrderError}">
            <div class="alert alert-danger" role="alert" id="errorDeleteProductFromOrder">
                <fmt:message key="cart.error_message"/>
            </div>
            <c:remove var="deleteProductFromOrderError" scope="session"/>
        </c:when>
        <c:when test="${confirmOrderSuccess}">
            <div class="alert alert-success" role="alert" id="successConfirmOrder">
                <fmt:message key="cart.order_confirm_message"/>
            </div>
            <c:remove var="confirmOrderSuccess" scope="session"/>
        </c:when>
        <c:when test="${confirmOrderError}">
            <div class="alert alert-danger" role="alert" id="errorConfirmOrder">
                <fmt:message key="cart.error_message"/>
            </div>
            <c:remove var="confirmOrderError" scope="session"/>
        </c:when>
        <c:when test="${changeAmountError}">
            <div class="alert alert-danger" role="alert" id="errorChangeAmount">
                <fmt:message key="cart.change_amount_error_message"/>
            </div>
            <c:remove var="changeAmountError" scope="session"/>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${orderIsEmpty or empty orderProducts}">
            <div class="card bg-light m-5" style="max-width: 100%;">
                <div class="card-body bg-light" style="text-align: center">
                    <h2><fmt:message key="cart.empty_message"/></h2>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="cart.table.head.product"/></th>
                    <th scope="col"></th>
                    <th scope="col" class="pl-5"><fmt:message key="cart.table.head.amount"/></th>
                    <th scope="col" style="text-align: end"><fmt:message key="cart.table.head.cost"/></th>
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
                                    <button type="submit" class="btn btn-link px-0 ml-1"
                                            <c:if test="${not orderProduct.product.status}">disabled</c:if>>
                                        <p><c:out value="${orderProduct.product.name}"/></p>
                                    </button>
                                </form>
                            </div>
                        </td>

                        <td style="text-align: start">
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="delete_product_from_order">
                                <input type="hidden" name="orderProductId"
                                       value="<c:out value="${orderProduct.id}"/>">
                                <button class="btn btn-outline-danger mx-2 my-2 my-sm-0" type="submit">
                                    <span><i class="fas fa-trash"></i> <fmt:message key="cart.button.delete"/></span>
                                </button>
                            </form>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${orderProduct.product.status}">
                                    <form class="add-product-to-order" method="post"
                                          action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="change_amount_product_in_order">
                                        <input type="hidden" name="orderProductId"
                                               value="<c:out value="${orderProduct.id}"/>">
                                        <div class="input-group" style="width: 150px">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-minus"></i></span>
                                            </div>
                                            <input type="text" class="form-control" maxlength="5" name="amountProduct"
                                                   value="<c:out value="${orderProduct.amount}"/>" required
                                                   pattern="^(?!0)[0-9]{1,5}$"/>
                                            <div class="input-group-append">
                                                <span class="input-group-text"><i class="fas fa-plus"></i></span>
                                            </div>
                                        </div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="pl-5"><c:out value="${orderProduct.amount}"/></div>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td style="text-align: end"><ctg:formatCurrency value="${orderProduct.totalPrice}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4">
                        <div class="d-flex justify-content-end">
                            <h5><fmt:message key="cart.total_cost"/> <ctg:formatCurrency value="${order.cost}"/></h5>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div class="d-flex justify-content-end">
                            <form action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="arrange_order">
                                <button type="submit" id="submit" class="btn btn-primary">
                                    <fmt:message key="cart.button.arrange_order"/>
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/view_order.js"></script>
</body>
</html>
