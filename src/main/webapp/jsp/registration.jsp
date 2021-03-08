<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css" type="text/css"/>
    <title><fmt:message key="registration.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="register-container">
    <div class="register-item-wrapper bg-light">
        <h2><fmt:message key="registration.message"/></h2>
        <form id="registrationForm" class="needs-validation <c:if test="${not empty registrationForm}">was-validated</c:if>" action="${pageContext.request.contextPath}/controller" method="post" novalidate>
            <input type="hidden" name="command" value="register">

            <div class="form-group">
                <label for="firstName"><fmt:message key="registration.first_name.label"/> </label>
                <input type="text" name="firstName" value="<c:out value="${registrationForm['firstName']}"/>" class="form-control" id="firstName"
                       placeholder="<fmt:message key="registration.first_name.placeholder"/>" required
                       pattern="[a-zA-Zа-яА-Я]+"/>
                <div id="firstNameValidMessage" class="valid-feedback" hidden><fmt:message key="registration.valid.message"/> </div>
                <div id="firstNameInvalidMessage" class="invalid-feedback" hidden><fmt:message key="registration.first_name.invalid.message"/> </div>
                <small id="firstNameHelp" class="form-text text-muted"><fmt:message key="registration.first_name.help"/></small>
            </div>

            <div class="form-group">
                <label for="lastName"><fmt:message key="registration.last_name.label"/></label>
                <input type="text" name="lastName" value="<c:out value="${registrationForm['lastName']}"/>" class="form-control" id="lastName"
                       placeholder="<fmt:message key="registration.last_name.placeholder"/>" required
                       pattern="[a-zA-Zа-яА-Я]+"/>
                <div id="lastNameValidMessage" class="valid-feedback" hidden><fmt:message key="registration.valid.message"/> </div>
                <div id="lastNameInvalidMessage" class="invalid-feedback" hidden><fmt:message key="registration.last_name.invalid.message"/> </div>
                <small id="lastNameHelp" class="form-text text-muted"><fmt:message key="registration.last_name.help"/></small>
            </div>

            <div class="form-group">
                <label for="username"><fmt:message key="registration.username.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-user"></i></span>
                    </div>
                    <input type="text" name="username" value="<c:out value="${registrationForm['username']}"/>" class="form-control" id="username"
                           placeholder="<fmt:message key="registration.username.placeholder"/>" required
                           pattern="\w+"/>
                    <div id="usernameValidMessage" class="valid-feedback" hidden><fmt:message key="registration.valid.message"/> </div>
                    <div id="usernameInvalidMessage" class="invalid-feedback" hidden><fmt:message key="registration.username.invalid.message"/> </div>
                </div>
                <small id="usernameHelp" class="form-text text-muted"><fmt:message key="registration.username.help"/></small>
            </div>

            <div class="form-group">
                <label for="email"><fmt:message key="registration.email.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-envelope"></i></span>
                    </div>
                    <input type="email" name="email" value="<c:out value="${registrationForm['email']}"/>" class="form-control" id="email"
                           placeholder="<fmt:message key="registration.email.placeholder"/>" aria-describedby="emailHelp" required
                           pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"/>
                    <div id="emailValidMessage" class="valid-feedback" hidden><fmt:message key="registration.valid.message"/> </div>
                    <div id="emailInvalidMessage" class="invalid-feedback" hidden><fmt:message key="registration.username.invalid.message"/> </div>
                </div>
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="registration.email.help"/></small>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="registration.password.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-lock"></i></span>
                    </div>
                    <input type="password" name="password" value="<c:out value="${registrationForm['password']}"/>" class="form-control" id="password"
                           placeholder="<fmt:message key="registration.password.placeholder"/>" aria-describedby="passwordHelp" required
                           pattern="[a-zA-Z0-9@#$%!]{8,40}">
                    <div id="passwordValidMessage" class="valid-feedback" hidden><fmt:message key="registration.valid.message"/></div>
                    <div id="passwordInvalidMessage" class="invalid-feedback" hidden><fmt:message key="registration.password.invalid.message"/></div>
                </div>
                <small id="passwordHelp" class="form-text text-muted"><fmt:message key="registration.password.help"/></small>
            </div>

            <div class="form-group">
                <label for="password"><fmt:message key="registration.confirm_password.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-lock"></i></span>
                    </div>
                    <input type="password" name="confirmPassword" value="<c:out value="${registrationForm['confirmPassword']}"/>" class="form-control" id="confirmPassword"
                           placeholder="<fmt:message key="registration.confirm_password.placeholder"/>"
                           aria-describedby="passwordHelp" required
                           pattern="[a-zA-Z0-9@#$%!]{8,40}">
                    <div id="confirmPasswordValidMessage" class="valid-feedback" hidden><fmt:message key="registration.valid.message"/></div>
                    <div id="confirmPasswordInvalidMessage" class="invalid-feedback" hidden><fmt:message key="registration.password.invalid.message"/></div>
                    <div id="confirmPasswordNotEqualMessage" class="invalid-feedback" hidden><fmt:message key="registration.confirm_password.notequal.message"/></div>
                </div>
                <small id="confirmPasswordHelp" class="form-text text-muted"><fmt:message key="registration.confirm_password.help"/></small>
            </div>
            <c:remove var="registrationForm" scope="session"/>

            <div class="register-item">
                <button type="submit" id="submit" class="btn btn-primary"><fmt:message
                        key="registration.submit"/></button>
            </div>
        </form>
        <br/>
        <c:if test="${errorUser}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="registration.error.user"/> <c:out value="${email}"/>
            </div>
            <c:remove var="errorUser" scope="session"/>
            <c:remove var="email" scope="session"/>
        </c:if>
        <c:if test="${errorActivateRegistration}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="registration.error.activate_registration"/>
            </div>
            <c:remove var="errorActivateRegistration" scope="session"/>
        </c:if>
    </div>
</div>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/registration.js"></script>
</body>
</html>
