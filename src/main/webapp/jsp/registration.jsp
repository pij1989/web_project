<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css" type="text/css"/>
    <title><fmt:message key="register.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="register-container">
    <div class="register-item-wrapper bg-light">
        <h2><fmt:message key="register.message"/></h2>
        <form id="registerForm" class="needs-validation" action="${pageContext.request.contextPath}/controller"
              onsubmit="return false" method="post"
              novalidate>
            <input type="hidden" name="command" value="register">

            <div class="form-group">
                <label for="firstName"><fmt:message key="register.firstname.label"/> </label>
                <input type="text" name="firstName" value="" class="form-control" id="firstName"
                       placeholder="<fmt:message key="register.firstname.placeholder"/>" required
                       pattern="[a-zA-Zа-яА-Я]+"/>
                <div id="firstNameValidationMessage"></div>
            </div>

            <div class="form-group">
                <label for="lastName"><fmt:message key="register.lastname.label"/></label>
                <input type="text" name="lastName" value="" class="form-control" id="lastName"
                       placeholder="<fmt:message key="register.lastname.placeholder"/>" required
                       pattern="[a-zA-Zа-яА-Я]+"/>
                <div id="lastNameValidationMessage"></div>
            </div>

            <div class="form-group">
                <label for="email"><fmt:message key="login.email.label"/></label>
                <input type="email" name="email" value="" class="form-control" id="email"
                       placeholder="<fmt:message key="login.email.placeholder"/>" required
                       pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$">
                <div id="emailValidationMessage"></div>
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="login.email.help"/></small>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="login.password.label"/></label>
                <input type="password" name="password" value="" class="form-control" id="password"
                       placeholder="<fmt:message key="login.password.placeholder"/>" aria-describedby="passwordHelp"
                       required
                       pattern="[a-zA-Z0-9@#$%!]{8,40}">
                <div id="passwordValidationMessage"></div>
                <small id="passwordHelp" class="form-text text-muted"><fmt:message key="login.password.help"/></small>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="register.confirmpassword.label"/></label>
                <input type="password" name="confirmPassword" value="" class="form-control" id="confirmPassword"
                       placeholder="<fmt:message key="register.confirmpassword.placeholder"/>"
                       aria-describedby="passwordHelp" required
                       pattern="[a-zA-Z0-9@#$%!]{8,40}">
                <div id="confirmPasswordValidationMessage"></div>
            </div>

            <div class="register-item">
                <button type="submit" id="submit" class="btn btn-primary"><fmt:message key="register.submit"/></button>
            </div>
        </form>
        <br/>
        <c:if test="${errorEmailOrPassword}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="registration.error.emailorpassword"/>
            </div>
        </c:if>
    </div>
</div>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/registration.js"></script>
</body>
</html>
