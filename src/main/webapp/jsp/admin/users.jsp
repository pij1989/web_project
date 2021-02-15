<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title><fmt:message key="users.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                <form class="form-inline my-3 my-lg-3" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="to_register_page_command">
                    <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                        <span><i class="fas fa-plus"></i> <fmt:message key="admin.button.create"/></span>
                    </button>
                </form>
            </div>
            <c:if test="${changeStatus eq 'Success'}">
                <div class="alert alert-success" id="successChangeStatus" role="alert">
                    <fmt:message key="users.alert.success"/>
                </div>
            </c:if>
            <c:if test="${changeStatus eq 'Error'}">
                <div class="alert alert-danger" id="errorChangeStatus" hidden role="alert">
                    <fmt:message key="users.alert.error"/>
                </div>
            </c:if>
            <c:remove var="changeStatus" scope="session"/>
            <c:choose>
                <c:when test="${not empty users}">
                    <div style="display: flex;justify-content: center">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="users.table.head.number"/></th>
                                <th scope="col"><fmt:message key="users.table.head.firstname"/></th>
                                <th scope="col"><fmt:message key="users.table.head.lastname"/></th>
                                <th scope="col"><fmt:message key="users.table.head.username"/></th>
                                <th scope="col"><fmt:message key="users.table.head.email"/></th>
                                <th scope="col"><fmt:message key="users.table.head.role"/></th>
                                <th scope="col"><fmt:message key="users.table.head.status"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="i" value="1"/>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <th scope="row"><c:out value="${i}"/></th>
                                    <td><c:out value="${user.firstName}"/></td>
                                    <td><c:out value="${user.lastName}"/></td>
                                    <td><c:out value="${user.username}"/></td>
                                    <td><c:out value="${user.email}"/></td>
                                    <td><c:out value="${user.roleType}"/></td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/controller" class="statusSelect">
                                            <div class="form-group">
                                                <input type="hidden" name="command" value="change_user_status">
                                                <input type="hidden" name="userId" value="<c:out value="${user.id}"/>">
                                                <select name="status" class="form-control"
                                                        style="width: 143px;">
                                                    <option
                                                            <c:if test="${user.statusType eq 'ACTIVE'}">selected</c:if>
                                                            value="ACTIVE">
                                                        <fmt:message key="users.status.active"/>
                                                    </option>
                                                    <option
                                                            <c:if test="${user.statusType eq 'BLOCKED'}">selected</c:if>
                                                            value="BLOCKED">
                                                        <fmt:message key="users.status.blocked"/>
                                                    </option>
                                                    <option
                                                            <c:if test="${user.statusType eq 'WAIT_ACTIVE'}">selected</c:if>
                                                            value="WAIT_ACTIVE">
                                                        <fmt:message key="users.status.waitactive"/>
                                                    </option>
                                                </select>
                                            </div>
                                        </form>
                                    </td>
                                </tr>
                                <c:set var="i" value="${i+1}"/>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="card bg-light m-5" style="max-width: 100%;">
                        <div class="card-body bg-light" style="text-align: center">
                            <h2><fmt:message key="users.message"/></h2>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/users.js"></script>
</body>
</html>
