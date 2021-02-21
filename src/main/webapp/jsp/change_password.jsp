<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css"/>
    <title><fmt:message key="changepassword.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="login-container">
    <div class="login-item-wrapper bg-light">
        <h2><fmt:message key="changepassword.message"/></h2>
        <form id="changePasswordForm" class="needs-validation" action="${pageContext.request.contextPath}/controller" method="post" novalidate>
            <input type="hidden" name="command" value="change_password">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-envelope"></i></span>
                    </div>
                    <input type="email" name="email" value="" class="form-control" id="email"
                           placeholder="<fmt:message key="login.email.placeholder"/>" aria-describedby="emailHelp"
                           required
                           pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$">
                </div>
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="login.email.help"/></small>
            </div>
            <div class="form-group">
                <label for="oldPassword"><fmt:message key="changepassword.oldpassword.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-lock"></i></span>
                    </div>
                    <input type="password" name="oldPassword" value="" class="form-control" id="oldPassword"
                           placeholder="<fmt:message key="login.password.placeholder"/>" aria-describedby="oldPasswordHelp"
                           required
                           pattern="[a-zA-Z0-9@#$%!]{8,40}">
                </div>
                <small id="oldPasswordHelp" class="form-text text-muted"><fmt:message key="login.password.help"/></small>
            </div>

            <div class="form-group">
                <label for="newPassword"><fmt:message key="changepassword.newpassword.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-lock"></i></span>
                    </div>
                    <input type="password" name="newPassword" value="" class="form-control" id="newPassword"
                           placeholder="<fmt:message key="login.password.placeholder"/>" aria-describedby="newPasswordHelp"
                           required
                           pattern="[a-zA-Z0-9@#$%!]{8,40}">
                </div>
                <small id="newPasswordHelp" class="form-text text-muted"><fmt:message key="login.password.help"/></small>
            </div>
            <div class="login-item">
                <button type="submit" id="submit" class="btn btn-primary"><fmt:message key="changepassword.submit"/></button>
            </div>
        </form>
        <br/>
        <c:if test="${changePasswordSuccess}">
            <div class="alert alert-success" role="alert" id="changePasswordSuccess">
                <fmt:message key="changepassword.successchange"/>
            </div>
            <c:remove var="changePasswordSuccess" scope="session"/>
        </c:if>
        <c:if test="${changePasswordError}">
            <div class="alert alert-danger" role="alert" id="changePasswordError">
                <fmt:message key="changepassword.errorchange"/>
            </div>
            <c:remove var="changePasswordError" scope="session"/>
        </c:if>
    </div>
</div>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/change_password.js"></script>
</body>
</html>

