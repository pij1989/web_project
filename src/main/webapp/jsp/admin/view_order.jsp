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
    <title><fmt:message key="order.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <c:choose>
                <c:when test="${not empty orderProducts}">
                    <br/>
                    <div class="row" style="line-height: 1;border-bottom: 1px solid #e5e5e5;">
                        <div class="col-6">
                            <c:if test="${not empty delivery}">
                                <h4 class="mb-3"><fmt:message key="order.delivery.title"/></h4>
                                <form>
                                    <div class="row">
                                        <div class="col-6 mb-3">
                                            <label for="firstName"><fmt:message key="order.delivery.form.first_name"/></label>
                                            <input type="text" class="form-control" id="firstName"
                                                   value="<c:out value="${delivery.firstName}"/>" disabled>
                                        </div>
                                        <div class="col-6 mb-3">
                                            <label for="lastName"><fmt:message key="order.delivery.form.last_name"/></label>
                                            <input type="text" class="form-control" id="lastName"
                                                   value="<c:out value="${delivery.lastName}"/>" disabled>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <div class="row">
                                            <div class="col-4 mb-3">
                                                <label for="city"><fmt:message key="order.delivery.form.city"/></label>
                                                <input type="text" class="form-control" id="city"
                                                       value="<c:out value="${delivery.address.city}"/>" disabled>
                                            </div>
                                            <div class="col-4 mb-3">
                                                <label for="street"><fmt:message key="order.delivery.form.street"/></label>
                                                <input type="text" class="form-control" id="street"
                                                       value="<c:out value="${delivery.address.street}"/>" disabled>
                                            </div>
                                            <div class="col-4 mb-3">
                                                <label for="homeNumber"><fmt:message key="order.delivery.form.home_number"/></label>
                                                <input type="text" class="form-control" id="homeNumber"
                                                       value="<c:out value="${delivery.address.homeNumber}"/>" disabled>
                                            </div>
                                        </div>

                                        <div class="mb-3">
                                            <label for="phone"><fmt:message key="order.delivery.form.telephone"/></label>
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-phone"></i></span>
                                                </div>
                                                <input type="text" class="form-control" id="phone"
                                                       value="<c:out value="${delivery.phoneNumber}"/>" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </c:if>
                        </div>
                        <div class="col-6">
                            <h4 class="mb-3"><fmt:message key="order.user.title"/> </h4>
                            <form>

                                <div class="row">
                                    <div class="col-6 mb-3">
                                        <label for="userFirstName"><fmt:message key="order.user.form.first_name"/></label>
                                        <input type="text" class="form-control" id="userFirstName"
                                               value="<c:out value="${viewUser.firstName}"/>" disabled>
                                    </div>
                                    <div class="col-6 mb-3">
                                        <label for="userLastName"><fmt:message key="order.user.form.last_name"/></label>
                                        <input type="text" class="form-control" id="userLastName"
                                               value="<c:out value="${viewUser.lastName}"/>" disabled>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-6 mb-3">
                                        <label for="username"><fmt:message key="order.user.form.username"/></label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text bg-white"><i
                                                        class="fas fa-user"></i></span>
                                            </div>
                                            <input type="text" class="form-control"
                                                   value="<c:out value="${viewUser.username}"/>"
                                                   id="username" disabled>
                                        </div>
                                    </div>
                                    <div class="col-6 mb-3">
                                        <label for="email"><fmt:message key="order.user.form.email"/></label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text bg-white"><i class="fas fa-envelope"></i></span>
                                            </div>
                                            <input type="text" class="form-control"
                                                   value="<c:out value="${viewUser.email}"/>"
                                                   id="email" disabled>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
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
                                        <p class="pl-2"><c:out value="${orderProduct.product.name}"/></p>
                                    </div>
                                </td>

                                <td style="text-align: end"><c:out value="${orderProduct.amount}"/></td>

                                <td style="text-align: end"><ctg:formatCurrency
                                        value="${orderProduct.totalPrice}"/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="4">
                                <div class="d-flex justify-content-end">
                                    <h5><fmt:message key="order.button.total_cost"/> <ctg:formatCurrency
                                            value="${viewOrder.cost}"/></h5>
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
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
</body>
</html>