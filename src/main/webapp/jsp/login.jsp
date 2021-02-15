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
    <title><fmt:message key="login.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="login-container">
    <div class="login-item-wrapper bg-light">
        <h2><fmt:message key="login.message"/></h2>
        <form id="loginForm" class="needs-validation" action="${pageContext.request.contextPath}/controller"
              onsubmit="return false" method="post"
              novalidate>
            <input type="hidden" name="command" value="login">
            <div class="form-group">
                <label for="email"><fmt:message key="login.email.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-envelope"></i></span>
                    </div>
                    <input type="email" name="email" value="" class="form-control" id="email"
                           placeholder="<fmt:message key="login.email.placeholder"/>" aria-describedby="emailHelp"
                           required
                           pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$">
                    <div id="emailValidMessage" class="valid-feedback" hidden><fmt:message
                            key="login.valid.message"/></div>
                    <div id="emailInvalidMessage" class="invalid-feedback" hidden><fmt:message
                            key="login.email.invalid.message"/></div>
                </div>
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="login.email.help"/></small>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="login.password.label"/></label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text bg-white"><i class="fas fa-lock"></i></span>
                    </div>
                    <input type="password" name="password" value="" class="form-control" id="password"
                           placeholder="<fmt:message key="login.password.placeholder"/>" aria-describedby="passwordHelp"
                           required
                           pattern="[a-zA-Z0-9@#$%!]{8,40}">
                    <div id="passwordValidMessage" class="valid-feedback" hidden><fmt:message
                            key="login.valid.message"/></div>
                    <div id="passwordInvalidMessage" class="invalid-feedback" hidden><fmt:message
                            key="login.password.invalid.message"/></div>
                </div>
                <small id="passwordHelp" class="form-text text-muted"><fmt:message key="login.password.help"/></small>
            </div>
            <div class="login-item">
                <button type="submit" id="submit" class="btn btn-primary"><fmt:message key="login.submit"/></button>
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=to_register_page_command">
                    <fmt:message key="login.link.registration"/></a>
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=to_change_password_page_command">
                    <fmt:message key="login.link.changepassword"/></a>
            </div>
        </form>
        <br/>
        <c:forEach var="error" items="${errors}">
            <c:if test="${error eq 'ERROR_USER_EMAIL'}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="login.error.email"/>
                </div>
            </c:if>
            <c:if test="${error eq 'ERROR_USER_PASSWORD'}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="login.error.password"/>
                </div>
            </c:if>
            <c:if test="${error eq 'ERROR_USER_NOT_EXIST'}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="login.error.user"/>
                </div>
            </c:if>
        </c:forEach>
        <c:remove var="errors" scope="session"/>
        <c:if test="${mismatchedPassword}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="login.mismatchedpassword"/><c:out value="${blockingCount}"/>
            </div>
            <c:remove var="mismatchedPassword" scope="session"/>
        </c:if>
        <c:if test="${blockedUser}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="login.blockeduser"/>
            </div>
            <c:remove var="blockedUser" scope="session"/>
        </c:if>
    </div>
</div>
<c:import url="fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>
