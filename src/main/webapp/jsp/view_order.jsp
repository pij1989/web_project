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
    <c:if test="${deleteProductFromOrderSuccess}">
        <div class="alert alert-success" role="alert" id="successDeleteProductFromOrder">
            Product is delete from order
        </div>
        <c:remove var="deleteProductFromOrderSuccess" scope="session"/>
    </c:if>
    <c:if test="${deleteProductFromOrderError}">
        <div class="alert alert-danger" role="alert" id="errorDeleteProductFromOrder">
            Error is occurred
        </div>
        <c:remove var="deleteProductFromOrderError" scope="session"/>
    </c:if>
    <c:choose>
        <c:when test="${orderIsEmpty or empty orderProducts}">
            <div class="card bg-light m-5" style="max-width: 100%;">
                <div class="card-body bg-light" style="text-align: center">
                    <h2>Cart is empty</h2>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Product</th>
                    <th scope="col"></th>
                    <th scope="col" class="pl-5">Amount</th>
                    <th scope="col" style="text-align: end">Cost</th>
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

                        <td style="text-align: start">
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="delete_product_from_order">
                                <input type="hidden" name="orderProductId"
                                       value="<c:out value="${orderProduct.id}"/>">
                                <button class="btn btn-outline-danger mx-2 my-2 my-sm-0" type="submit">
                                    <span><i class="fas fa-trash"></i> Delete</span>
                                </button>
                            </form>
                        </td>

                        <td>
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
                                           pattern="^[0-9]{1,5}$"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text"><i class="fas fa-plus"></i></span>
                                    </div>
                                </div>
                            </form>
                        </td>

                        <td style="text-align: end"><c:out value="${orderProduct.totalPrice}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4">
                        <div class="d-flex justify-content-end">
                            <h5>Total cost: <c:out value="${order.cost}"/></h5>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div class="d-flex justify-content-end">
                            <form>
                                <button type="submit" id="submit" class="btn btn-primary">Arrange order</button>
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
