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
    <title><fmt:message key="products.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="container">
    <main role="main" class="container">
        <c:import url="fragment/navigation.jsp"/>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Product</th>
                <th scope="col"></th>
                <th scope="col" style="text-align: end">Amount</th>
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
                                <button type="submit" class="btn btn-link"
                                        style="padding-left: 0;padding-right: 0">
                                    <p><c:out value="${orderProduct.product.name}"/></p>
                                </button>
                            </form>
                        </div>
                    </td>

                    <td style="text-align: start">
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                                <%--                            <input type="hidden" name="command" value="delete_category">--%>
                                <%--                            <input type="hidden" name="categoryId" value="<c:out value="${category.id}"/>">--%>
                            <button class="btn btn-outline-danger mx-2 my-2 my-sm-0" type="submit">
                                <span><i class="fas fa-trash"></i> Delete</span>
                            </button>
                        </form>
                    </td>

                    <td style="text-align: end"><c:out value="${orderProduct.amount}"/></td>
                    <td style="text-align: end"><c:out value="${orderProduct.totalPrice}"/></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="4">
                    <div class="d-flex justify-content-end">
                        Total command: <c:out value="${order.cost}"/>
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
    </main>
</div>
<c:import url="fragment/footer.jsp"/>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/view_product.js"></script>
</body>
</html>
